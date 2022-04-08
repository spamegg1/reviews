#include <cs50.h>
#include <stdio.h>
#include <string.h>

int main(void)
{
    string name = get_string("What's your name? ");

    int i = 0;

    // Remember that characters use single quotes!
    while (name[i] != '\0')
    {
        printf("%c\n", name[i]);
        i++;
    }
}