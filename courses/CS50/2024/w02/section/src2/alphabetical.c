#include <cs50.h>
#include <stdio.h>
#include <string.h>

int main(void)
{
    string phrase = get_string("Phrase: ");
    for (int i = 0, length = strlen(phrase); i < length - 1; i++)
    {
        if (phrase[i] > phrase[i + 1])
        {
            printf("Not in alphabetical order!\n");
            return 0;
        }
    }
    printf("In alphabetical order!\n");
    return 0;
}
