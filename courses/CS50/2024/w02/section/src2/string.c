#include <cs50.h>
#include <stdio.h>
#include <string.h>

int main(void)
{
    string text = "This is CS50";
    for (int i = 0, length = strlen(text); i < length; i++)
    {
        printf("%i", text[i]);
    }
    printf("\n");
}
