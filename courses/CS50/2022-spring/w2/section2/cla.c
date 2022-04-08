#include <cs50.h>
#include <stdio.h>

int main(int argc, string argv[])
{
    // Print argc
    printf("argc is: %i\n", argc);

    // Loop through strings stored in argv, print them out.
    for (int i = 0; i < argc; i++)
    {
        printf("argv[%i] is: ", i);
        printf("%s\n", argv[i]);
    }
}