# 9.4 Write a program to read through the mbox-short.txt and
# figure out who has sent the greatest number of mail messages.
# The program looks for 'From ' lines and takes the second word
# of those lines as the person who sent the mail.
# The program creates a Python dictionary that maps the sender's
# mail address to a count of the number of times they appear in the file.
# After the dictionary is produced, the program reads through the dictionary
# using a maximum loop to find the most prolific committer.
# Desired output:
# cwen@iupui.edu 5

name = input("Enter file:")
if len(name) < 1:
    name = "mbox-short.txt"
handle = open(name)
senders = {}

for line in handle:
    if not line.startswith("From "):
        continue
    sender = line.split()[1]
    senders[sender] = senders.get(sender, 0) + 1

maximum = 0
top_sender = ''
for sender in senders:
    if senders[sender] > maximum:
        maximum = senders[sender]
        top_sender = sender

print(f"{top_sender} {maximum}")
