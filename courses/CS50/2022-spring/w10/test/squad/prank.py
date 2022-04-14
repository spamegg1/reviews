import sys

def prank():
    filenames = sys.argv[1:]
    strings = []  # length of this is the same as the number of files

    for filename in filenames:
        with open(filename) as file:
            strings.append(file.read().split('\n'))

    # assume all files have same number of lines
    linecount = len(strings[0])
    filecount = len(filenames)
    for i in range(linecount):
        print(" ".join(strings[j][i] for j in range(filecount)))

if __name__ == "__main__":
    prank()
