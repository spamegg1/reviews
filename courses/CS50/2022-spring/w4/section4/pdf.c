#include <cs50.h>
#include <stdint.h>
#include <stdio.h>

int main(int argc, string argv[])
{
    // Check for usage
    if (argc != 2)
    {
        printf("Improper usage.\n");
        return 1;
    }

    // Open file
    FILE *pdf = fopen(argv[1], "r");
}