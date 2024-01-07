# TODO
from cs50 import get_int

VALID_LENGTHS = [13, 15, 16]
AMEX = ['34', '37']
MASTERCARD = ['51', '52', '53', '54', '55']
VISA = '4'

def main():
    # Get input from user
    number = get_int("Number: ")
    while number <= 0:
        number = get_int("Number: ")

    number = str(number)
    length = len(number)

    # Calculate length of number to decide if it is valid
    if length not in VALID_LENGTHS:
        print("INVALID")
        return

    # Print INVALID if the length is OK; but the number is not
    # This is for AMEX (length 15)
    if length == 15 and number[:2] not in AMEX:
        print("INVALID")
        return

    # This is for MASTERCARD or VISA (length 16)
    if length == 16:
        if number[:2] not in MASTERCARD and number[0] != VISA:
            print("INVALID")
            return

    # This is for VISA (length 13)
    if length == 13 and number[0] != VISA:
        print("INVALID")
        return

    # Now we know we have a number that fits one of
    # company definitions and has 13, 15 or 16 digits.
    # Now we can verify the checksum.
    checksum = 0

    # Multiply every other digit by 2, starting with second-to-last
    # digit, then add the digits of these doubles (which are 0-18)
    for i in range(2, 13, 2):
        checksum += (int(number[-i]) * 2) % 10
        checksum += (int(number[-i]) * 2) // 10

    if length == 15 or length == 16:
        checksum += (int(number[-14]) * 2) % 10
        checksum += (int(number[-14]) * 2) // 10

    if length == 16:
        checksum += (int(number[-16]) * 2) % 10
        checksum += (int(number[-16]) * 2) // 10


    # Now add the remaining (odd) digits
    for i in range(1, 14, 2):
        checksum += int(number[-i])

    if length == 15 or length == 16:
        checksum += int(number[-15])

    # Now verify checksum
    if checksum % 10 != 0:
        print("INVALID")
        return

    # Now we know checksum is verified. Determine the type/company of the card
    if length == 13:
        print("VISA")
        return

    if length == 15:
        print("AMEX")
        return

    if length == 16:
        if number[0] == VISA:
            print("VISA")
            return
        else:
            print("MASTERCARD")
            return


if __name__ == "__main__":
    main()
