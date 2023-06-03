package games.game2048

/*
 * This function moves all the non-null elements to the beginning of the list
 * (by removing nulls) and merges equal elements.
 * The parameter 'merge' specifies the way how to merge equal elements:
 * it returns a new element that should be present in the resulting list
 * instead of two merged elements.
 *
 * If the function 'merge("a")' returns "aa",
 * then the function 'moveAndMergeEqual' transforms the input in the following way:
 *   a, a, b -> aa, b
 *   a, null -> a
 *   b, null, a, a -> b, aa
 *   a, a, null, a -> aa, a
 *   a, null, a, a -> aa, a
 *
 * You can find more examples in 'TestGame2048Helper'.
*/
fun <T : Any> List<T?>.moveAndMergeEqual(merge: (T) -> T): List<T> {
    val list = filterNotNull()
    if (list.isEmpty() || list.size == 1) return list

    val result = mutableListOf<T>()
    var left = 0
    var right = 1

    while (left < list.size) {
        if (left == list.size - 1){
            result.add(list[left])
            break
        } else if (list[left] != list[right]) {
            result.add(list[left])
            left ++
            right ++
        } else if (list[left] == list[right]) {
            result.add(merge(list[left]))
            left += 2
            right += 2
        }
    }
    return result
}
