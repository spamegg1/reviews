#include <stdio.h>
#include <math.h>

typedef struct
{
    int blue;
    int green;
    int red;
}
triple;

int avg(int array[], int count)
{
    int sum = 0;
    for (int i = 0; i < count; i++)
    {
        sum += array[i];
    }
    return round(sum / (float) count);
}

triple avgTriple(int blues[], int greens[], int reds[], int count)
{
    triple result;
    result.blue = avg(blues, count);
    result.green = avg(greens, count);
    result.red = avg(reds, count);
    return result;
}

int main(int argc, char *argv[])
{
    int array[] = {1, 2, 3, 4, 5};
    printf("%i\n", avg(array, 5));

    int blues[] = {1, 2, 3, 4, 5};
    int greens[] = {1, 2, 3, 4, 5};
    int reds[] = {1, 2, 3, 4, 5};
    triple x = avgTriple(blues, greens, reds, 5);
    printf("%i %i %i\n", x.blue, x.green, x.red);
}
