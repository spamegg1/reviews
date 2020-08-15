# Generates Jack code for creating a list of 997 words
# spamegg 1/20/2019

infile = open('500CAPITALIZED.txt', 'r')
outfile = open('500lists.txt', 'w')

outfile.write('var Array wordlist;\n')

for i in range(26):
    outfile.write('var Array wordlist' + str(i) + ';\n')

outfile.write('\n')

j = 0
for line in infile:
    i = 0
    outfile.write('let wordlist' + str(j) +
                  ' = Array.new(' + str(len(line.split())) + ');\n')
    for word in line.split():
        outfile.write('let wordlist' +
                      str(j) + '[' + str(i) + '] = "' + word + '";\n')
        i += 1
    j += 1
    outfile.write('\n')

outfile.write('let wordlist = Array.new(25);\n')

for i in range(26):
    outfile.write('let wordlist[' + str(i) + '] = wordlist' + str(i) + ';\n')

outfile.write('\nreturn wordlist;')
