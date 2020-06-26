from enum import Enum


class Operation(Enum):
    """Operations"""

    DELETED = 1
    INSERTED = 2
    SUBSTITUTED = 3

    def __str__(self):
        return str(self.name.lower())


def distances(a, b):
    """Calculate edit distance from a to b"""
    # Store lengths of strings
    m, n = len(a), len(b)

    # This is the edit cost function
    def cost(i, j):
        if i == 0 and j == 0:
            return 0
        elif i == 0 and j > 0:
            return j
        elif i > 0 and j == 0:
            return i
        # string indices are 1 less than matrix indices below
        elif a[i - 1] == b[j - 1]:
            return min(cost(i - 1, j) + 1,      # deletion
                       cost(i, j - 1) + 1,      # insertion
                       cost(i - 1, j - 1))      # substitution
        else:
            return min(cost(i - 1, j) + 1,      # deletion
                       cost(i, j - 1) + 1,      # insertion
                       cost(i - 1, j - 1) + 1)  # substitution

    # This is the 2D matrix to be returned, it is (m+1) by (n+1)
    # because string indices are 1 less than matrix indices
    matrix = [[0 for i in range(n + 1)] for j in range(m + 1)]

    # Base case: both strings are empty. No operation required.
    # Corresponds to the [0][0] location in the matrix
    matrix[0][0] = (0, None)

    # Base case: first string (a) is empty. Just n INSERTIONS required.
    # Corresponds to the first row in the matrix (except [0][0])
    for j in range(1, n + 1):
        matrix[0][j] = (j, Operation.INSERTED)

    # Base case: second string (b) is empty. Just m DELETIONS required.
    # Corresponds to the first column in the matrix (except [0][0])
    for i in range(1, m + 1):
        matrix[i][0] = (i, Operation.DELETED)

    # Now populate the rest of the matrix
    for i in range(1, m + 1):
        for j in range(1, n + 1):

            # Calculate the cost using above function
            pathcost = cost(i, j)

            # Determine last operation based on which gave us the min cost
            operation = None

            # string indices are 1 less than matrix indices
            if a[i - 1] == b[j - 1]:
                if pathcost == cost(i - 1, j) + 1:
                    operation = Operation.DELETED
                elif pathcost == cost(i, j - 1) + 1:
                    operation = Operation.INSERTED
                elif pathcost == cost(i - 1, j - 1):
                    operation = Operation.SUBSTITUTED

            else:
                if pathcost == cost(i - 1, j) + 1:
                    operation = Operation.DELETED
                elif pathcost == cost(i, j - 1) + 1:
                    operation = Operation.INSERTED
                elif pathcost == cost(i - 1, j - 1) + 1:
                    operation = Operation.SUBSTITUTED

            # Finally put this value into matrix[i][j]
            matrix[i][j] = (pathcost, operation)
    # end of outer for loop

    return matrix