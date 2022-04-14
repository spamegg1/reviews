check50:
	clang -ggdb3 -std=c11 -Wno-incompatible-library-redeclaration -c -o check50.o check50.c
	clang -ggdb3 -std=c11 -Wno-incompatible-library-redeclaration -c -o ctype.o ctype.c
	clang -ggdb3 -std=c11 -Wno-incompatible-library-redeclaration -c -o tests.o tests.c
	clang -ggdb3 -std=c11 -o check50 check50.o ctype.o tests.o
