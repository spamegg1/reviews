import sys

def red():
    if len(sys.argv) != 2:
        sys.exit(1)

    filename = sys.argv[1]

    count = 0
    with open(filename) as file:
        for line in file:
            count += line.count('#')

    print(count)

if __name__ == "__main__":
    red()
