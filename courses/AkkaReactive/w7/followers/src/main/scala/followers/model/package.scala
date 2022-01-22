package followers.model

/**
  * A map that associates each user id to the set of users he follows.
  *
  * For instance, in a system with two users, `1` and `2`, we can
  * have the following map of followers:
  *
  * {{{
  *   Map(
  *     1 -> Set(2),
  *     2 -> Set()
  *   )
  * }}}
  *
  * Which means that user `1` follows user `2`, and user
  * `2` follows nobody.
  */
type Followers = Map[Int, Set[Int]]
