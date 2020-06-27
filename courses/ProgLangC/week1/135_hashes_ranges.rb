# Programming Languages, Dan Grossman
# Section 7: Hashes and Ranges


h1 = {}
h1["a"] = "Found A"
h1[false] = "Found false"
h1["a"]
h1[false]
h1[42]
h1.keys
h1.values
h1.delete("a")

h2 = {"SML"=>1, "Racket"=>2, "Ruby"=>3}
h2["SML"]

# Symbols are like strings, but cheaper.  Often used with hashes.
h3 = {:sml => 1, :racket => 2, :ruby => 3}

# each for hashes best with 2-argument block

h2.each {|k,v| print k; print ": "; puts v}

# ranges
(1..100).inject {|acc,elt| acc + elt}

def foo a
  a.count {|x| x*x < 50}
end

# duck typing in foo
foo [3,5,7,9]
foo (3..9)
