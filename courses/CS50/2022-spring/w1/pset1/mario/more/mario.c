#include <cs50.h>
#include <stdio.h>

int main(void)
{
    // get input
    int height = get_int("Height: ");
    while (height < 1 || height > 8)
    {
        height = get_int("Height: ");
    }

    // create pyramid
    int hashes = 1;
    int spaces = height - hashes;

    for (int i = 0; i < height; i++)
    {
        // print spaces
        for (int j = 0; j < spaces; j++)
        {
            printf(" ");
        }

        // print left hashes
        for (int j = 0; j < hashes; j++)
        {
            printf("#");
        }

        // print the gaps
        printf("  ");

        // print right hashes
        for (int j = 0; j < hashes; j++)
        {
            printf("#");
        }

        // go to next line
        printf("\n");

        hashes++;
        spaces = height - hashes;
    }
    return 0;
}