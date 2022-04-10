#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include "dictionary.h"

// Loads dictionary into memory, returning true if successful, else false
bool load(const char* dictionary)
{
    // Initialize word count
    word_count = 0;

    // calloc will default the reserved memory values inside a node to 0
    // This means that the pointers in the children[] array are NULL by default
    // and the boolean is_word in a node is FALSE by default
    // This is the pointer (root) to the top of the dictionary trie structure
    // root was defined in dictionary.h, initialized here
    root = calloc(sizeof(node), 1);

    // Open dictionary for reading
    FILE* file = fopen(dictionary, "r");

    // Check if file pointer is NULL (i.e. file opening failed)
    if (file == NULL)
    {
        printf("Failed to open dictionary\n");
        return false;
    }

    // Check if root pointer is NULL (i.e. memory allocation failed)
    if (root == NULL)
    {
        fclose(file);
        return false;
    }

    // Keep track of current node traversing the trie structure, starting at top
    node* current = root;

    // This variable denotes position of character in alphabet (0-26)
    int pos = -1;

    // Load each word in dictionary into trie structure, letter by letter
    for (int c = fgetc(file); c != EOF; c = fgetc(file))
    {
        // Find location of character c in alphabet (i.e. in children[])
        // Constants APOSTR and ASCII_a are defined in dictionary.h
        pos = (c == '\'') ? APOSTR : (c - ASCII_a);

        // Check for newline character
        if (c == '\n')
        {
            // We must have reached end of a word in dictionary
            current->is_word = true;

            // For the next word in dictionary, we need to go back to the root
            current = root;

            // Increment word count
            word_count++;
        }

        // if c has never been seen in this position
        else if (current->children[pos] == NULL)
        {
            // Make pointer to a new node, have children[pos] point to it
            node* newnode = calloc(sizeof(node), 1);

            // Check if newnode pointer is NULL (i.e. memory allocation failed)
            if (newnode == NULL)
            {
                unload();
                fclose(file);
                return false;
            }

            // Have current node's children[pos] point to newnode
            // so that the letters of the word can continue
            current->children[pos] = newnode;

            // Move forward in trie, change current node to newnode
            current = newnode;
        }
        else // c was seen before, move to new node and continue
        {
            // follow where children[pos] is pointing, that's the new current node
            current = current->children[pos];
        }
    }

    // Dictionary successfully loaded, return true
    fclose(file);
    return true;
}

// Recursive function for unload(), works top to bottom
// Recursively frees all children by following pointers from children[] to nodes,
// repeating until NULL pointers in children[] are reached.
bool recursive_free(node* ptr)
{
    // pointer to traverse the trie
    node* current = ptr;

    // Recursively call the function on the nodes that
    // are pointed to by the children of current node
    for (int i = 0; i < ALPHABET; i++)
    {
        if (current->children[i] != NULL)
        {
            // free all the children!
            recursive_free(current->children[i]);
        }
    }
    // finally free the current node as well
    free(ptr);
    return true;
}

// Unloads dictionary from memory, returning true if successful, else false
// Starts from root of the trie, working top to bottom, recursively frees
// all children by following pointers from children[] to nodes,
// repeating until NULL pointers in children[] are reached.
bool unload(void)
{
    // For a recursive solution, we need unload to take nodes as parameter
    // But we are not allowed to change the declaration of unload
    // So we use function defined above, called at root,
    // and root is defined globally in dictionary.h, then initialized in load()
    return recursive_free(root);
}

// Returns number of words in dictionary if loaded, else 0 if not yet loaded
unsigned int size(void)
{
    // Yet another void function that takes no parameters, like unload()
    // For this, we had to declare word_count in dictionary.h as global
    // variable, and we are assuming load() was called before size()
    return word_count;
}

// Returns true if word is in dictionary, else false
// Assumes that dictionary is already loaded
bool check(const char* word)
{
    // Find length of word
    int len = strlen(word);

    // This variable denotes position of character in alphabet (0-26)
    int pos = -1;

    // Keep track of current node traversing the trie structure, starting at top
    node* current = root;

    // For each character in word, find corresponding element in children[]
    for (int i = 0; i < len; i++)
    {
        // Find location of character tolower(word[i]) in children[]
        // This is the same as in load(), except case insensitive
        pos = (tolower(word[i]) == '\'') ? APOSTR : (tolower(word[i]) - ASCII_a);

        // If children[pos] is NULL, word is not in dictionary, so MISSPELLED
        if (current->children[pos] == NULL)
        {
            return false;
        }

        // Otherwise, move on to the next character in word
        else
        {
            // Update current node, traversing down, for next letter in word
            current = current->children[pos];
        }

    }
    // If we are at the end of the input word, and is_word is true
    if (current->is_word == true)
    {
        // word is in dictionary, return true
        return true;
    }
    else
    {
        return false;
    }
}

