package democracy

/**
 * A grade to assign to a candidate. There are seven possible grades 
 * (from the worst to the best): `Bad`, `Mediocre`, `Inadequate`, 
 * `Passable`, `Good`, `VeryGood`, and `Excellent`.
 *
 * Grades can be compared by using their `ordinal` method:
 *
 * {{{
 *   Grade.Mediocre.ordinal < Grade.Good.ordinal
 * }}}
 */
enum Grade:
  case Bad, Mediocre, Inadequate, Passable, Good, VeryGood, Excellent

object Grade:
  /**
   * @return The median grade of a collection of grades.
   *
   * The median grade can be computed by sorting the collection
   * and taking the element in the middle. If there are an even
   * number of grades, any of the two grades that are just before
   * of after the middle of the sequence are correct median values.
   *
   * Grades can be compared by using their `ordinal` method.
   *
   * Hints: use the following operations:
   * - `sortBy` and `ordinal` to sort the collection of grades,
   * - `size` to compute the number of elements,
   * - `apply` to select an element at a specific index.
   */
  def median(grades: Seq[Grade]): Grade =                                // TODO
    grades
      .sortBy(_.ordinal)
      .apply(grades.size / 2)

end Grade

/**
 * A candidate in an election.
 * @param name (unique) name of the candidate (e.g., “Barack Obama”)
 */
case class Candidate(name: String)

/**
 * A ballot, which assigns a grade to each candidate of an election.
 * @param grades The grades assigned to each candidate
 */
case class Ballot(grades: Map[Candidate, Grade])

/**
 * An election is defined by a simple description and a set of possible
 * candidates.
 * @param description Description of the election (e.g. “Presidential Election”)
 * @param candidates Possible candidates
 */
case class Election(description: String, candidates: Set[Candidate]):
  /**
   * @return The candidate that wins this election, according to the Majority
   *         Judgement voting process.
   *
   * @param ballots The ballots for this election
   *
   * The ballots ''must'' assign a grade to each of the `candidates` of this
   * election.
   */
  def elect(ballots: Seq[Ballot]): Candidate =
    assert(ballots.nonEmpty)
    assert(ballots forall (_.grades.keySet == candidates))

    // Re-structure the data to get all the grades assigned to
    // each candidate by all the voters

    // First step: use the operation `flatMap` to flatten the ballots
    // into a single sequence containing the grades assigned to each
    // candidate by the voters.
    val allGrades: Seq[(Candidate, Grade)] = ballots flatMap (_.grades)  // TODO

    // Second step: use the operation `groupMap` to transform the
    // collection of pairs of `(Candidate, Grade)` into a `Map`
    // containing all the grades that were assigned to a given
    // `Candidate`.
    val gradesPerCandidate: Map[Candidate, Seq[Grade]] =                 // TODO
      allGrades.groupMap (_._1) (_._2)

    findWinner(gradesPerCandidate)
  end elect

  /**
   * @return The winner of this election, according to the Majority Judgement
   *         voting process.
   *
   * @param gradesPerCandidate The grades that have been assigned to each
   *                             candidate by the voters.
   */
  def findWinner(gradesPerCandidate: Map[Candidate, Seq[Grade]]): Candidate =
    // In case all the candidates have an empty collection of grades (this
    // can happen because of the tie-breaking algorithm, see below), 
    // the winner is chosen by lottery from among the candidates.
    if gradesPerCandidate forall ((_, grades) => grades.isEmpty) then
      val candidatesSeq = gradesPerCandidate.keys.toSeq
      val randomIndex   = util.Random.between(0, candidatesSeq.size)
      candidatesSeq(randomIndex)
    else
      // Otherwise, find the highest median grade assigned to a candidate.
      // Use the operation `values` to select the collections of grades,
      // then use the operation `filter` to keep only the non empty grades,
      // then use the operation `map` to compute the median value of each
      // collection of grades, and finally use the operation `maxBy` 
      // to find the highest median grade.
      val bestMedianGrade: Grade =                                       // TODO
        gradesPerCandidate                         // Map[Candidate, Seq[Grade]]
          .values                                                  // Seq[Grade]
          .filter(_.nonEmpty)                                      // Seq[Grade]
          .map(Grade.median)                                       // Seq[Grade]
          .maxBy(_.ordinal)                                             // Grade

      // Use the operation `filter` to select all the candidates 
      // that got the same best median grade (as the case may be)
      val bestCandidates: Map[Candidate, Seq[Grade]] =                   // TODO
        gradesPerCandidate filter
          ((_, grades) => Grade.median(grades) == bestMedianGrade)

      // In case only one candidate got the best median grade, it’s the winner!
      if bestCandidates.size == 1 then
        // Use the operation `head` to retrieve the only element
        // of the collection `bestCandidates`
        bestCandidates.head._1                                           // TODO
      else
        // Otherwise, there is a tie between several candidates. The
        // tie-breaking algorithm is the following:
        // “If more than one candidate has the same highest median-grade, the
        // winner is discovered by removing (one-by-one) any grades equal in
        // value to the shared median grade from each tied candidate's total.
        // This is repeated until only one of the previously tied candidates
        // is currently found to have the highest median-grade.”
        // (source: https://en.wikipedia.org/wiki/Majority_judgment)

        // Use the operation `map` to transform each element of the
        // `bestCandidates`. And use the operation `diff` to remove one
        // `bestMedianGrade` from the grades assigned to the candidates.    TODO
        val bestCandidatesMinusOneMedianGrade: Map[Candidate, Seq[Grade]] =
          bestCandidates map ((c, g) => (c, g diff Seq(bestMedianGrade)))

        // Finally, call `findWinner` on the reduced collection of candidates,
        // `bestCandidatesMinusOneMedianGrade`.
        findWinner(bestCandidatesMinusOneMedianGrade)                    // TODO
  end findWinner

end Election
