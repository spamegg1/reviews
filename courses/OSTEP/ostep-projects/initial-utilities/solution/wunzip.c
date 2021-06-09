#include <stdio.h>              // this has to be included in any file doing I/O
#include <stdlib.h>                                         // needed for exit()

int main(int argc, char *argv[]) {
    if (argc == 1) {                                        // no file specified
        printf("wunzip: file1 [file2 ...]\n");    // this exact statement needed
        exit(1);
    }

    FILE *fp = NULL;                                             // file pointer
    char character;                     // fread() will read into this character
    long count;           // used to count number of times character is repeated

    for (int i = 1; i < argc; i++) {        // there can be multiple input files
        fp = fopen(argv[i], "r");                // try to open file for reading
        if (fp == NULL) {         // fopen() returns NULL if file does not exist
            printf("wunzip: cannot open file\n");
            exit(EXIT_FAILURE);
        }

        count = 0;
        while (                            // check that file has correct format
            fread(&count, 4, 1, fp) > 0 &&            // first 4 bytes: a number
            fread(&character, 1, 1, fp) > 0          // next 1 byte: a character
            ) {
                for (int j = 0; j < count; j++) {   // for count number of times
                    printf("%c", character);        // print character (with %c)
                }
        }

        fclose(fp);                   // don't forget to close the current file!
    }
    return EXIT_SUCCESS;
}
