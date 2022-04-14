# [International Obfuscated C Code Contest](https://cs50.harvard.edu/college/2022/spring/test/obfuscated/#international-obfuscated-c-code-contest)

![IOCCC logo](https://cs50.harvard.edu/college/2022/spring/test/obfuscated/ioccc.png)

Source: [ioccc.org](https://www.ioccc.org/)

Programmers sometimes [obfuscate](https://en.wikipedia.org/wiki/Obfuscation_(software)) source code in order to make it harder for humans (but not computers)  to understand it. For instance, insofar as JavaScript code is often  executed client-side, a programmer might want to obfuscate it in order  to protect their intellectual property. And [some people](https://www.ioccc.org/) like to obfuscate code just for fun! Obfuscation itself might involve  eliminating unnecessary whitespace and shortening the names of functions and variables to be nondescript, essentially adopting (very!) bad  style.

But obfuscation really just makes it harder, not impossible, for  humans to understand code. With enough effort, code can be deobfuscated  too. (Of course, it might be less effort just to rewrite the same code  from scratch!) Let’s convince you of such.

For each of the obfuscated functions below, state what it does and,  in no more than three sentences, explain how it works. Assume that any  requisite libraries have been included (elsewhere). You’re welcome to  copy/paste the code into [VS Code](https://code.cs50.io/) in order to style and experiment with it.

1. (2 points.)

   ```c
   int f(float x){return(int)(x+0.5);}
   ```

**ANSWER** Let’s style it better:

```c
int f(float x)
{
    return (int)(x + 0.5);
}
```

It takes a float `x`, adds `0.5` to it, and returns the integer version of the result (rounding it down).

1. (3 points.)

   ```c
   int f(char *s){char*t=s;while(*t!='\0'){t++;}return t-s;}
   ```

**ANSWER** Let’s clean it up, and maybe change variable names:

```c
int f(char *s)
{
    char *t = s;
    while (*t != '\0')
    {
        t++;
    }
    return t - s;
}
```

It takes a string and returns the length of the string.

1. (3 points.)

   ```c
   long f(int x,int y){long n=1;for(int i=0;i<y;i++){n*=x;}return n;}
   ```

**ANSWER** Clean it up:

```c
long f(int x, int y)
{
    long n = 1;
    for (int i = 0; i < y; i++)
    {    
        n *= x;
    }
    return n;
}
```

This takes two integers `x` and `y` as input, and returns `x` raised to the power `y`.

1. (4 points.)

   ```c
   int f(char*s){int r=0;for(int i=0,n=strlen(s);i<n;i++){r+=(s[n-i-1]-'0')*pow(10,i);}return r;}
   ```

**ANSWER** Clean it:

```c
int f(char *s)
{
    int r = 0;
    for (int i = 0, n = strlen(s); i < n; i++)
    {
        r += (s[n - i - 1] - '0') * pow(10, i);
    }
    return r;
}
```

It  takes a string, then goes through the string in reverse order, does some computation on it, multiplying it by powers of 10, to convert it to decimal, in some sense…

`‘a’` which is normally 97 in ASCII, becomes 49.

`‘b’` which is normally 98 in ASCII, becomes 50. 

`‘c’` which is normally 98 in ASCII, becomes 51. And so on.

`‘ab’` becomes 540.

`‘abc’` becomes 5451. So 54 is `ab` (or is it 5400?) and 51 is `c` so they get concatenated.