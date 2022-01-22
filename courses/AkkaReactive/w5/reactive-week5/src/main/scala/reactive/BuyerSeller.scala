package reactive

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

// A foreign buyer/seller protocol
object BuyerSeller {

  case class Address()
  case class Date()

  case class RequestQuote(title: String, buyer: ActorRef[Quote])

  case class Quote(price: BigDecimal, seller: ActorRef[BuyOrQuit])

  sealed trait BuyOrQuit
  case class Buy(address: Address, buyer: ActorRef[Shipping]) extends BuyOrQuit
  case object Quit extends BuyOrQuit

  case class Shipping(date: Date)

}

// Using message adapters to make a third-party (the secretary) speak the buyer protocol
object BuyerSellerAdapter {

  import BuyerSeller._

  sealed trait Secretary
  case class BuyBook(title: String, maxprice: BigDecimal, seller: ActorRef[RequestQuote]) extends Secretary
  case class QuoteWrapper(msg: Quote) extends Secretary
  case class ShippingWrapper(msg: Shipping) extends Secretary

  def secretary(address: Address): Behavior[Secretary] =
    Behaviors.receivePartial {
      case (ctx, BuyBook(title, maxPrice, seller)) =>
        val quote: ActorRef[Quote] = ctx.messageAdapter(QuoteWrapper)
        seller ! RequestQuote(title, quote)
        buyBook(maxPrice, address)
    }

  def buyBook(maxPrice: BigDecimal, address: Address): Behavior[Secretary] =
    Behaviors.receivePartial {
      case (ctx, QuoteWrapper(Quote(price, session))) =>
        if (price > maxPrice) {
          session ! Quit // Nay, too expensive.
          println("did not get it :-(")
          Behaviors.stopped
        } else {
          val shipping = ctx.messageAdapter(ShippingWrapper)
          session ! Buy(address, shipping)
          Behaviors.same
        }
      case (ctx, ShippingWrapper(Shipping(date))) =>
        println("got it!")
        Behaviors.stopped // Yay, a book has been bought!
    }

}

// An alternative formulation of the buyer/seller protocol that makes
// it easier for third-parties to wrap it
object BuyerSellerWithRoles {

  case class Address()
  case class Date()

  sealed trait BuyerToSeller // Messages sent to the seller
  sealed trait SellerToBuyer // Messages sent to the buyer

  case class RequestQuote(title: String, buyer: ActorRef[Quote]) extends BuyerToSeller

  case class Quote(price: BigDecimal, seller: ActorRef[BuyOrQuit]) extends SellerToBuyer

  sealed trait BuyOrQuit extends BuyerToSeller
  case class Buy(address: Address, buyer: ActorRef[Shipping]) extends BuyOrQuit
  case object Quit extends BuyOrQuit

  case class Shipping(date: Date) extends SellerToBuyer

}

// Using message adapters to wrap the `SellerToBuyer` role of the buyer/seller protocol
object BuyerSellerWithRolesAdapter {

  import BuyerSellerWithRoles._

  sealed trait Secretary
  case class BuyBook(title: String, maxprice: BigDecimal, seller: ActorRef[RequestQuote]) extends Secretary
  case class WrapFromSeller(msg: SellerToBuyer) extends Secretary

  def buyer(address: Address): Behavior[Secretary] =
    Behaviors.setup { ctx =>
      val fromSeller = ctx.messageAdapter(WrapFromSeller)
      buyerMain(address, fromSeller)
    }

  def buyerMain(address: Address, fromSeller: ActorRef[SellerToBuyer]): Behavior[Secretary] =
    Behaviors.receiveMessagePartial {
      case BuyBook(title, maxPrice, seller) =>
        seller ! RequestQuote(title, fromSeller)
        buyBook(maxPrice, address, fromSeller)
    }

  def buyBook(maxPrice: BigDecimal, address: Address, fromSeller: ActorRef[SellerToBuyer]): Behavior[Secretary] =
    Behaviors.receiveMessagePartial {
      case WrapFromSeller(Quote(price, session)) =>
        if (price > maxPrice) {
          session ! Quit
          println("did not get it :-(")
          Behaviors.stopped
        } else {
          session ! Buy(address, fromSeller)
          Behaviors.same
        }
      case WrapFromSeller(Shipping(date)) =>
        println("got it!")
        Behaviors.stopped
    }

}

// Using a child actor to wrap a foreign protocol
object BuyerSellerWithRolesChildActors {

  import BuyerSellerWithRoles._

  sealed trait BuyerCommand
  case class BuyBook(title: String, maxprice: BigDecimal, seller: ActorRef[RequestQuote]) extends BuyerCommand
  case class Bought(shippingDate: Date) extends BuyerCommand
  case object NotBought extends BuyerCommand

  def buyer(address: Address): Behavior[BuyerCommand] =
    Behaviors.receive {
      case (ctx, BuyBook(title, maxPrice, seller)) =>
        val session = ctx.spawnAnonymous(buyBook(maxPrice, address, ctx.self))
        seller ! RequestQuote(title, session)
        ctx.watchWith(session, NotBought)
        Behaviors.same
      case (ctx, Bought(shippingDate)) =>
        println("got it!")
        Behaviors.stopped
      case (ctx, NotBought) =>
        println("did not get it :-(")
        Behaviors.stopped
    }

  def buyBook(maxPrice: BigDecimal, address: Address, replyTo: ActorRef[Bought]): Behavior[SellerToBuyer] =
    Behaviors.receive {
      case (ctx, Quote(price, session)) =>
        if (price > maxPrice) {
          session ! Quit
          Behaviors.stopped
        } else {
          session ! Buy(address, ctx.self)
          Behaviors.same
        }
      case (ctx, Shipping(date)) =>
        replyTo ! Bought(date)
        Behaviors.stopped
    }
}
