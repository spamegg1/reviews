package todo
package data


/*
 * Tags represents a collection of Tag items. The Tag items should be distinct
 * (that is, with no duplicates).
 *
 * We assume ordering is important to the user, hence we use a List rather than
 * Set.
 *
 * You should NOT modify this file.
 */
final case class Tags(tags: List[Tag]):
  def toList: List[Tag] = tags
