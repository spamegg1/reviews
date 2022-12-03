
public class Main {

    public static void main(String[] args) {
        // write experimental main programs here
        PaymentCard petesCard = new PaymentCard(10);

        System.out.println("money " + petesCard.balance());
        boolean wasSuccessful = petesCard.takeMoney(8);
        System.out.println("successfully withdrew: " + wasSuccessful);
        System.out.println("money " + petesCard.balance());

        wasSuccessful = petesCard.takeMoney(4);
        System.out.println("successfully withdrew: " + wasSuccessful);
        System.out.println("money " + petesCard.balance());



        PaymentTerminal unicafeExactum = new PaymentTerminal();

        double change = unicafeExactum.eatAffordably(10);
        System.out.println("remaining change " + change);

        change = unicafeExactum.eatAffordably(5);
        System.out.println("remaining change " + change);

        change = unicafeExactum.eatHeartily(4.3);
        System.out.println("remaining change " + change);

        System.out.println(unicafeExactum);




        PaymentTerminal unicafeExactum2 = new PaymentTerminal();

        double change2 = unicafeExactum2.eatAffordably(10);
        System.out.println("remaining change: " + change2);

        PaymentCard annesCard = new PaymentCard(7);

        boolean wasSuccessful2 = unicafeExactum2.eatHeartily(annesCard);
        System.out.println("there was enough money: " + wasSuccessful2);
        wasSuccessful2 = unicafeExactum2.eatHeartily(annesCard);
        System.out.println("there was enough money: " + wasSuccessful2);
        wasSuccessful2 = unicafeExactum2.eatAffordably(annesCard);
        System.out.println("there was enough money: " + wasSuccessful2);

        System.out.println(unicafeExactum2);



        PaymentTerminal unicafeExactum3 = new PaymentTerminal();
        System.out.println(unicafeExactum3);

        PaymentCard annesCard2 = new PaymentCard(2);

        System.out.println("amount of money on the card is " + annesCard2.balance() + " euros");

        boolean wasSuccessful3 = unicafeExactum3.eatHeartily(annesCard2);
        System.out.println("there was enough money: " + wasSuccessful3);

        unicafeExactum3.addMoneyToCard(annesCard2, 100);

        wasSuccessful3 = unicafeExactum3.eatHeartily(annesCard2);
        System.out.println("there was enough money: " + wasSuccessful3);

        System.out.println("amount of money on the card is " + annesCard2.balance() + " euros");

        System.out.println(unicafeExactum3);
    }
}

