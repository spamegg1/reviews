# Programming Languages, Dan Grossman
# Section 7: Arrays Longer Example

a = [3,2,7,9]
a[2]
a[0]
a[4]
a.size
a[-1]
a[-2]
a[1] = 6
a
a[6] = 14
a
a[5]
a.size

a[3] = "hi"

b = a + [true,false]
c = [3,2,3] | [1,2,3]

# array make fine tuples

triple = [false, "hi", a[0] + 4]
triple[2]

# arrays can also have initial size chosen at run-time
# (and as we saw can grow later -- and shrink)
x = if a[1] < a[0] then 10 else 20 end
y = Array.new(x)

# better: initialized with a block (coming soon)
z = Array.new(x) { 0 }
w = Array.new(x) {|i| -i }

# stacks
a
a.push 5
a.push 7
a.pop
a.pop
a.pop

# queues (from either end)

a.push 11
a.shift
a.shift
a.unshift 14

# aliasing

d = a
e = a + []
d[0]
a[0] = 6
d[0]
e[0]

# slices 

f = [2,4,6,8,10,12,14]
f[2,4]
f.slice(2,2)
f.slice(-2,2)
f[2,4] = [1,1]

# iterating: next segment, teaser here:

[1,3,4,12].each {|i| puts (i * i)}
