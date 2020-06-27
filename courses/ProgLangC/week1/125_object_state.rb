# Programming Languages, Dan Grossman
# Section 7: Object State

class A
  def m1
    @foo = 0
  end

  def m2 x
    @foo += x
  end

  def foo
    @foo
  end

end

class B 
  # uses initialize method, which is better than m1
  # initialize can take arguments too (here providing defaults)
  def initialize(f=0)
    @foo = f
  end

  def m2 x
    @foo += x
  end

  def foo
    @foo
  end

end

class C
  # we now add in a class-variable, class-constant, and class-method

  Dans_Age = 38

  def self.reset_bar
    @@bar = 0
  end

  def initialize(f=0)
    @foo = f
  end

  def m2 x
    @foo += x
    @@bar += 1
  end

  def foo
    @foo
  end
  
  def bar
    @@bar
  end
end

# example uses 
=begin
x = A.new
y = A.new # different object than x
z = x # alias to x
x.foo # get back nil because instance variable not initialized
x.m2 3 # error because try to add with nil object
x.m1 # creates @foo in object x refers to
z.foo # remember, x and z are aliases
z.m2 3
x.foo
y.m1
y.m2 4
y.foo
x.foo

w = B.new 3
v = B.new
w.foo + v.foo

d = C.new 17
d.m2 5
e = C.new
e.m2 6
d.bar
forty = C::Dans_Age + d.bar

=end
