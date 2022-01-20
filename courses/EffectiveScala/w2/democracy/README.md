# Democracy

*Democracy is a form of government in which the people have the authority to choose their governing legislation.* (Wikipedia)

However, it turns out that the voting processes that people use to choose their governing legislation may have caveats. For instance, it is perfectly possible that the outcome of an election is not the candidate preferred by a majority of people. (For the record, this has happened in France in 2012, where François Hollande was elected president although François Bayrou was preferred over him.)

In this assignment, you will implement the [Majority Judgement](https://en.wikipedia.org/wiki/Majority_judgment) voting system. This voting system is an alternative to commonly used voting systems (such as first-past-the-post or two-round systems) that guarantees that the winner between three or more candidates will be the candidate who had received an absolute majority of the highest grades given by all the voters.

## Overview

Unlike common voting systems, in Majority Judgement voters don’t vote for a single candidate, but they give a grade to each candidate. The candidate with the highest median grade wins the election.

In our implementation, each voter can give a grade among bad, mediocre, inadequate, passable, good, very good, and excellent (from worst to best).

A ballot contains the grades that a voter assigns to each alternative of the election.

We find the winner of the election by computing the median grade received by each alternative. The alternative with the highest median grade is the winner.

In case more than one alternative have the same median grade, there is a tie-break. The tie-breaking procedure consists of removing from each alternative the grades equal to the median grade until only one of the previously tied candidates has the highest median grade.

## Open the Project

Download the [handout archive](https://moocs.scala-lang.org/~dockermoocs/handouts/scala-3/democracy.zip), extract it somewhere on your filesystem, and open the directory **democracy** in your code editor.

Open the file **src/main/scala/democracy/MajorityJudgement.scala**.

It contains a program with some blanks to fill in (the **???** expressions). You can read it from top to bottom and fill the blanks as you read.

## Fill in the Blanks

We model the grade a voter can assign to an alternative with the enumeration **Grade**.

The first task consists in computing the median grade of a collection of grades. One way of achieving this is to sort the collection of grades and take the element in the middle of the collection.

Then, implement the **elect** method of the class **Election**. It takes a collection of ballots and returns the winner alternative. The method **elect** re-organizes the data contained in the ballots so that the majority judgement algorithm can be applied to the data. From the collection of ballots, construct a **Map** containing all the grades assigned to each alternative.

Finally, implement the majority judgement algorithm in the method **findWinner**. First, compute the highest median grade assigned to an alternative. Second, retrieve all the alternatives that got the same median grade as the highest median grade. At that point, if only one alternative got the highest median grade, it’s the winner. Otherwise, there is a tie-break between all the candidates that got the same median grade as the highest median grade. In such a case, apply the algorithm **findWinner** again on the tied alternatives, after having removed one median grade from their grades.