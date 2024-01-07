// Division with longs, demonstrating double

#include <cs50.h>
#include <stdio.h>

int main(void)
{
    // Prompt user for x
    long x = get_long("x: ");

    // Prompt user for y
    long y = get_long("y: ");

    // Divide x by y
    double z = (double) x / (double) y;
    printf("%.20f\n", z);
}
