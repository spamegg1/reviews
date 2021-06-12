#include "types.h"
#include "user.h"


int main(int argc, char *argv[])
{
    char *p = 0;
    printf(1, "The first four bytes of the null program instructions are:\n");
	printf(1, "(ignore leading FFFFFF)\n");
    printf(1, "%x ", (uint) *p);
    printf(1, "%x ", (uint) *(p + 1));
    printf(1, "%x ", (uint) *(p + 2));
    printf(1, "%x\n", (uint) *(p + 3));
    exit();
}
