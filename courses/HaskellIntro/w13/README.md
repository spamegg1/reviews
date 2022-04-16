[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Packages    

So far in this class, we have focused “small programming”:  Functions, algorithms, data structures etc. We did not talk about  structuring code itself. We will do that today, by having a closer look  at modules and show how to create your own packages.

This is relevant for your project, as you should package your project nicely up this way, so that its dependencies are well specified

# Modules

We start with the code from last class, where we ran some statistics  over some data. The module contains code that does various things:

- Code to parses the data
- Code to calculates statistics
- Code to format these statistics nicely as HTML
- The `main` function that combines all these.

Obviously, this is not a good style in the long run, as the file will just grow too big. Also, it does not encourage reuse: In another  project, it is likely that one wants to use the same parsing code, but  then do different things with the data.

## Name and path

Therefore, we will put the parsing code into a module of its own. For that, we need to come up with a module name. You have already seen the  pattern: Capitalized words separated by dots. A good name might be `Data.Lahmann.Parser`.

The module name dictactes the filename, with dots replaced by  directory separators. We therefore take parsing code (some of which we  have to rip out of the `main` function) into a file named `Parser.hs` in a directory `Data/Lahmann`, which has to start with

```haskell
module Data.Lahmann.Parse where
…
```

In our main program, we can now `import Data.Lahmann.Parse` and everything works as before. A few imports can be removed, which is a good sign: The *user* of `Data.Lahmann.Parse` should not have to worry about whether the data is stored in CSV files or any other format. Using the flag `-fwarn-unused-imports` assists in finding useless imports.

It make make sense to not hard-code the directory name of the files  into the parsing code, but rather pass that from the main function.

## Exporting only what is required

By saying `import Data.Lahmann.Parse` in the main file, we are importing everything that that module exports. The module currently exports everything that is defined in that module, including internal  helpers such as `addBattingSums`. We can see that by firing up GHCi and looking at the output of `:browse Data.Lahmann.Parse`.

This is bad style: The user of the `Parse` module should be oblivious about the internal workings of the module, and the programmer responsible for the `Parse` module should have the freedom of adding, removing or changing any internal functions without breaking other code.

Therefore, a module should always declare its *public interface* by stating which functions, types and constructors are exported. This  is done via an export list that names all exported entities after the  module name:

```haskell
module Data.Lahmann.Parse
  ( PlayerId         -- exporting a type synonym
  , Batting(Batting) -- exporting a type with a constructor
  , Player(..)       -- exporting a type with all its constructors
  , …
  ) where
…
```

## Where to put the types?

Let us put the statistics code into a module `Data.Lahmann.Stats`. We now run into a problem: Where do we put the type definitions (`data Batting`, `data Player`)? They currently reside in `Data.Lahmann.Parse`, but having the statistics module import that feels very wrong. AS it  would if we moved the type definitions to the statistics module and  import that.

The only way out is to create yet another module, say, `Data.Lahmann.Types`, that defines all the common types of our project.

And finally, the module `Data.Lahmann.Stats.HTML` contains code to take the data, runs the stats, and writes it to a given filename.

# Packages

We now have a bunch of modules, and a main program. But these are not completely independent artifacts, but are rather inherently connected,  and we want to express this connection. A bunch of related modules is  called a *package*.

We have worked with packages before: We installed existing packages from Hackage using the `cabal` tool. Now we will create our own package.

## A cabal file

To do so, we simply run `cabal init` in the current directory and answer all the questions. We start with a library package. Afterwards, we open the `lahmann-stats.cabal` file and clean it up a bit, so that it might look like this:

```yaml
name:                lahmann-stats
version:             0.1.0.0
synopsis:            Baseball stats
license:             BSD3
license-file:        LICENSE
author:              Joachim Breitner
maintainer:          mail@joachim-breitner.de
category:            Data
build-type:          Simple
extra-source-files:  ChangeLog.md
cabal-version:       >=1.10


library
  exposed-modules:
    Data.Lahmann.Types,
    Data.Lahmann.Parser,
    Data.Lahmann.Stats,
    Data.Lahmann.Stats.Html

  build-depends:
     base >=4.8 && <4.9,
     bytestring >=0.10 && <0.11,
     blaze-html >=0.8 && <0.9,
     cassava >=0.4 && <0.5,
     vector >=0.11 && <0.12,
     containers >=0.5 && <0.6,
     filepath >=1.4 && <1.5,
     statistics >=0.13 && <0.14
  default-language:    Haskell2010
```

I removed the `Main` module from `exposed-modules`, because this is not part of the library, but rather of the program.

## Building and installing

Running `cabal install` in this directory will now build  this library and install it in your local Haskell package. You can now  use it from other projects. Neat.

## Adding an executable

Our project is not only a library, but also an executable program. We can tell this to cabal as well, by adding a new *stanza* for our program:

```yaml
executable lahmann-stats
  main-is: Main.hs
  build-depends:
     base >=4.8 && <4.9,
     bytestring >=0.10 && <0.11,
     blaze-html >=0.8 && <0.9,
     cassava >=0.4 && <0.5,
     vector >=0.11 && <0.12,
     containers >=0.5 && <0.6,
     filepath >=1.4 && <1.5,
     statistics >=0.13 && <0.14
  default-language:    Haskell2010
```

For now I was lazy and simply copied the `build-depends` from above. It would also have been possible to depend on the `lahmann-data` *library* here, but then `Main.hs` would have to live in a different directory, for technical reasons (namely that `cabal` cannot tell GHC not to try to use the other modules if they are in the same directory).

When we run `cabal install` now, we find the compiled program in `~/.cabal/bin/`.

# Creating documentation

Another benefit of having a package is that it is easy to create API documentation for the library. Simply run `cabal haddock` and it will create documentation that looks much like the documentation that we have seen so far.

We notice that only exported functions appear in the documentation. That is good and makes sense.

But we also notice that there is no text. Unfortunately, documentation does not write itself.

To add documentation to a function or type, we add a comment before it which starts with a `|` (or alternatively a comment afterwards that start with a `^`):

```haskell
-- | Reads the player data from a downloaded copy of Lahmann's baseball
-- statistics <http://seanlahman.com/baseball-archive/statistics/>.
--
-- The first parameter indicates the directory name where the files can
-- be found.
readPlayerStats :: FilePath -> IO (M.Map PlayerID Player)
```

The tool that creates these documentation is called haddock, and you should consult the [haddock manual](https://www.haskell.org/haddock/doc/html/ch03s08.html) for more information on the markup language you can use here, and other features of haddock.

# Creating a tarball

If you want to distribute your finished package, you can use the command `cabal sdist` to bundle everything require up into a `.tar.gz` file [such as this one](https://www.seas.upenn.edu/~cis194/fall16/extras/13-packages.tar.gz).

Annoyingly, `cabal` does not check if you have included all necessary modules in the `exposed-modules` section, so make sure to unpack the resulting file somewhere else and try to `cabal build` it!

# Further reading

Cabal files can also specify test suites and benchmark suites, and  have a few more features that you might want to know about. See the [cabal manual](https://www.haskell.org/cabal/users-guide/) and also [the wiki page on the topic](https://wiki.haskell.org/How_to_write_a_Haskell_program).

​      Powered      by [shake](http://community.haskell.org/~ndm/shake/),      [hakyll](http://jaspervdj.be/hakyll/index.html),      [pandoc](http://johnmacfarlane.net/pandoc/),      [diagrams](http://projects.haskell.org/diagrams),      and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).          

  