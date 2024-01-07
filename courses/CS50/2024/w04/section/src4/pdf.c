#include <cs50.h>
#include <stdint.h>
#include <stdio.h>

int main(void)
{
    // Read first four bytes in given file
    string filename = argv[1];
    FILE *f = fopen(filename, "r");
    uint8_t buffer[4];
    fread(buffer, 1, 4, f);

    // Print first four bytes in given file
    for (int i = 0; i < 4; i++)
    {
        printf("%i\n", buffer[i]);
    }
    fclose(f);
}
