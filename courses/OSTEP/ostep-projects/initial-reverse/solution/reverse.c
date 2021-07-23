#include <stdio.h>              // this has to be included in any file doing I/O
#include <stdlib.h>                           // needed for exit() and constants
#include <string.h>                              // needed for string comparison
#include <sys/types.h>                                        // needed for stat
#include <sys/stat.h>                                         // needed for stat
#include <unistd.h>                                           // needed for stat

// char *filename(char *path) {
//     char *name = strchr(path, '/');
//     if (name == NULL) {
//         return path;
//     }
//     return name;
// }

// needed to detect same file under different directories
// for example: ./reverse tests/5.in tests-out/5.in should be detected as same
int samefile(char *file1, char *file2) {
    struct stat statbuf1, statbuf2;
    stat(file1, &statbuf1);
    stat(file2, &statbuf2);
    if (statbuf1.st_ino == statbuf2.st_ino) {
        return 1;
    }
    return 0;
}

// this code is repeated in all cases, so I factored it out to a function.
void reverse(FILE *in, FILE *out) {
    char *reversedlines = calloc(1, sizeof(char));// will hold infile in reverse
    char *lineptr = NULL;                                // needed for getline()
    size_t n = 0;                                        // needed for getline()
    unsigned long newsize;       // to calculate new size after adding each line

    while (getline(&lineptr, &n, in) > 0) {
        newsize = strlen(reversedlines) + strlen(lineptr) + 1;// null terminator
        reversedlines = realloc(reversedlines, newsize);
        lineptr = realloc(lineptr, newsize);
        strcat(lineptr, reversedlines);         // concat line and reversedlines
        strcpy(reversedlines, lineptr);         // copy it back to reversedlines
    }

    fprintf(out, "%s", reversedlines);  // this is the right way to write string
    //fwrite(reversedlines, sizeof(reversedlines), 1, out); // this doesn't work
    free(reversedlines);
    free(lineptr);            // remember to free memory! valgrind will complain
}

int main(int argc, char *argv[]) {
    if (argc == 1) {     // no arguments given: read from stdin, write to stdout
        reverse(stdin, stdout);
    }

    else if (argc == 2) { // infile specified: read from infile, write to stdout
        FILE *in = fopen(argv[1], "r");                    // try to open infile
        if (in == NULL) {
            fprintf(stderr, "reverse: cannot open file '%s'\n", argv[1]);
            exit(EXIT_FAILURE);
        }

        reverse(in, stdout);
        fclose(in);    // remember to close file handles! valgrind will complain
    }

    else if (argc == 3) {                  // read from infile, write to outfile
        char *infile = argv[1];
        char *outfile = argv[2];

        // input and output files are same
        if (samefile(infile, outfile)) {
            fprintf(stderr, "reverse: input and output file must differ\n");
            exit(EXIT_FAILURE);
        }

        FILE *in = fopen(infile, "r");
        FILE *out = fopen(outfile, "w");
        if (in == NULL) {
            fprintf(stderr, "reverse: cannot open file '%s'\n", infile);
            exit(EXIT_FAILURE);
        }
        if (out == NULL) {
            fprintf(stderr, "reverse: cannot open file '%s'\n", outfile);
            exit(EXIT_FAILURE);
        }

        reverse(in, out);
        fclose(in);    // remember to close file handles! valgrind will complain
        fclose(out);
    }

    else {                                                 // too many arguments
        fprintf(stderr, "usage: reverse <input> <output>\n");
        exit(EXIT_FAILURE);
    }

    return EXIT_SUCCESS;
}
