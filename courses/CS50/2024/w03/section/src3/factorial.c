#include <cs50.h>
#include <stdio.h>

int factorial(int n);

int main(void) 
{
    // Get positive value for N
    int n;
    do
    {
        n = get_int("n: ");
    }
    while (n < 0);

    // Print factorial
    printf("%i\n", factorial(n));
}

int factorial(int n)
{
    // Base case
    if (n == 1)
    {
        return 1;
    }

    // Recursive case
    return n * factorial(n - 1);
}
