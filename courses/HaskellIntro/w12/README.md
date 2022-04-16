[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Libraries    

As you work on your final projects, you might find that lists  and functions are not sufficient, and you might need more library  functions. Today, we will look at various interesting libraries, and  also try go get a hang of how to find them and how to use their  documentation. We will also talk about performance a bit.

The lecture will consist of live coding an example program (reading statistics from [this baseball data base](http://seanlahman.com/baseball-archive/statistics/) and presenting them differently). In doing so, we learned about the following list of libraries:

- [Bytestrings](http://hackage.haskell.org/package/bytestring), in their strict and lazy variant, to represent binary or ASCII data.
- `Data.Map`, part of [containers](http://hackage.haskell.org/package/containers).
- [cassava](http://hackage.haskell.org/package/cassava) to parse CSV files.
- [vector](http://hackage.haskell.org/package/vector) to efficiently represent sequences
- [statistics](http://hackage.haskell.org/package/statistics) to calculate a correlation
- [table-layout](https://hackage.haskell.org/package/table-layout) for pretti ASCII (or unicode) tables
- [blaze-html](http://hackage.haskell.org/package/blaze-html) to generate valid HTML code

[This](https://www.seas.upenn.edu/~cis194/fall16/extras/12-libraries.hs) is (almost) the code that we came up with.

​      Powered      by [shake](http://community.haskell.org/~ndm/shake/),      [hakyll](http://jaspervdj.be/hakyll/index.html),      [pandoc](http://johnmacfarlane.net/pandoc/),      [diagrams](http://projects.haskell.org/diagrams),      and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).          