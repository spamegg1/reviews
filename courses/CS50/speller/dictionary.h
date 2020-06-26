// Declares a dictionary's functionality

#ifndef DICTIONARY_H
#define DICTIONARY_H

#include <stdbool.h>

// Maximum length for a word
// (e.g., pneumonoultramicroscopicsilicovolcanoconiosis)
#define LENGTH 45

// Size of our alphabet: letters a-z plus the apostrophe
#define ALPHABET 27

// Apostrophe is designated position 26 by our choice
#define APOSTR 26

// Letters a-z are 0-25. Do ASCII conversion, a is 97,
// so subtract magic number 97, so that a is 0, z is 25
#define ASCII_a 97

// Variable to keep track of dictionary's word count in load()
int word_count;

// Create a trie structure (with nodes) for loading
// the dictionary and for finding words in dictionary
// Nodes have arrays of size 27 b/c of 26 letters + apostrophe '\''
typedef struct node
{
    bool is_word;
    struct node* children[27]; // an array of pointer-to-node's
}
node;

// This is the pointer (root) to the top of the dictionary trie structure
// Will be initialized and used in dictionary.c (in load and unload functions)
// It was necessary to declare it here in order to make unload() recursive
// because unload() takes no parameters and we are not allowed to change it.
node* root;

// Prototypes
bool check(const char *word);
bool load(const char *dictionary);
unsigned int size(void);
bool unload(void);

#endif // DICTIONARY_H
