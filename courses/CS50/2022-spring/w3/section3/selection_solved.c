#include <cs50.h>
#include <stdio.h>

#define SIZE 5

int main(void)
{
    int numbers[SIZE] = {0, 5, 1, 3, 4};

    // Print unsorted array
    printf("Unsorted array: ");
    for (int i = 0; i < SIZE; i++)
    {
        printf("%i", numbers[i]);
    }
    printf("\n");

    // Sort array with Selection Sort
    for (int i = 0; i < SIZE - 1; i++)
    {
        int smallest = numbers[i];
        int index = i;

        for (int j = i; j < SIZE; j++)
        {
            if (numbers[j] < smallest)
            {
                smallest = numbers[j];
                index = j;
            }
        }

        int temp = numbers[i];
        numbers[i] = smallest;
        numbers[index] = temp;
    }

    // Print sorted array
    printf("Sorted array: ");
    for (int i = 0; i < SIZE; i++)
    {
        printf("%i", numbers[i]);
    }
    printf("\n");
}