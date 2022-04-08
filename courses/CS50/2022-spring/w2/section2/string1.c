#include <cs50.h>
#include <stdio.h>
#include <string.h>

int main(void)
{
    string name = get_string("What's your name? ");

    // Use strlen function get length of string
    int length = strlen(name);

    for (int i = 0; i < length; i++)
    {
        printf("%c\n", name[i]);
    }
}