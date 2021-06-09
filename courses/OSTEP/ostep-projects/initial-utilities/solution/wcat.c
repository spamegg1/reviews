/* SOME EXPLANATIONS

This is how the main function for a command-line program works in C:
    Returns an int, indicating success (0) or failure (1),
    takes an int 'argc' which is the number of arguments, and
    takes an array 'argv' of strings (char* = string) that holds the arguments.

There can be more than one argument. Like:
    prompt> ./wcat file1.txt file2.txt

In that case, wcat should print every file. In the above example,
    argc is 3, and
    argv is ["./wcat", "file1.txt", "file2.txt"]
    argv[0] is "./wcat",
    argv[1] is "file1.txt",
    argv[2] is "file2.txt".
So we only open files for argv[1], argv[2], skipping argv[0].

EXIT_SUCCESS is 0, EXIT_FAILURE is 1. Included in stdlib.h.

Always search for info in: https://manual.cs50.io
Library functions used below (fopen, fclose, fgets) are explained there.
*/

#include <stdio.h>              // this has to be included in any file doing I/O
#include <stdlib.h>                           // needed for exit() and constants
#define SIZE 512             // size of character buffer into which to read file

int main(int argc, char *argv[]) {
    char buffer[SIZE];                                // allocate buffer of SIZE
    FILE *fp = NULL;                                             // file pointer

    // for each file name in the input, open the file
    for (int i = 1; i < argc; i++) {
        fp = fopen(argv[i], "r");                // try to open file for reading

        if (fp == NULL)            // if file does not exist, fopen returns NULL
        {
            printf("wcat: cannot open file\n");   // this exact statement needed
            exit(EXIT_FAILURE);                      // exit with error status 1
        }

        while (fgets(buffer, SIZE, fp)) {  // if there is more stuff in the file
            printf("%s", buffer);                                   // print it!
        }

        fclose(fp);                // don't forget to close the file after done!
    }
    return EXIT_SUCCESS;                      // return 0 in all non-error cases
}
