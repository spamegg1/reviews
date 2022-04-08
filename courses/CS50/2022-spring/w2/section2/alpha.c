#include <cs50.h>
#include <stdio.h>
#include <string.h>

int main(void)
{
    // Get a string
    string test = get_string("String: ");

    // Get length of string
    int length = strlen(test);

    for (int i = 0; i < length - 1; i++)
    {

        // If characters are not in alphabtical order
        if (test[i] > test[i + 1])
        {
            printf("No.\n");
            return 0; // Use return to exit our program without continuing further.
        }
    }

    printf("Yes.\n");
}