infile = open('500.txt', 'r')
outfile = open('500CAPITALIZED.txt', 'w')

for line in infile:
    for word in line.split():
        outfile.write(word.upper())
        outfile.write(' ')
    outfile.write('\n')

infile.close()
outfile.close()
