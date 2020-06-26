# Questions

## What is pneumonoultramicroscopicsilicovolcanoconiosis?

Longest word in dictionary/large, 45 letters.

## According to its man page, what does `getrusage` do?

Returns resource usage statistics for the calling process, for all children
of the calling process that have been terminated, and for the calling thread.

## Per that same man page, how many members are in a variable of type `struct rusage`?

16

## Why do you think we pass `before` and `after` by reference (instead of by value) to `calculate`, even though we're not changing their contents?

Passing them by value might change performance.

## Explain as precisely as possible, in a paragraph or more, how `main` goes about reading words from a file. In other words, convince us that you indeed understand how that function's `for` loop works.

The for loop goes through file (to be spellchecked) character by character.
It only allows alphabetical characters and apostrophes.
If character is alphabetical or an apostrophe, it is appended to a word.
Also it ignores strings that are too long, and it ignores numbers.
Once a non-alphabetical, non-numerical, non-apostrophe character is reached,
it must have reached a space or a newline; so finally a whole word is built.
The word is then null terminated.
The word's spelling is checked, recording usage statistics before and after the check.
If the word is misspelled, it is printed.

## Why do you think we used `fgetc` to read each word's characters one at a time rather than use `fscanf` with a format string like `"%s"` to read whole words at a time? Put another way, what problems might arise by relying on `fscanf` alone?

There might be characters not allowed in the dictionary, such as numbers etc.

## Why do you think we declared the parameters for `check` and `load` as `const` (which means "constant")?

This prevents those parameters from being assigned to during the runs of those functions.
These functions may take a long time to run, and they both go through the
dictionary, so if those parameters are allowed to be assigned to during the run,
it might break the program.
