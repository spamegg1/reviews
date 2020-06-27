# Programming Languages, Dan Grossman
# Section 7: Passing Blocks

3.times { puts "hello" }

[4,6,8].each { puts "hi" }

i = 7
[4,6,8].each {|x| if i > x then puts (x+1) end }

a = Array.new(5) {|i| 4*(i+1)}
a.each { puts "hi" }
a.each {|x| puts (x * 2) }
a.map  {|x| x * 2 } #synonym: collect
a.any? {|x| x > 7 } 
a.all? {|x| x > 7 } 
a.all? # implicit are elements "true" (i.e., neither false nor nil)
a.inject(0) {|acc,elt| acc+elt }
a.select {|x| x > 7 } #non-synonym: filter

def t i
  (0..i).each do |j|
    print "  " * j
    (j..i).each {|k| print k; print " "}
    print "\n"
  end
end

t 9

