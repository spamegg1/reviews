/* First read wcat.c for explanations.
clang -o wgrep wgrep.c -Wall -Werror */

#include <stdio.h>              // this has to be included in any file doing I/O
#include <stdlib.h>                                         // needed for exit()
#include <string.h>                              // needed for string comparison

int main(int argc, char *argv[]) {
    if (argc == 1) {                                 // no search term specified
        printf("wgrep: searchterm [file ...]\n");
        exit(1);
    }

    char *searchterm = argv[1];
    char *lineptr = NULL;               // setting up, in order to use getline()
    size_t size = 0;                    // setting up, in order to use getline()

    if (argc == 2) {  // search term specified, but no file specified, use stdin
        while (getline(&lineptr, &size, stdin) != -1) {    // if there is a line
            if (strstr(lineptr, searchterm)) {           // search line for term
                printf("%s", lineptr);       // if term is found, print the line
            }
        }
    }

    else {                        // search term and at least one file specified
        FILE *fp = NULL;                                         // file pointer

        for (int i = 2; i < argc; i++) {             // files start with argv[2]
            fp = fopen(argv[i], "r");            // try to open file for reading

            if (fp == NULL)        // if file does not exist, fopen returns NULL
            {
                printf("wgrep: cannot open file\n");    // this statement needed
                exit(1);                               // exit with error status
            }

            while (getline(&lineptr, &size, fp) != -1) {     // similar to above
                if (strstr(lineptr, searchterm)) {
                    printf("%s", lineptr);
                }
            }
        }
    }

    return 0;                                                        // success!
}
