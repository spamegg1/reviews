#include <cs50.h>
#include <stdio.h>

// Define an integer, named SIZE, whose value shouldn't change
const int SIZE = 5;

int main(void)
{
    // Define an array of size SIZE
    int array[SIZE];

    // Set first value of array
    array[0] = 1;

    // Print first value of array
    printf("%i\n", array[0]);

    // Dynamically construct rest of array
    for (int i = 1; i < SIZE; i++)
    {
        array[i] = 2 * array[i - 1];
        printf("%i\n", array[i]);
    }
}