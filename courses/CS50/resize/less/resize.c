// Resizes a BMP file

#include <stdio.h>
#include <stdlib.h>

#include "bmp.h"

int main(int argc, char *argv[])
{
    // ensure proper usage
    if (argc != 4)
    {
        fprintf(stderr, "Usage: resize n infile outfile\n");
        return 1;
    }

    // Get resize factor, ensure proper usage
    int n = atoi(argv[1]);

    if (n <= 0 || n > 100)
    {
        fprintf(stderr, "n must be positive and <= 100\n");
        return 1;
    }

    // remember filenames
    char *infile = argv[2];
    char *outfile = argv[3];

    // open input file
    FILE *inptr = fopen(infile, "r");
    if (inptr == NULL)
    {
        fprintf(stderr, "Could not open %s.\n", infile);
        return 1;
    }

    // open output file
    FILE *outptr = fopen(outfile, "w");
    if (outptr == NULL)
    {
        fclose(inptr);
        fprintf(stderr, "Could not create %s.\n", outfile);
        return 1;
    }

    // read infile's BITMAPFILEHEADER
    BITMAPFILEHEADER bf;
    fread(&bf, sizeof(BITMAPFILEHEADER), 1, inptr);

    // read infile's BITMAPINFOHEADER
    BITMAPINFOHEADER bi;
    fread(&bi, sizeof(BITMAPINFOHEADER), 1, inptr);

    // ensure infile is (likely) a 24-bit uncompressed BMP 4.0
    if (bf.bfType != 0x4d42 || bf.bfOffBits != 54 || bi.biSize != 40 ||
        bi.biBitCount != 24 || bi.biCompression != 0)
    {
        fclose(outptr);
        fclose(inptr);
        fprintf(stderr, "Unsupported file format.\n");
        return 1;
    }

////// OUTFILE'S HEADERS ///////////////////////////////////////////////
    // define new headers for OUTFILE (initially the same as infile's)
    BITMAPFILEHEADER new_bf = bf;
    BITMAPINFOHEADER new_bi = bi;

    // Resize width and height (in pixels) in OUTFILE's BITMAPINFOHEADER by n
    new_bi.biWidth = n * bi.biWidth;
    new_bi.biHeight = n * bi.biHeight;

    // determine the INFILE's padding for scanlines
    int infile_padding = (4 - (bi.biWidth * sizeof(RGBTRIPLE)) % 4) % 4;

    // determine the OUTFILE's padding for scanlines
    int outfile_padding = (4 - (new_bi.biWidth * sizeof(RGBTRIPLE)) % 4) % 4;

    // Recalculate biSizeImage (in bytes) in OUTFILE's BITMAPINFOHEADER
    new_bi.biSizeImage = ((sizeof(RGBTRIPLE) * new_bi.biWidth + outfile_padding) * abs(new_bi.biHeight));

    // Recalculate bfSize (in bytes) in OUTFILE's BITMAPFILEHEADER
    new_bf.bfSize = new_bi.biSizeImage + sizeof(BITMAPFILEHEADER) + sizeof(BITMAPINFOHEADER);

    // write outfile's BITMAPFILEHEADER
    fwrite(&new_bf, sizeof(BITMAPFILEHEADER), 1, outptr);

    // write outfile's BITMAPINFOHEADER
    fwrite(&new_bi, sizeof(BITMAPINFOHEADER), 1, outptr);
////// END OF OUTFILE'S HEADERS ///////////////////////////////////////


    // FOR HORIZONTAL RESIZING:
    // iterate over infile's scanlines
    for (int i = 0, biHeight = abs(bi.biHeight); i < biHeight; i++)
    {
        // We keep track of each horizontally resized scanline
        // for vertical resizing later
        RGBTRIPLE array[new_bi.biWidth];
        LONG array_index = 0;

        // iterate over pixels in scanline FOR HORIZONTAL RESIZING
        for (int j = 0; j < bi.biWidth; j++)
        {
            // temporary storage
            RGBTRIPLE triple;

            // read RGB triple from infile
            fread(&triple, sizeof(RGBTRIPLE), 1, inptr);

            // store the same triple *n times* in our array
            for (int a = 0; a < n; a++)
            {
                array[array_index + a] = triple;
            }
            // increment array_index by n
            array_index += n;
        }

        // FOR VERTICAL RESIZING:
        // For n times, write array to outfile, then add OUTFILE's padding
        for (int b = 0; b < n; b++)
        {
            // write the array to outfile
            fwrite(&array, sizeof(RGBTRIPLE) * new_bi.biWidth, 1, outptr);

            // then add OUTFILE's padding, if any
            for (int k = 0; k < outfile_padding; k++)
            {
                fputc(0x00, outptr);
            }
        }

        // skip over INFILE's padding, if any
        fseek(inptr, infile_padding, SEEK_CUR);
    }

    // close infile
    fclose(inptr);

    // close outfile
    fclose(outptr);

    // success
    return 0;
}
