# Programming Languages, Dan Grossman
# Section 7: Class Definitions are Dynamic

require_relative './127_example' # similar to ML's use

# above line defines the MyRational class, but now we can change it

class MyRational
  def double
     self + self 
   end
end

class Fixnum
  def double
    self + self
  end
end

def m
  42
end

class Object
  def m 
    43
  end
end

# this one will crash irb
# class Fixnum
#   def + x
#     42
#   end
# end
