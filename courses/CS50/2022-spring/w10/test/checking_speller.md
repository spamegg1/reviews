# [Checking Speller](https://cs50.harvard.edu/college/2022/spring/test/speller/#checking-speller)

Open-source projects (whose source code is, by definition, open to anyone) are commonly stored on sites like [GitHub](https://github.com/), where other programmers can not only view the code but also open  “issues” to report problems (or request features). Suppose, in fact,  that [@dmalan](https://github.com/dmalan) has recently pushed his implementation of Speller, in Python, to the GitHub repository at [github.com/cs50/speller](https://github.com/cs50/speller). But he’s already noticed some issues with his own code, per [github.com/cs50/speller/issues?q=is%3Aopen+is%3Aissue+author%3Admalan+sort%3Acreated-asc](https://github.com/cs50/speller/issues?q=is%3Aopen+is%3Aissue+author%3Admalan+sort%3Acreated-asc).

For each of the issues below, explain how resolve it in no more than  three sentences, citing line numbers and proposing new lines of code as  appropriate. Take care not to introduce any bugs!

1. (2 points.) [File is never closed in load function](https://github.com/cs50/speller/issues/1)

1. (2 points.) [Capitalized words are always considered misspelled](https://github.com/cs50/speller/issues/2)

1. (2 points.) [Implementation of check is longer than it needs to be](https://github.com/cs50/speller/issues/3)

1. (2 points.) [Determining size of dictionary always takes linear time](https://github.com/cs50/speller/issues/4)

1. (2 points.) [Checking words always takes linear time](https://github.com/cs50/speller/issues/5)

If you’d like to download the repository’s code into your codespace, execute, e.g.:

```bash
wget https://github.com/cs50/speller/archive/refs/heads/main.zip
unzip main.zip
```

If you’d like to run the (buggy) code, execute, e.g.:

```bash
cd speller-main
python speller.py dictionaries/large texts/lalaland.txt
```