disp(sprintf('2 decimals: %0.2f', pi));  % 3.14
disp(sprintf('6 decimals: %0.6f', pi));  % 3.141593

a = pi;
format long;
a  % 3.14159265358979

format short;
a  % 3.1416

% matrices
A = [1 2; 3 4; 5 6];
A

% vectors
v = [1; 2; 3];
v

% generate vectors and matrices
v = 1: 0.1: 2;
v

v = 1:6;
v

m = ones(2, 3);
m

c = 2*ones(2, 3);
c

w = zeros(1, 3);
w

w = rand(3, 3);
w

w = randn(1, 3);
w

e = eye(4);
e

% histograms
w = -6 + sqrt(10)*(randn(1, 10000));
hist(w);
hist(w, 50);

% other matrix commands
A = [1 2; 3 4; 5 6];
A(3, 2);
A(:,2);
A(3, :);
A([1 3], :);
A(:);

A(:,2) = [10; 11; 12];
A = [A, [100; 101; 102]];

B = [11 12; 13 14; 15 16];
C = [A, B];
C = [A; B];

size(A)
size(A, 1) % rows
size(A, 2) % columns

sz = size(A);
size(sz)

v = [1 2 3 4];
length(v) % 4 = longest dim

% loading data/files
pwd;
ls;
load featuresX.dat;
load('featuresX.dat');
load priceY.dat;

who;
whos;
featuresX;
size(featuresX);
clear featuresX;

v = priceY(1:10);
save hello.mat v;
save hello.txt v -ascii;
clear;
load hello.mat;

% matrix multiplication
A = [1 2; 3 4; 5 6];
B = [11 12; 13 14; 15 16];
C = [1 1; 2 2];
A*C % matrix multiplication
A.*B % element-wise multiplication
A.^2 % element-wise square

v = [1; 2; 3];
1 ./ v % element-wise reciprocal
1 ./ A % element-wise reciprocal
log(v) % element-wise logarithm
exp(v)
abs(v)

% increment all entries by 1
v + ones(length(v), 1);
v + 1

% transpose
A'
(A')' = A;

% element wise comparison
A < 3 % 1 true, 0 false

% column-wise max/min
max(A)

% find indices satisfying comparison
find(v < 3) % 1 2

sum(v)
floor(v)
ceil(v)
rand(3)
max(rand(3), rand(3))

sum(A, 1)
sum(A, 2)

% diagonal sum
A = magic(9);
sum(sum(A .* eye(9)))

% pseudo inverse
pinv(A)











