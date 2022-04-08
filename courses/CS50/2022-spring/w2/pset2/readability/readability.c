#include <cs50.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <math.h>

// must compile with: clang readability.c -o readability -lcs50 -lm

int count_letters(string text);
int count_words(string text);
int count_sentences(string text);
bool isperiod(char letter);
float coleman_liau(int letters, int words, int sentences);
void print_score(float score);

int main(void)
{
    // get input
    string text = get_string("Text: ");

    // calculate letter, word, sentence count
    int letters = count_letters(text);
    int words = count_words(text);
    int sentences = count_sentences(text);

    // calculate index and print result
    float score = coleman_liau(letters, words, sentences);
    print_score(score);

    return 0;
}

int count_letters(string text)
{
    int count = 0;
    for (int i = 0; i < strlen(text); i++)
    {
        if (isalpha(text[i]))
        {
            count ++;
        }
    }
    return count;
}

// Assume that a sentence will not start or end with a space, and
// assume that a sentence will not have multiple spaces in a row
int count_words(string text)
{
    int count = 1;
    for (int i = 0; i < strlen(text); i++)
    {
        if (isspace(text[i]))
        {
            count++;
        }
    }
    return count;
}

// Assume that a sentence will not start or end with a space, and
// assume that a sentence will not have multiple spaces in a row
int count_sentences(string text)
{
    int count = 0;
    for (int i = 0; i < strlen(text); i++)
    {
        if (isperiod(text[i]))
        {
            count++;
        }
    }
    return count;
}

bool isperiod(char letter)
{
    return letter == '.' || letter == '?' || letter == '!';
}

float coleman_liau(int letters, int words, int sentences)
{
    float L = ((float) letters / (float) words) * 100.0;
    float S = ((float) sentences / (float) words) * 100.0;
    return 0.0588 * L - 0.296 * S - 15.8;
}

void print_score(float score)
{
    if (score >= 16.0)
    {
        printf("Grade 16+\n");
    }
    else if (score < 1.0)
    {
        printf("Before Grade 1\n");
    }
    else
    {
        printf("Grade %i\n", (int) round(score));
    }
}
