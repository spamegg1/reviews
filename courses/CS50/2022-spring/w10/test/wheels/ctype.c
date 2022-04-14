#include <string.h>

#include "ctype.h"

// Return true if c is alphabetical (uppercase or lowercase), else false
bool isalpha(char c)
{
    if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    {
        return true;
    }
    else
    {
        return false;
    }
}

// Return true if c is a decimal digit, else false
bool isdigit(char c)
{
    // TODO
    return '0' <= c && c <= '9';
}

// Return true if c is alphabetical (uppercase or lowercase)
// or numeric (i.e., a decimal digit), else false
bool isalnum(char c)
{
    // TODO
    return isdigit(c) || isalpha(c);
}

// Return true if every char in s is alphabetical and uppercase, else false
bool areupper(char *s)
{
    for (int i = 0; i < strlen(s); i++)
    {
        if (!('A' <= s[i] && s[i] <= 'Z'))
        {
            return false;
        }
    }
    return true;
}
