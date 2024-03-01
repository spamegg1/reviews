import FurCharacteristic.Fluffy

object FilterTask:
  // Checks if the cat is calico.
  def isCatCalico(cat: Cat): Boolean =
    /* Check if the cat is calico */
    cat.pattern == Pattern.Tricolor(TricolorSubtype.Calico)

  // Checks if the cat is fluffy.
  def isCatFluffy(cat: Cat): Boolean =
    /* Check if the cat is fluffy */
    cat.furCharacteristics.contains(Fluffy)

  // Checks if the cat's breed is Abyssinian.
  def isCatAbyssinian(cat: Cat): Boolean =
    /* Check if the cat's breed is Abyssinian */
    cat.breed == Breed.Abyssinian

  // Checks if the cat is calico, fluffy or Abyssinian.
  def desiredKindOfCat(cat: Cat): Boolean =
    /* Check if the cat is calico, fluffy or Abyssinian */
    isCatCalico(cat) || isCatFluffy(cat) || isCatAbyssinian(cat)

  // Filter the cats with the desired characteristics.
  // Notice that a named function is passed into the filter.
  def filterCats(shelterCats: Set[Cat]): Set[Cat] =
    shelterCats.filter(desiredKindOfCat)
