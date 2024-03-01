import TricolorSubtype.Calico

object FindTask:
  def isWhiteAndFluffy(cat: Cat): Boolean =
    /* Check if the cat is white and fluffy */
    cat.primaryColor == Color.White &&
      cat.furCharacteristics.contains(FurCharacteristic.Fluffy)

  def findWhiteAndFluffyCat(bagOfCats: Set[Cat]): Option[Cat] =
    /* Find a cat which is white and fluffy */
    bagOfCats.find(isWhiteAndFluffy)

  def isCalicoAndPersian(cat: Cat): Boolean =
    /* Find a cat which is abyssinian and calico */
    cat.pattern == Pattern.Tricolor(TricolorSubtype.Calico)
      && cat.breed == Breed.Persian

  def findCalicoAndPersian(bagOfCats: Set[Cat]): Option[Cat] =
    /* Find a cat which is abyssinian and calico */
    bagOfCats.find(isCalicoAndPersian)
