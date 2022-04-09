#include <cs50.h>
#include <stdio.h>
#include <string.h>

// Max number of candidates
#define MAX 9

// preferences[i][j] is number of voters who prefer i over j
int preferences[MAX][MAX];

// locked[i][j] means i is locked in over j
bool locked[MAX][MAX];

// Each pair has a winner, loser
typedef struct
{
    int winner;
    int loser;
}
pair;

// Array of candidates
string candidates[MAX];
pair pairs[MAX * (MAX - 1) / 2];

int pair_count;
int candidate_count;

// Function prototypes
bool vote(int rank, string name, int ranks[]);
void record_preferences(int ranks[]);
void add_pairs(void);
void sort_pairs(void);
void lock_pairs(void);
void print_winner(void);
bool path_exists(int winner, int loser);

int main(int argc, string argv[])
{
    // Check for invalid usage
    if (argc < 2)
    {
        printf("Usage: tideman [candidate ...]\n");
        return 1;
    }

    // Populate array of candidates
    candidate_count = argc - 1;
    if (candidate_count > MAX)
    {
        printf("Maximum number of candidates is %i\n", MAX);
        return 2;
    }
    for (int i = 0; i < candidate_count; i++)
    {
        candidates[i] = argv[i + 1];
    }

    // Clear graph of locked in pairs
    for (int i = 0; i < candidate_count; i++)
    {
        for (int j = 0; j < candidate_count; j++)
        {
            locked[i][j] = false;
        }
    }

    pair_count = 0;
    int voter_count = get_int("Number of voters: ");

    // Query for votes
    for (int i = 0; i < voter_count; i++)
    {
        // ranks[i] is voter's ith preference
        int ranks[candidate_count];

        // Query for each rank
        for (int j = 0; j < candidate_count; j++)
        {
            string name = get_string("Rank %i: ", j + 1);

            if (!vote(j, name, ranks)) // j = rank below in vote
            {
                printf("Invalid vote.\n");
                return 3;
            }
        }

        record_preferences(ranks);

        printf("\n");
    }

    add_pairs();
    sort_pairs();
    lock_pairs();
    print_winner();
    return 0;
}

// Update ranks given a new vote
bool vote(int rank, string name, int ranks[])
{
    for (int i = 0; i < candidate_count; i++)
    {
        if (strcmp(name, candidates[i]) == 0)
        {
            ranks[rank] = i;
            return true;
        }
    }
    return false;
}

// Update preferences given one voter's ranks
void record_preferences(int ranks[])
{
    for (int i = 0; i < candidate_count - 1; i++)
    {
        preferences[i][i] = 0;

        for (int j = i + 1; j < candidate_count; j++)
        {
            preferences[ranks[i]][ranks[j]]++;
        }
    }
    return;
}

// Record pairs of candidates where one is preferred over the other
void add_pairs(void)
{
    for (int i = 0; i < candidate_count - 1; i++)
    {
        for (int j = i + 1; j < candidate_count; j++)
        {

            int pref_ij = preferences[i][j];
            int pref_ji = preferences[j][i];
            pair newpair;

            if (pref_ij > pref_ji)
            {
                newpair.winner = i;
                newpair.loser = j;
                pairs[pair_count] = newpair;
                pair_count++;
            }
            else if (pref_ij < pref_ji)
            {
                newpair.winner = j;
                newpair.loser = i;
                pairs[pair_count] = newpair;
                pair_count++;
            }
        }
    }
    return;
}

// Sort pairs in decreasing order by strength of victory
void sort_pairs(void)
{
    for (int i = 0; i < pair_count - 1; i++)
    {
        for (int j = i + 1; j < pair_count; j++)
        {

            pair ith = pairs[i];
            pair jth = pairs[j];
            pair temp;

            int str_i = preferences[ith.winner][ith.loser];
            int str_j = preferences[jth.winner][jth.loser];

            if (str_i < str_j)
            {
                temp = ith;
                pairs[i] = jth;
                pairs[j] = temp;
            }
        }
    }
    return;
}

// Checks if a directed path exists from loser to winner
bool path_exists(int winner, int loser)
{
    for (int i = 0; i < candidate_count; i++)
    {
        if (locked[loser][i])
        {
            if (i == winner || path_exists(winner, i))
            {
                return true;
            }
        }
    }
    return false;
}

// Lock pairs into the candidate graph in order, without creating cycles
void lock_pairs(void)
{
    for (int i = 0; i < pair_count; i++)
    {
        // assume pairs is sorted by decreasing strength of victory
        pair pr = pairs[i];

        if (!path_exists(pr.winner, pr.loser))
        {
            locked[pr.winner][pr.loser] = true;
        }
    }
    return;
}

// Print the winner of the election
void print_winner(void)
{
    int winner = 0;
    bool flag = true;
    while (winner < candidate_count)
    {
        for (int i = 0; i < candidate_count; i++)
        {
            if (locked[i][winner])
            {
                flag = false;
                break;
            }
        }
        if (flag)
        {
            break;
        }
        else
        {
            winner++;
            flag = true;
        }
    }
    printf("%s\n", candidates[winner]);
    return;
}

