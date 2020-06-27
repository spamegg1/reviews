# Programming Languages, Dan Grossman
# Section 7: Classes and Objects

class A
  def m1
    34
  end

  def m2 (x,y)
    z = 7
    if x > y 
      false
    else
      x + y * z
    end
  end

end

class B
  def m1
    4
  end

  def m3 x
    x.abs * 2 + self.m1
  end
end

# returning self is convenient for "stringing method calls"
class C
  def m1
    print "hi "
    self
  end
  def m2
    print "bye "
    self
  end
  def m3
    print "\n"
    self
  end
end

# example uses (can type into irb)
# here in a multiline comment, which is not well-known
=begin
a = A.new
thirty_four = a.m1
b = B.new
four = b.m1
forty_seven = B.new.m3 -17
thirty_one = a.m2(3,four)

c = C.new
c.m1.m2.m3.m1.m1.m3
=end
