#include <cs50.h>
#include <stdio.h>

int main(void)
{
    int sequence[5];

    sequence[0] = 1;
    printf("%i ", sequence[0]);

    for (int i = 1; i < 5; i++)
    {
        sequence[i] = sequence[i - 1] * 2;
        printf("%i ", sequence[i]);
    }
    printf("\n");
}
