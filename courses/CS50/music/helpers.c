// Helper functions for music

#include <cs50.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include "helpers.h"

// Converts a fraction formatted as X/Y to eighths
int duration(string fraction)
{
    int numerator = fraction[0] - 48;
    int denominator = fraction[2] - 48;
    return (numerator * 8) / denominator;
}

// Calculates frequency (in Hz) of a note
int frequency(string note)
{
    int len = strlen(note);
    double n; // the exponent we need to calculate freq
    char letter = note[0]; // letter of the note
    int octave = note[len - 1] - 48; // octave of the note
    char accidental; // accidental (# or b) of the note
    if (len == 3)
    {
        accidental = note[1];
    }

    // This variable increments or decrements n depending
    // on whether accidental is sharp (#) or flat (b)
    int acc = 0;
    if (len == 3)
    {
        acc = (accidental == 'b') ? -1 : 1;
    }

    switch (letter)
    {
        case 'A':
            n = ((octave - 4) * 12) + 0 + acc;
            break;
        case 'B':
            n = ((octave - 4) * 12) + 2 + acc;
            break;
        case 'C':
            n = ((octave - 5) * 12) + 3 + acc;
            break;
        case 'D':
            n = ((octave - 5) * 12) + 5 + acc;
            break;
        case 'E':
            n = ((octave - 5) * 12) + 7 + acc;
            break;
        case 'F':
            n = ((octave - 5) * 12) + 8 + acc;
            break;
        case 'G':
            n = ((octave - 5) * 12) + 10 + acc;
            break;
    }

    return round(440 * pow(2.00, n / 12));
}

// Determines whether a string represents a rest
bool is_rest(string s)
{
    if (strcmp(s, "") == 0)
    {
        return true;
    }
    else
    {
        return false;
    }
}
