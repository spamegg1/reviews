#include <stdio.h>
#include <cs50.h>
#include <math.h>

int main(void)
{
    // Get input from user
    long long cc_number = get_long_long("Number: ");
    // Ask for input again if necessary
    while(cc_number <= 0)
    {
        cc_number = get_long_long("Number: ");
    }

    // Calculate length of number to decide if it is valid
    int length = 0;
    long long copy_number = cc_number;
    while (copy_number > 0)
    {
        copy_number /= 10;
        length++;
    }

    // Print INVALID if the length is not a credit card length
    if (length != 13 && length != 15 && length != 16)
    {
        printf("INVALID\n");
        return 0;
    }

    // Print INVALID if the length is OK; but the number does not
    // comply with company definitions
    long long first_two_digits;
    long long first_digit;

    // This is for AMEX
    if (length == 15)
    {
        first_two_digits = cc_number / 10000000000000;
        if (first_two_digits != 34 && first_two_digits != 37)
        {
            printf("INVALID\n");
            return 0;
        }
    }

    // This is for MASTERCARD or VISA (length 16)
    if (length == 16)
    {
        first_digit = cc_number / 1000000000000000;
        first_two_digits = cc_number / 100000000000000;
        if (first_two_digits != 51 && first_two_digits != 52 &&
        first_two_digits != 53 && first_two_digits != 54 &&
        first_two_digits != 55 && first_digit != 4)
        {
            printf("INVALID\n");
            return 0;
        }
    }

    // This is for VISA
    if (length == 13)
    {
        first_digit = cc_number / 1000000000000;

        if (first_digit != 4)
        {
            printf("INVALID\n");
            return 0;
        }
    }

    // Now we know we have a number that fits one of
    // company definitions and has 13, 15 or 16 digits.
    // Now calculate the digits (counted right to left).
    int digit1 = cc_number % 10;
    int digit2 = (cc_number / 10) % 10;
    int digit3 = (cc_number / 100) % 10;
    int digit4 = (cc_number / 1000) % 10;
    int digit5 = (cc_number / 10000) % 10;
    int digit6 = (cc_number / 100000) % 10;
    int digit7 = (cc_number / 1000000) % 10;
    int digit8 = (cc_number / 10000000) % 10;
    int digit9 = (cc_number / 100000000) % 10;
    int digit10 = (cc_number / 1000000000) % 10;
    int digit11 = (cc_number / 10000000000) % 10;
    int digit12 = (cc_number / 100000000000) % 10;
    int digit13 = (cc_number / 1000000000000) % 10;
    int digit14;
    int digit15;
    int digit16;

    if (length == 15 || length == 16)
    {
        digit14 = (cc_number / 10000000000000) % 10;
        digit15 = (cc_number / 100000000000000) % 10;
    }

    if (length == 16)
    {
        digit16 = (cc_number / 1000000000000000) % 10;
    }

    // Now we can verify the checksum
    // Multiply every other digit by 2, starting with second-to-last digit
    // then add the digits of these doubles (which are 0-18)
    int sum = 0;
    sum += (digit2 * 2) % 10;
    sum += (digit2 * 2) / 10;
    sum += (digit4 * 2) % 10;
    sum += (digit4 * 2) / 10;
    sum += (digit6 * 2) % 10;
    sum += (digit6 * 2) / 10;
    sum += (digit8 * 2) % 10;
    sum += (digit8 * 2) / 10;
    sum += (digit10 * 2) % 10;
    sum += (digit10 * 2) / 10;
    sum += (digit12 * 2) % 10;
    sum += (digit12 * 2) / 10;

    if (length == 15 || length == 16)
    {
        sum += (digit14 * 2) % 10;
        sum += (digit14 * 2) / 10;
    }
    if (length == 16)
    {
        sum += (digit16 * 2) % 10;
        sum += (digit16 * 2) / 10;
    }

    // Now add the remaining digits
    sum += digit1;
    sum += digit3;
    sum += digit5;
    sum += digit7;
    sum += digit9;
    sum += digit11;
    sum += digit13;

    if (length == 15 || length == 16)
    {
        sum += digit15;
    }

    // Now verify checksum
    if (sum % 10 != 0)
    {
        printf("INVALID\n");
        return 0;
    }

    // Now we know checksum is verified.
    // Determine the type/company of the card
    if (length == 13)
    {
        printf("VISA\n");
        return 0;
    }

    if (length == 15)
    {
        printf("AMEX\n");
        return 0;
    }

    if (length == 16)
    {
        first_digit = cc_number / 1000000000000000;
        if (first_digit == 4)
        {
            printf("VISA\n");
            return 0;
        }
        else
        {
            printf("MASTERCARD\n");
            return 0;
        }
    }
}