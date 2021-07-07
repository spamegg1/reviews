package democracy

import munit.*

import scala.util.Random
import scala.util.control.NonFatal

class MajorityJudgementSuite extends FunSuite:

  def randomGrade(): Grade =
    Grade.values(Random.nextInt(Grade.values.length))

  test("The median of an empty collection is undefined"){
    try
      val median = Grade.median(Nil)
      fail(s"Got median value $median")
    catch
      case NonFatal(_) => () // OK, all fine
  }

  test("The median of a collection with a single element should be that element"){
    val grade  = randomGrade()
    val median = Grade.median(List(grade))
    assertEquals(median, grade)
  }

  test("The median of a collection with two elements can be any of those two elements"){
    val grade1, grade2 = randomGrade()
    val median         = Grade.median(List(grade1, grade2))
    val (min, max)     = if grade1.ordinal < grade2.ordinal then (grade1, grade2) else (grade2, grade1)
    assert(
      median.ordinal >= min.ordinal,
      s"Median value $median should not be lower than ${min.ordinal}",
    )
    assert(
      median.ordinal <= max.ordinal,
      s"Median value $median should not be higher than ${max.ordinal}",
    )
  }

  test("The first (resp. second) half of a sorted collection should be lower (resp. higher) than the median grade"){
    for
      _      <- 1 to 100
      length  = Random.nextInt(50) + 1
      grades  = List.fill(length)(randomGrade())
    do
      val medianGrade  = Grade.median(grades)
      val sortedGrades = grades.sortBy(_.ordinal)
      val (firstHalf, secondHalf) = sortedGrades.splitAt(length / 2)
      firstHalf.find(_.ordinal > medianGrade.ordinal).foreach { counterExample =>
        fail(s"Found element with value ${counterExample.ordinal} in the first half of the collection. This element has a higher value than the median $medianGrade.")
      }
      secondHalf.find(_.ordinal < medianGrade.ordinal).foreach { counterExample =>
        fail(s"Found element with value ${counterExample.ordinal} in the second half of the collection. This element has a lower value than the median $medianGrade.")
      }
  }

  test("A median value minimizes the arithmetic mean of the absolute deviations of a collection"){
    for
      _            <- 1 to 100
      length        = Random.nextInt(50) + 1
      grades        = List.fill(length)(randomGrade())
      median        = Grade.median(grades)
      medianErrors  = grades.map(value => math.abs(median.ordinal - value.ordinal)).sum
      x             = Random.nextDouble() * 6
      xErrors       = grades.map(value => math.abs(x - value.ordinal)).sum
    do
      assert(
        medianErrors <= xErrors + 0.001,
        s"Found a value $x that has lower absolute deviations than the median ${median.ordinal}."
      )
  }

  test("The winner is the candidate that received an absolute majority of the highest grades given by all the voters"){
    val candidate1 = Candidate("Candidate 1")
    val candidate2 = Candidate("Candidate 2")
    val candidate3 = Candidate("Candidate 3")
    val candidates = Set(candidate1, candidate2, candidate3)
    val election = Election("Some election", candidates)

    def randomBallot(): Ballot =
      Ballot(candidates.map(_ -> randomGrade()).toMap)

    val voters = 50

    for
      _       <- 1 to 100
      ballots  = Seq.fill(voters)(randomBallot())
      winner   = election.elect(ballots)
      loosers  = candidates.filter(_ != winner)
    do
      val candidateMedians =
        candidates.map { candidate =>
          candidate -> Grade.median(ballots.map(_.grades(candidate)))
        }.toMap
      val highestMedian = candidateMedians.values.maxBy(_.ordinal)
      assert(
        loosers.forall(candidate => candidateMedians(candidate).ordinal <= highestMedian.ordinal)
      )
  }

  test("full scenario with no tie-break"){
    val tiramisu    = Candidate("Tiramisu")
    val cremeBrulee = Candidate("Crème brûlée")
    val cheesecake  = Candidate("Cheesecake")

    val election = Election("Best dessert", Set(tiramisu, cremeBrulee, cheesecake))

    val ballot1 = Ballot(
      Map(
        tiramisu    -> Grade.Excellent,
        cremeBrulee -> Grade.Good,
        cheesecake  -> Grade.Inadequate
      )
    )

    val ballot2 = Ballot(
      Map(
        tiramisu    -> Grade.Excellent,
        cremeBrulee -> Grade.Passable,
        cheesecake  -> Grade.Good
      )
    )

    val ballot3 = Ballot(
      Map(
        tiramisu    -> Grade.VeryGood,
        cremeBrulee -> Grade.Inadequate,
        cheesecake  -> Grade.Good
      )
    )

    val elected = election.elect(Seq(ballot1, ballot2, ballot3))

    assertEquals(elected, tiramisu)
  }

  test("full scenario with a tie-break"){
    val karting  = Candidate("Karting")
    val sailing  = Candidate("Sailing")
    val hiking   = Candidate("Hiking")
    val cooking  = Candidate("Cooking")
    val election = Election(
      "Team Building Activity",
      Set(karting, sailing, hiking, cooking)
    )
    val ballotAlice = Ballot(
      Map(
        karting -> Grade.Good,
        sailing -> Grade.Excellent,
        hiking  -> Grade.VeryGood,
        cooking -> Grade.VeryGood
      )
    )

    val ballotBob = Ballot(
      Map(
        karting -> Grade.VeryGood,
        sailing -> Grade.VeryGood,
        hiking  -> Grade.VeryGood,
        cooking -> Grade.Excellent
      )
    )

    val ballotCarol = Ballot(
      Map(
        karting -> Grade.Passable,
        sailing -> Grade.Mediocre,
        hiking  -> Grade.Good,
        cooking -> Grade.Good
      )
    )

    val elected = election.elect(Seq(ballotAlice, ballotBob, ballotCarol))
    assertEquals(elected, cooking)
  }

  test("full scenario with a tie-break and the same grades for cooking and hiking") {
    val karting  = Candidate("Karting")
    val sailing  = Candidate("Sailing")
    val hiking   = Candidate("Hiking")
    val cooking  = Candidate("Cooking")
    val election = Election(
      "Team Building Activity",
      Set(karting, sailing, hiking, cooking)
    )
    val ballotAlice = Ballot(
      Map(
        karting -> Grade.Good,
        sailing -> Grade.Excellent,
        hiking  -> Grade.VeryGood,
        cooking -> Grade.VeryGood
      )
    )

    val ballotBob = Ballot(
      Map(
        karting -> Grade.VeryGood,
        sailing -> Grade.VeryGood,
        hiking  -> Grade.Excellent,
        cooking -> Grade.Excellent
      )
    )

    val ballotCarol = Ballot(
      Map(
        karting -> Grade.Passable,
        sailing -> Grade.Mediocre,
        hiking  -> Grade.Good,
        cooking -> Grade.Good
      )
    )

    val elected = election.elect(Seq(ballotAlice, ballotBob, ballotCarol))
    assert(
      Set(cooking, hiking).contains(elected),
      s"When several candidates receive exactly the same grades, the winner should be chosen by lottery among them. Elected: $elected. Expected: Cooking or Hiking."
    )
  }

end MajorityJudgementSuite
