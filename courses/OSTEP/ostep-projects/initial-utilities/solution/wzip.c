/* Compile with: gcc -o wzip wzip.c -Wall -Werror */
#include <stdio.h>              // this has to be included in any file doing I/O
#include <stdlib.h>                                         // needed for exit()
#include <string.h>                     // needed for string comparison and copy

int main(int argc, char *argv[]) {
    if (argc == 1) {                                        // no file specified
        printf("wzip: file1 [file2 ...]\n");      // this exact statement needed
        exit(EXIT_FAILURE);
    }

    FILE *fp = NULL;                                       // input file pointer
    char prev[2] = "";                  // fread() will read into this character
    char next[2] = "";                // fwrite() will write into this character
    long count = 0;       // used to count number of times character is repeated

    for (int i = 1; i < argc; i++) {        // there can be multiple input files
        fp = fopen(argv[i], "r");                // try to open file for reading

        if (fp == NULL) {         // fopen() returns NULL if file does not exist
            printf("wzip: cannot read file\n");
            exit(EXIT_FAILURE);
        }

        while (fread(next, 1, 1, fp)) {        // there is more to read in file
            if (strcmp(prev, next) == 0) {   // is it the same as previous char?
                count++;
            }
            else {        // we encountered a new char. Let's write the last one
                if (prev[0] != '\0') {        // if prev is not the empty string
                    fwrite(&count, 4, 1, stdout);       // an integer is 4 bytes
                    fwrite(prev, 1, 1, stdout);              // a char is 1 byte
                }
                strcpy(prev, next);                 // copy new char to previous
                count = 1;                       // start counting new character
            }
        }

        fclose(fp);                   // don't forget to close the current file!
    }

    // since while loop checks if next char exists, then writes prev char,
    // we have to write the final char that was encountered:
    fwrite(&count, 4, 1, stdout);            // write the last character's count
    fwrite(prev, 1, 1, stdout);                      // write the last character

    return EXIT_SUCCESS;
}
