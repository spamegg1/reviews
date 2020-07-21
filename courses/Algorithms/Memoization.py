from functools import update_wrapper


def decorator(d):
    """Make function d a decorator: d wraps a function fn."""
    def _d(fn):
        return update_wrapper(d(fn), fn)
    update_wrapper(_d, d)
    return _d


@decorator
def memo(f):
    """Decorator that catches the return value for each call to f(args).
    Then when called again with same args, we can just look it up."""
    cache = {}

    def _f(*args):
        try:
            return cache[args]
        except KeyError:
            cache[args] = result = f(*args)
            return result
        except TypeError:
            # some element of args cannot be a dictionary key
            return f(args)
    return _f


@decorator
def countcalls(f):
    """Decorator that makes the function f count calls to it, in callcounts[f]."""
    def _f(*args):
        callcounts[_f] += 1
        return f(*args)
    callcounts[_f] = 0
    return _f


callcounts = {}


@decorator
def trace(f):
    indent = '   '

    def _f(*args):
        signature = '%s(%s)' % (f.__name__, ', '.join(map(repr, args)))
        print('%s--> %s' % (trace.level*indent, signature))
        trace.level += 1
        try:
            result = f(*args)
            print('%s<-- %s === %s' % ((trace.level-1)*indent, signature, result))
        finally:
            trace.level -= 1
        return result
    trace.level = 0
    return _f


def disabled(f):
    return f
# Now we can write e.g. trace = disabled or countcalls = disabled
# to disable debug tools everywhere in the code.


# trace = disabled
# countcalls = disabled
@countcalls
@memo
@trace
def fib(n):
    return 1 if n <= 1 else fib(n-1) + fib(n-2)


print(fib(6))
