#include "helpers.h"
#include <math.h>

RGBTRIPLE BLACK = { .rgbtBlue = 0, .rgbtGreen = 0, .rgbtRed = 0};

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

    // now do the kernels on the original
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

// Detect edges
void edges(int height, int width, RGBTRIPLE image[height][width])
{
    // TODO
    // first create copy of image, with black pixels around the edges:
    RGBTRIPLE imgcpy[height + 2][width + 2];

    // top and bottom edges all black
    for (int j = 0; j < width + 2; j++)
    {
        imgcpy[0][j] = BLACK;
        imgcpy[height + 1][j] = BLACK;
    }

    // left and right edges all black
    for (int i = 1; i < height + 1; i++)
    {
        imgcpy[i][0] = BLACK;
        imgcpy[i][width + 1] = BLACK;
    }

    // insides of the black edges, same as image
    for (int i = 1; i < height + 1; i++)
    {
        for (int j = 1; j < width + 1; j++)
        {
            imgcpy[i][j] = image[i - 1][j - 1];
        }
    }

    // now do the edges on the original
    for (int i = 1; i < height + 1; i++)
    {
        for (int j = 1; j < width + 1; j++)
        {
            int gxb = (-1) * imgcpy[i-1][j-1].rgbtBlue +
                      (1)  * imgcpy[i-1][j+1].rgbtBlue +
                      (-2) * imgcpy[i][j-1].rgbtBlue   +
                      (2)  * imgcpy[i][j+1].rgbtBlue   +
                      (-1) * imgcpy[i+1][j-1].rgbtBlue +
                      (1)  * imgcpy[i+1][j+1].rgbtBlue;
            int gyb = (-1) * imgcpy[i-1][j-1].rgbtBlue +
                      (-2) * imgcpy[i-1][j].rgbtBlue   +
                      (-1) * imgcpy[i-1][j+1].rgbtBlue +
                      (1)  * imgcpy[i+1][j-1].rgbtBlue +
                      (2)  * imgcpy[i+1][j].rgbtBlue   +
                      (1)  * imgcpy[i+1][j+1].rgbtBlue;
            int resb = round(sqrt(gxb * gxb + gyb * gyb));
            image[i - 1][j - 1].rgbtBlue = resb > 255 ? 255 : resb;

            int gxg = (-1) * imgcpy[i-1][j-1].rgbtGreen +
                      (1)  * imgcpy[i-1][j+1].rgbtGreen +
                      (-2) * imgcpy[i][j-1].rgbtGreen   +
                      (2)  * imgcpy[i][j+1].rgbtGreen   +
                      (-1) * imgcpy[i+1][j-1].rgbtGreen +
                      (1)  * imgcpy[i+1][j+1].rgbtGreen;
            int gyg = (-1) * imgcpy[i-1][j-1].rgbtGreen +
                      (-2) * imgcpy[i-1][j].rgbtGreen   +
                      (-1) * imgcpy[i-1][j+1].rgbtGreen +
                      (1)  * imgcpy[i+1][j-1].rgbtGreen +
                      (2)  * imgcpy[i+1][j].rgbtGreen   +
                      (1)  * imgcpy[i+1][j+1].rgbtGreen;
            int resg = round(sqrt(gxg * gxg + gyg * gyg));
            image[i - 1][j - 1].rgbtGreen = resg > 255 ? 255 : resg;

            int gxr = (-1) * imgcpy[i-1][j-1].rgbtRed +
                      (1)  * imgcpy[i-1][j+1].rgbtRed +
                      (-2) * imgcpy[i][j-1].rgbtRed   +
                      (2)  * imgcpy[i][j+1].rgbtRed   +
                      (-1) * imgcpy[i+1][j-1].rgbtRed +
                      (1)  * imgcpy[i+1][j+1].rgbtRed;
            int gyr = (-1) * imgcpy[i-1][j-1].rgbtRed +
                      (-2) * imgcpy[i-1][j].rgbtRed   +
                      (-1) * imgcpy[i-1][j+1].rgbtRed +
                      (1)  * imgcpy[i+1][j-1].rgbtRed +
                      (2)  * imgcpy[i+1][j].rgbtRed   +
                      (1)  * imgcpy[i+1][j+1].rgbtRed;
            int resr = round(sqrt(gxr * gxr + gyr * gyr));
            image[i - 1][j - 1].rgbtRed = resr > 255 ? 255 : resr;
        }
    }
    return;
}
