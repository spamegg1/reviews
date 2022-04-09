#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

int main(int argc, char *argv[])
{
    // ensure proper usage
    if (argc != 2)
    {
        fprintf(stderr, "Usage: recover rawfile\n");
        return 1;
    }

    // remember filename
    char *infile = argv[1];

    // open input file
    FILE *inptr = fopen(infile, "r");
    if (inptr == NULL)
    {
        fprintf(stderr, "Could not open %s.\n", infile);
        return 2;
    }

    // Count the number of JPGs found
    int jpeg_count = -1;

    // Detects header block
    int headerCheck;
    // Detects non-header jpeg block
    int jpegCheck;

    // Declare buffer to read raw file into
    uint8_t buffer[512];

    //create output file
    char filename[8];
    FILE *img = NULL;

    // This is the main loop for finding and writing JPEGs
    while (fread(buffer, 1, 512, inptr) == 512)
    {
        // Check if we are at the start of a new JPEG
        if (buffer[0] == 0xff &&
            buffer[1] == 0xd8 &&
            buffer[2] == 0xff &&
            (buffer[3] & 0xf0) == 0xe0) //bitwise addition
        {
            // JPEG Header block found
            headerCheck = 1;
            // Increment JPEG count
            jpeg_count++;

            // Check if we have already found a JPEG before
            // This means we are at the end of a JPEG
            // and the start of another JPEG
            if (jpegCheck == 1)
            {
                // We need to close previous JPEG file,
                fclose(img);

                // Reset jpegCheck to 0
                jpegCheck = 0;

                // and open a new one, name the new JPEG
                sprintf(filename, "%03i.jpg", jpeg_count);

                // Open a new file with that name
                img = fopen(filename, "w");
            }
            else // We found our very FIRST JPEG
            {
                // Name the first JPEG as "000.jpg"
                sprintf(filename, "%03i.jpg", jpeg_count);

                // Open a new file with that name
                img = fopen(filename, "w");
            }
        }

        if (headerCheck == 1) // If we are NOT at the start of a new JPEG
        {
            // The current 512 bytes belong to the currently opened JPEG
            // So we are in the middle of a JPEG
            jpegCheck = 1;

            // so keep writing into it
            fwrite(buffer, 1, 512, img);
        }

    }// end of while loop

    // Now close all remaining files
    fclose(img);
    fclose(inptr);

    // success
    return 0;

}// end of main
