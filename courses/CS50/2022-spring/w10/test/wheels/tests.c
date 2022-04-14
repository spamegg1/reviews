#include <assert.h>
#include <stdbool.h>
#include <string.h>

#include "ctype.h"
#include "tests.h"

void test_isalpha(void)
{
    assert(isalpha('A') == true);
    assert(isalpha('Z') == true);
    assert(isalpha('a') == true);
    assert(isalpha('z') == true);
    assert(isalpha('0') == false);
    assert(isalpha('9') == false);
    assert(isalpha('+') == false);
    assert(isalpha('/') == false);
}

void test_isdigit(void)
{
    // TODO
    assert(isdigit('0') == true);
    assert(isdigit('9') == true);
    assert(isdigit('a') == false);
    assert(isdigit('z') == false);
    assert(isdigit('A') == false);
    assert(isdigit('Z') == false);
    assert(isdigit('+') == false);
    assert(isdigit('/') == false);
}

void test_isalnum(void)
{
    // TODO
    assert(isalnum('0') == true);
    assert(isalnum('9') == true);
    assert(isalnum('a') == true);
    assert(isalnum('z') == true);
    assert(isalnum('A') == true);
    assert(isalnum('Z') == true);
    assert(isalnum('*') == false);
    assert(isalnum('+') == false);
}

void test_areupper(void)
{
    // TODO
    assert(areupper("0") == false);
    assert(areupper("9") == false);
    assert(areupper("a") == false);
    assert(areupper("z") == false);
    assert(areupper("A") == true);
    assert(areupper("Z") == true);
    assert(areupper("aZ") == false);
    assert(areupper("Az") == false);
    assert(areupper("zA") == false);
    assert(areupper("Za") == false);
    assert(areupper("*") == false);
    assert(areupper("+") == false);
    assert(areupper("a/Z") == false);
    assert(areupper("AZ") == true);
    assert(areupper("ZA") == true);
    assert(areupper("ABCDEFG") == true);
}
