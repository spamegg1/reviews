#include <stdio.h>
#include <cs50.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>

int main(int argc, string argv[])
{
    // Give error if there is not exactly one command line argument
    if (argc != 2)
    {
        printf("Error\n");
        return 1;
    }

    string key = argv[1];

    for (int i = 0; i < strlen(key); i++)
    {
        if (!isdigit(key[i]))
        {
            printf("Usage: ./caesar key\n");
            return 1;
        }
    }

    // k is the number of places by which letters are shifted in the alphabet
    int k = atoi(key);

    // Update k modulo 26, in case k is bigger than 26
    // because we will wrap around the alphabet.
    k = k % 26;

    // Initialize the alphabets
    string lower_alphabet = "abcdefghijklmnopqrstuvwxyz";
    string upper_alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Get from user the plaintext to be ciphered
    string plaintext = get_string("plaintext: ");
    int length = strlen(plaintext);

    // Declare the ciphertext
    char ciphertext[length + 1];

    // Go through the plaintext,
    // replacing letters by shitfing them k places in the alphabet
    // Spaces or punctuation remain the same.
    for (int i = 0; i < length; i++)
    {
        // First check if the ith character in plaintext is a letter
        if (isalpha(plaintext[i]))
        {
            // Now check if it is upper case
            if (isupper(plaintext[i]))
            {
                // Now find its position in the alphabet,
                // shift it by k, wrap around if necessary,
                // and add that character to ciphertext
                for (int j = 0; j < 26; j++)
                {
                    if (upper_alphabet[j] == plaintext[i])
                    {
                        ciphertext[i] = upper_alphabet[(j + k) % 26];
                    }
                }
            }

            // Repeat same procedure for lower case
            if (islower(plaintext[i]))
            {
                // Now find its position in the alphabet,
                // shift it by k, wrap around if necessary,
                // and add that to ciphertext
                for (int m = 0; m < 26; m++)
                {
                    if (lower_alphabet[m] == plaintext[i])
                    {
                        ciphertext[i] = lower_alphabet[(m + k) % 26];
                    }
                }
            }
        }

        // Finally, if the ith character is not a letter,
        // leave it unchanged
        else
        {
            ciphertext[i] = plaintext[i];
        }
    }
    // Finalize ciphertext with the null character
    ciphertext[length] = '\0';

    printf("ciphertext: %s\n", ciphertext);
}
