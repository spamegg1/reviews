# Questions

## What's `stdint.h`?

A header file in C standard library that specifies exact-width integer types.

## What's the point of using `uint8_t`, `uint32_t`, `int32_t`, and `uint16_t` in a program?

In order to use BYTE, DWORD, LONG and WORD, which are BMP-related data types of Microsoft.

## How many bytes is a `BYTE`, a `DWORD`, a `LONG`, and a `WORD`, respectively?

1, 4, 4, 2.

## What (in ASCII, decimal, or hexadecimal) must the first two bytes of any BMP file be? Leading bytes used to identify file formats (with high probability) are generally called "magic numbers."

424d in hexadecimal.

## What's the difference between `bfSize` and `biSize`?

`bfSize` is the size, in bytes, of the bitmap file.
`biSize` is the number of bytes required by the structure BITMAPINFOHEADER.

## What does it mean if `biHeight` is negative?

It means the image is upside down.

## What field in `BITMAPINFOHEADER` specifies the BMP's color depth (i.e., bits per pixel)?

biBitCount.

## Why might `fopen` return `NULL` in lines 24 and 32 of `copy.c`?

Because the input or the output files might not be opened,
for reading and writing, respectively.

## Why is the third argument to `fread` always `1` in our code?

Because the files we are reading in the first argument
always have the same size as the second argument.

## What value does line 63 of `copy.c` assign to `padding` if `bi.biWidth` is `3`?

3.

## What does `fseek` do?

Move position forward or backward in a file.

## What is `SEEK_CUR`?

Current seeking position.
