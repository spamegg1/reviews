#include "helpers.h"
#include <math.h>

// Convert image to grayscale
void grayscale(int height, int width, RGBTRIPLE image[height][width])
{
    // TODO
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            int blue = image[i][j].rgbtBlue;
            int green = image[i][j].rgbtGreen;
            int red = image[i][j].rgbtRed;
            int avg = round((blue + green + red) / 3.0);

            image[i][j].rgbtBlue = avg;
            image[i][j].rgbtGreen = avg;
            image[i][j].rgbtRed = avg;
        }
    }
    return;
}

// Convert image to sepia
void sepia(int height, int width, RGBTRIPLE image[height][width])
{
    // TODO
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            int blue = image[i][j].rgbtBlue;
            int green = image[i][j].rgbtGreen;
            int red = image[i][j].rgbtRed;

            int sepiaBlue = round(.272 * red + .534 * green + .131 * blue);
            int sepiaRed = round(.393 * red + .769 * green + .189 * blue);
            int sepiaGreen = round(.349 * red + .686 * green + .168 * blue);

            image[i][j].rgbtBlue = sepiaBlue > 255 ? 255 : sepiaBlue;
            image[i][j].rgbtGreen = sepiaGreen > 255 ? 255 : sepiaGreen;
            image[i][j].rgbtRed = sepiaRed > 255 ? 255 : sepiaRed;
        }
    }
    return;
}

// Reflect image horizontally
void reflect(int height, int width, RGBTRIPLE image[height][width])
{
    // TODO
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width / 2; j++)
        {
            RGBTRIPLE temp;
            temp = image[i][j];
            image[i][j] = image[i][width - 1 - j];
            image[i][width - 1 - j] = temp;
        }
    }
    return;
}

// Blur image
void blur(int height, int width, RGBTRIPLE image[height][width])
{
    // TODO
    // first create copy of image
    RGBTRIPLE imgcpy[height][width];
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            imgcpy[i][j] = image[i][j];
        }
    }

    // now do the blur on the original
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            // if we are on the top edge
            if (i == 0)
            {
                // if we are on the left edge
                if (j == 0)
                {
                    image[i][j].rgbtBlue = round((
                        imgcpy[i][j].rgbtBlue + imgcpy[i][j+1].rgbtBlue +
                        imgcpy[i+1][j].rgbtBlue + imgcpy[i+1][j+1].rgbtBlue
                    ) / 4.0);
                    image[i][j].rgbtGreen = round((
                        imgcpy[i][j].rgbtGreen + imgcpy[i][j+1].rgbtGreen +
                        imgcpy[i+1][j].rgbtGreen + imgcpy[i+1][j+1].rgbtGreen
                    ) / 4.0);
                    image[i][j].rgbtRed = round((
                        imgcpy[i][j].rgbtRed + imgcpy[i][j+1].rgbtRed +
                        imgcpy[i+1][j].rgbtRed + imgcpy[i+1][j+1].rgbtRed
                    ) / 4.0);
                }

                // if we are on the right edge
                else if (j == width - 1)
                {
                    image[i][j].rgbtBlue = round((
                        imgcpy[i][j].rgbtBlue + imgcpy[i][j-1].rgbtBlue +
                        imgcpy[i+1][j].rgbtBlue + imgcpy[i+1][j-1].rgbtBlue
                    ) / 4.0);
                    image[i][j].rgbtGreen = round((
                        imgcpy[i][j].rgbtGreen + imgcpy[i][j-1].rgbtGreen +
                        imgcpy[i+1][j].rgbtGreen + imgcpy[i+1][j-1].rgbtGreen
                    ) / 4.0);
                    image[i][j].rgbtRed = round((
                        imgcpy[i][j].rgbtRed + imgcpy[i][j-1].rgbtRed +
                        imgcpy[i+1][j].rgbtRed + imgcpy[i+1][j-1].rgbtRed
                    ) / 4.0);
                }

                // else, we are somewhere in the middle of top edge
                else
                {
                    image[i][j].rgbtBlue = round((
                        imgcpy[i][j].rgbtBlue + imgcpy[i][j+1].rgbtBlue +
                        imgcpy[i][j-1].rgbtBlue +
                        imgcpy[i+1][j].rgbtBlue + imgcpy[i+1][j+1].rgbtBlue +
                        imgcpy[i+1][j-1].rgbtBlue
                    ) / 6.0);
                    image[i][j].rgbtGreen = round((
                        imgcpy[i][j].rgbtGreen + imgcpy[i][j+1].rgbtGreen +
                        imgcpy[i][j-1].rgbtGreen +
                        imgcpy[i+1][j].rgbtGreen + imgcpy[i+1][j+1].rgbtGreen +
                        imgcpy[i+1][j-1].rgbtGreen
                    ) / 6.0);
                    image[i][j].rgbtRed = round((
                        imgcpy[i][j].rgbtRed + imgcpy[i][j+1].rgbtRed +
                        imgcpy[i][j-1].rgbtRed +
                        imgcpy[i+1][j].rgbtRed + imgcpy[i+1][j+1].rgbtRed +
                        imgcpy[i+1][j-1].rgbtRed
                    ) / 6.0);
                }
            }

            // if we are on the bottom edge
            else if (i == height - 1)
            {
                // if we are on the left edge
                if (j == 0)
                {
                    image[i][j].rgbtBlue = round((
                        imgcpy[i][j].rgbtBlue + imgcpy[i][j+1].rgbtBlue +
                        imgcpy[i-1][j].rgbtBlue + imgcpy[i-1][j+1].rgbtBlue
                    ) / 4.0);
                    image[i][j].rgbtGreen = round((
                        imgcpy[i][j].rgbtGreen + imgcpy[i][j+1].rgbtGreen +
                        imgcpy[i-1][j].rgbtGreen + imgcpy[i-1][j+1].rgbtGreen
                    ) / 4.0);
                    image[i][j].rgbtRed = round((
                        imgcpy[i][j].rgbtRed + imgcpy[i][j+1].rgbtRed +
                        imgcpy[i-1][j].rgbtRed + imgcpy[i-1][j+1].rgbtRed
                    ) / 4.0);
                }

                // if we are on the right edge
                else if (j == width - 1)
                {
                    image[i][j].rgbtBlue = round((
                        imgcpy[i][j].rgbtBlue + imgcpy[i][j-1].rgbtBlue +
                        imgcpy[i-1][j].rgbtBlue + imgcpy[i-1][j-1].rgbtBlue
                    ) / 4.0);
                    image[i][j].rgbtGreen = round((
                        imgcpy[i][j].rgbtGreen + imgcpy[i][j-1].rgbtGreen +
                        imgcpy[i-1][j].rgbtGreen + imgcpy[i-1][j-1].rgbtGreen
                    ) / 4.0);
                    image[i][j].rgbtRed = round((
                        imgcpy[i][j].rgbtRed + imgcpy[i][j-1].rgbtRed +
                        imgcpy[i-1][j].rgbtRed + imgcpy[i-1][j-1].rgbtRed
                    ) / 4.0);
                }

                // else, we are somewhere in the middle of bottom edge
                else
                {
                    image[i][j].rgbtBlue = round((
                        imgcpy[i][j].rgbtBlue + imgcpy[i][j+1].rgbtBlue +
                        imgcpy[i][j-1].rgbtBlue +
                        imgcpy[i-1][j].rgbtBlue + imgcpy[i-1][j+1].rgbtBlue +
                        imgcpy[i-1][j-1].rgbtBlue
                    ) / 6.0);
                    image[i][j].rgbtGreen = round((
                        imgcpy[i][j].rgbtGreen + imgcpy[i][j+1].rgbtGreen +
                        imgcpy[i][j-1].rgbtGreen +
                        imgcpy[i-1][j].rgbtGreen + imgcpy[i-1][j+1].rgbtGreen +
                        imgcpy[i-1][j-1].rgbtGreen
                    ) / 6.0);
                    image[i][j].rgbtRed = round((
                        imgcpy[i][j].rgbtRed + imgcpy[i][j+1].rgbtRed +
                        imgcpy[i][j-1].rgbtRed +
                        imgcpy[i-1][j].rgbtRed + imgcpy[i-1][j+1].rgbtRed +
                        imgcpy[i-1][j-1].rgbtRed
                    ) / 6.0);
                }
            }
            // else we are not on the top or bottom edge
            else
            {
                // if we are on the left edge
                if (j == 0)
                {
                    image[i][j].rgbtBlue = round((
                        imgcpy[i-1][j].rgbtBlue + imgcpy[i-1][j+1].rgbtBlue +
                        imgcpy[i][j].rgbtBlue + imgcpy[i][j+1].rgbtBlue +
                        imgcpy[i+1][j].rgbtBlue + imgcpy[i+1][j+1].rgbtBlue
                    ) / 6.0);
                    image[i][j].rgbtGreen = round((
                        imgcpy[i-1][j].rgbtGreen + imgcpy[i-1][j+1].rgbtGreen +
                        imgcpy[i][j].rgbtGreen + imgcpy[i][j+1].rgbtGreen +
                        imgcpy[i+1][j].rgbtGreen + imgcpy[i+1][j+1].rgbtGreen
                    ) / 6.0);
                    image[i][j].rgbtRed = round((
                        imgcpy[i-1][j].rgbtRed + imgcpy[i-1][j+1].rgbtRed +
                        imgcpy[i][j].rgbtRed + imgcpy[i][j+1].rgbtRed +
                        imgcpy[i+1][j].rgbtRed + imgcpy[i+1][j+1].rgbtRed
                    ) / 6.0);
                }

                // if we are on the right edge
                else if (j == width - 1)
                {
                    image[i][j].rgbtBlue = round((
                        imgcpy[i-1][j].rgbtBlue + imgcpy[i-1][j-1].rgbtBlue +
                        imgcpy[i][j].rgbtBlue + imgcpy[i][j-1].rgbtBlue +
                        imgcpy[i+1][j].rgbtBlue + imgcpy[i+1][j-1].rgbtBlue
                    ) / 6.0);
                    image[i][j].rgbtGreen = round((
                        imgcpy[i-1][j].rgbtGreen + imgcpy[i-1][j-1].rgbtGreen +
                        imgcpy[i][j].rgbtGreen + imgcpy[i][j-1].rgbtGreen +
                        imgcpy[i+1][j].rgbtGreen + imgcpy[i+1][j-1].rgbtGreen
                    ) / 6.0);
                    image[i][j].rgbtRed = round((
                        imgcpy[i-1][j].rgbtRed + imgcpy[i-1][j-1].rgbtRed +
                        imgcpy[i][j].rgbtRed + imgcpy[i][j-1].rgbtRed +
                        imgcpy[i+1][j].rgbtRed + imgcpy[i+1][j-1].rgbtRed
                    ) / 6.0);
                }

                // else, we are somewhere in the middle
                else
                {
                    image[i][j].rgbtBlue = round((
                        imgcpy[i-1][j-1].rgbtBlue + imgcpy[i-1][j].rgbtBlue +
                        imgcpy[i-1][j+1].rgbtBlue +
                        imgcpy[i][j-1].rgbtBlue + imgcpy[i][j].rgbtBlue +
                        imgcpy[i][j+1].rgbtBlue +
                        imgcpy[i+1][j-1].rgbtBlue + imgcpy[i+1][j].rgbtBlue +
                        imgcpy[i+1][j+1].rgbtBlue
                    ) / 9.0);
                    image[i][j].rgbtGreen = round((
                        imgcpy[i-1][j-1].rgbtGreen + imgcpy[i-1][j].rgbtGreen +
                        imgcpy[i-1][j+1].rgbtGreen +
                        imgcpy[i][j-1].rgbtGreen + imgcpy[i][j].rgbtGreen +
                        imgcpy[i][j+1].rgbtGreen +
                        imgcpy[i+1][j-1].rgbtGreen + imgcpy[i+1][j].rgbtGreen +
                        imgcpy[i+1][j+1].rgbtGreen
                    ) / 9.0);
                    image[i][j].rgbtRed = round((
                        imgcpy[i-1][j-1].rgbtRed + imgcpy[i-1][j].rgbtRed +
                        imgcpy[i-1][j+1].rgbtRed +
                        imgcpy[i][j-1].rgbtRed + imgcpy[i][j].rgbtRed +
                        imgcpy[i][j+1].rgbtRed +
                        imgcpy[i+1][j-1].rgbtRed + imgcpy[i+1][j].rgbtRed +
                        imgcpy[i+1][j+1].rgbtRed
                    ) / 9.0);
                }
            }
        }
    }
    return;
}
