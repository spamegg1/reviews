% for loop
v = zeros(10, 1);

for i = 1:10,
  v(i) = 2^i;
end;

v

indices = 1:10;
for i=indices,
  disp(i);
end;


% while loop
i = 1;
while i <= 5,
  v(i) = 100;
  i = i + 1;
end;
v

i = 1; 
while true,
  v(i) = 999;
  i = i + 1;
  if i == 6,
    break;
  end;
end;
v

% more general if-else
v(1) = 2;
if v(1) == 1,
  disp('the value is one');
elseif v(1) == 2,
  disp('the value is two');
else
  disp('the value is not one or two.');
end;

% other stuff
x = [1 1; 1 2; 1 3];
y = [1; 2; 3];

theta = [0; 1];
j = costFunctionJ(x, y, theta)

theta = [0; 0];
j = costFunctionJ(x, y, theta)




















