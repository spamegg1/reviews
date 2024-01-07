// Incorrectly gets a string from user using scanf; compile with -Wno-uninitialized

#include <stdio.h>

int main(void)
{
    char *s;
    printf("s: ");
    scanf("%s", s);
    printf("s: %s\n", s);
}
