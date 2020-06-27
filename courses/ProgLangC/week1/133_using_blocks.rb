# Programming Languages, Dan Grossman
# Section 7: Using Blocks

class Foo
  def initialize(max)
    @max = max
  end

  def silly
    yield(4,5) + yield(@max,@max)
  end

  def count base
    if base > @max
      raise "reached max"
    elsif yield base
      1
    else
      1 + (count(base+1) {|i| yield i})
    end
  end
end

f = Foo.new(1000)

f.silly {|a,b| 2*a - b}

f.count(10) {|i| (i * i) == (34 * i)}
