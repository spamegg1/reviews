#include <stdio.h>
#include <cs50.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>

string LOWER = "abcdefghijklmnopqrstuvwxyz";
string UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
bool invalid(string key);
void str_tolower(string key);

int main(int argc, string argv[])
{
    // Give error if there is not exactly one command line argument
    if (argc != 2)
    {
        printf("Usage: ./substitution key\n");
        return 1;
    }

    // Store the key in a variable, convert all to lowercase
    string key = argv[1];
    str_tolower(key);

    // Give error if key is invalid
    if (invalid(key))
    {
        return 1;
    }

    // Get from user the plaintext to be ciphered
    string plaintext = get_string("plaintext: ");
    int length = strlen(plaintext);

    // Declare the ciphertext, terminate with the null character
    char ciphertext[length + 1];
    ciphertext[length] = '\0';

    // Go through the plaintext
    for (int i = 0; i < length; i++)
    {
        // First check if the ith character in plaintext is a letter
        if (isalpha(plaintext[i]))
        {
            // Now check if it is upper case
            if (isupper(plaintext[i]))
            {
                // Now find its position in the alphabet,
                // replace it by corresponding letter in key
                for (int k = 0; k < 26; k++)
                {
                    if (UPPER[k] == plaintext[i])
                    {
                        ciphertext[i] = toupper(key[k]);
                        break;
                    }
                }
            }

            // Repeat same procedure for lower case
            else if (islower(plaintext[i]))
            {
                // Now find its position in the alphabet,
                // replace it by corresponding letter in key
                for (int k = 0; k < 26; k++)
                {
                    if (LOWER[k] == plaintext[i])
                    {
                        ciphertext[i] = key[k];
                        break;
                    }
                }
            }
        }

        // Finally, if the ith character is not a letter, leave it unchanged
        // Also we do not move forward in the shift array this time,
        // as no letters from keyword were used. Variable j remains unchanged.
        else
        {
            ciphertext[i] = plaintext[i];
        }
    }

    // Print the completed ciphertext
    printf("ciphertext: %s\n", ciphertext);
    return 0;
}

// returns true if key is invalid, false if key is good.
// Assumes key is all lowercase.
bool invalid(string key)
{
    // check if key is exactly 26 characters long
    if (strlen(key) != 26)
    {
        printf("Key must contain 26 characters.\n");
        return true;
    }

    // check if key contains non-alphabetic characters
    for (int i = 0; i < 26; i++)
    {
        if (!isalpha(key[i]))
        {
            printf("Key must contain only alphabetical characters.\n");
            return true;
        }
    }

    // check if key contains each lowercase letter exactly once
    for (int i = 0; i < 26; i++)
    {
        int count = 0;
        for (int j = 0; j < 26; j++)
        {
            if (LOWER[i] == key[j])
            {
                count++;
            }
        }
        if (count != 1)
        {
            printf("Key must contain each letter exactly once.\n");
            return true;
        }
    }

    // passed all checks, key is valid.
    return false;
}

// convert string to lowercase, in place
void str_tolower(string key)
{
    for (int i = 0; i < strlen(key); i++)
    {
        key[i] = tolower(key[i]);
    }
}
