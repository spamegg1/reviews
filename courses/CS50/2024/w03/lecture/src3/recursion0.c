// Draws a pyramid (incorrectly) using recursion,
// must be compiled with -Wno-infinite-recursion

#include <cs50.h>
#include <stdio.h>

void draw(int n);

int main(void)
{
    draw(1);
}

void draw(int n)
{
    for (int i = 0; i < n; i++)
    {
        printf("#");
    }
    printf("\n");

    draw(n + 1);
}
