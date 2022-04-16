[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

## Piazza

- We will be using [Piazza](http://piazza.com/upenn/fall2018/cis194) for announcements and as a place for you to ask questions.

## Installation and coding environment

- For the first few lectures, the online platform http://code.world/haskell it is sufficient.

- Eventually you will have to run Haskell locally. Visit the [Haskell Platform download page](https://www.haskell.org/platform/prior.html) for Windows, Mac, and Linux installers and instructions. We will use GHC 7.10.3 for this course.

  After you installed the platform, run

  ```
  cabal install codeworld-api
  ```

  to be able to run programs as you do in the CodeWorld online IDE.

- If you have a favorite text editor (such as emacs or vim) it will work just fine for editing Haskell programs. There is a nice [haskell-mode for emacs](http://projects.haskell.org/haskellmode-emacs/). Vim comes with syntax highlighting for Haskell out of the box; for more options [try this vim haskell mode](http://projects.haskell.org/haskellmode-vim/). Other editors commonly used with Haskell include Nodepad++, TextMate, Gedit, or Sublime.

## Help/community

- Your first stop for asking questions related to this course should be [Piazza](http://piazza.com/upenn/fall2014/cis194/home). However, if you have questions of a more general nature or are interested in exploring the larger Haskell community, read on!
- The [#haskell IRC channel](http://www.haskell.org/haskellwiki/IRC_channel) is a great place to get help. Strange as it may seem if you’ve spent  time in other IRC channels, #haskell is always full of friendly, helpful people.
- Many people from the Haskell community are active on [StackOverflow](http://stackoverflow.com/questions/tagged/haskell), which can be a good place to ask questions.
- The [Haskell-beginners mailing list](http://haskell.org/mailman/listinfo/beginners) is a good place to ask beginner-level questions.
- The [Haskell-cafe mailing list](http://haskell.org/mailman/listinfo/haskell-cafe) can also be a good place to ask questions, but is much higher-traffic.
- The Haskell wiki has a [list of frequently asked questions](http://www.haskell.org/haskellwiki/FAQ).
- [tryhaskell.org](http://tryhaskell.org/) gives you a  ghci session in your browser, and includes a very simple tutorial. It  also features an interface to the #haskell IRC channel.
- [hpaste.org](http://hpaste.org) is a good place to paste programs you’re having trouble with in order to get help from people in #haskell.
- [Haskell PasteBin](http://paste.hskll.org/) is another paste site, which visualizes a `example` top level value. If it is a function, it will create input widges for its parameters, and if it returns a [diagram](http://projects.haskell.org/diagrams/), it draws that result.

## Reading

- FP Complete’s [School of Haskell](https://www.fpcomplete.com/school), a set of online tutorials.
- [Real World Haskell](http://book.realworldhaskell.org/), by Bryan O’Sullivan, Don Stewart, and John Goerzen, published by  O’Reilly. A thorough and detailed introduction to Haskell that gets into the nitty gritty of using Haskell effectively in the “real world”. Can  be read online for free, or in dead tree form.
- [Learn You a Haskell for Great Good!](http://learnyouahaskell.com/) is a whimsical and easy-to-follow Haskell tutorial, with super awesome  illustrations. Also available online or in dead tree form.
- [Beginning Haskell](http://www.amazon.com/Beginning-Haskell-A-Project-Based-Approach/dp/1430262508/) is a recent addition to the Haskell canon. It can be seen as an update of Real World Haskell. This is not available for free.
- [Some](https://github.com/bitemyapp/learnhaskell) [reflections](http://dev.stephendiehl.com/hask/) on Haskell.
- The [Haskell wikibook](http://en.wikibooks.org/wiki/Haskell) actually contains a substantial amount of well-written information; a  great resource if you’re having trouble understanding a particular topic and want a different approach.
- The [Haskell wiki](http://www.haskell.org/) is a huge  grab-bag of all sorts of information, examples, explanations. The  quality varies but it’s definitely a great resource.
- The [Typeclassopedia](http://haskell.org/haskellwiki/Typeclassopedia) explains many of the type classes in the standard libraries (`Functor`, `Applicative`, `Monad`, `Monoid`, `Arrow`, `Foldable`, `Traversable`…).
- [Planet Haskell](http://planet.haskell.org/) aggregates blog posts from the Haskell community.
- There is a [Haskell subreddit](http://www.reddit.com/r/haskell/) for aggregating Haskell-related websites, blog posts, and news.

## Reference

- [Standard library documentation](http://www.haskell.org/ghc/docs/latest/html/libraries/index.html).
- A useful [Haskell cheatsheet](http://cheatsheet.codeslower.com/).
- [Hackage](http://hackage.haskell.org/) is a huge  repository of Haskell packages. If it isn’t on Hackage, it doesn’t  exist. Packages can be automatically downloaded and installed from  Hackage using the [cabal-install](http://www.haskell.org/haskellwiki/Cabal-Install) tool.
- Looking for a function but don’t know what it’s called? Want to see the documentation for a particular function? [Hoogle](http://www.haskell.org/hoogle/) searches many standard libraries and can search either by name or by type.
- [Hayoo](http://holumbus.fh-wedel.de/hayoo/hayoo.html) is another search engine for the Haskell documentation, which is much more complete (it searches all of Hackage).
- If you really want the nitty-gritty details of the Haskell language standard, see the [2010 Haskell report](http://www.haskell.org/onlinereport/haskell2010/).

## Previous installments of CIS194

- [Spring 2015](https://www.seas.upenn.edu/~cis194/spring15) by Noam Zilberstein
- [Fall 2014](https://www.seas.upenn.edu/~cis194/fall14) by Richard Eisenberg
- [Spring 2013](https://www.seas.upenn.edu/~cis194/spring13) by Brent Yorgey

​      Powered      by [shake](http://community.haskell.org/~ndm/shake/),      [hakyll](http://jaspervdj.be/hakyll/index.html),      [pandoc](http://johnmacfarlane.net/pandoc/),      [diagrams](http://projects.haskell.org/diagrams),      and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).          

  