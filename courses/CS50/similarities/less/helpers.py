from nltk.tokenize import sent_tokenize


def lines(a, b):
    """Return lines in both a and b"""
    return list(set(a.split('\n')) & set(b.split('\n')))


def sentences(a, b):
    """Return sentences in both a and b"""
    return list(set(sent_tokenize(a)) & set(sent_tokenize(b)))


def substrings(a, b, n):
    """Return substrings of length n in both a and b"""
    return list(set([a[i:i + n] for i in range(len(a) - n + 1)])
                & set([b[i:i + n] for i in range(len(b) - n + 1)]))