#include <cs50.h>
#include <stdio.h>

int main(void)
{
    // TODO: Prompt for start size
    int startsize;
    do
    {
        startsize = get_int("Start size: ");
    }
    while (startsize < 9);

    // TODO: Prompt for end size
    int endsize;
    do
    {
        endsize = get_int("End size: ");
    }
    while (endsize < startsize);

    // TODO: Calculate number of years until we reach threshold
    int years = 0;
    int size = startsize;
    while (size < endsize)
    {
        size += size / 3 - size / 4;
        years++;
    }

    // TODO: Print number of years
    printf("Years: %i\n", years);
}
