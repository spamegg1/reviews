[CIS 194](https://www.seas.upenn.edu/~cis194/fall16/index.html) | [Policies](https://www.seas.upenn.edu/~cis194/fall16/policies.html) | [Resources](https://www.seas.upenn.edu/~cis194/fall16/resources.html) | [Final Project](https://www.seas.upenn.edu/~cis194/fall16/final.html)

# Good Haskell Style

All your submitted programming assignments should *emerge creatively* from the following style guidelines. Programming is just as much social art form as it is engineering discipline, and as any artist knows,  constraints serve to enhance rather than quench creativity.

1. DO use `camelCase` for function and variable names.

   **Variant:** Use `ids_with_underscores` for local variables only, and use `camelCase` for global ones.

2. DO use descriptive function names, which are as long as they need to be but no longer than they have to be.
    Good: `solveRemaining`.
    Bad: `slv`.
    Ugly: `solveAllTheCasesWhichWeHaven'tYetProcessed`.

3. DON’T use tab characters. Ever.

   Haskell is layout-sensitive and tabs Mess Everything Up. I don’t care how you feel about tabs when coding in other languages. Just trust me  on this one. Note this does not mean you need to hit space a zillion  times to indent each line; your Favorite Editor ought to support  auto-indentation using spaces instead of tabs.

4. DO keep lines at a natural length, and avoid long lines when it costs readability.

   I am not a fan of hard rules when it comes to line length, so I give  you no precise number. Some lines just have to be a bit longer than  usual.

5. DO give every top-level function a type signature. Type  signatures enhance documentation, clarify thinking, and provide nesting  sites for endangered bird species. Top-level type signatures also result in better error messages. With no type signatures, type errors tend to  show up far from where the real problem is; explicit type signatures  help localize type errors.

   Locally defined functions and constants (part of a  expression or   clause) do not need type signatures, but adding them doesn’t hurt (in  particular, the argument above about localizing type errors still  applies).

6. DO precede every top-level function by a comment explaining what it does.

7. DO use `-Wall`. Either pass `-Wall` to `ghc` on the command line, or (easier) put

   ```haskell
   {-# OPTIONS_GHC -Wall #-}
   ```

   at the top of your `.hs` file. All your submitted programs should compile with no warnings.

8. DO, as much as possible, break up your programs into small  functions that do one thing, and compose them to create more complex  functions.

9. DO make all your functions *total*. That is, they should give sensible results (and not crash) for every input.

​      Powered      by [shake](http://community.haskell.org/~ndm/shake/),      [hakyll](http://jaspervdj.be/hakyll/index.html),      [pandoc](http://johnmacfarlane.net/pandoc/),      [diagrams](http://projects.haskell.org/diagrams),      and [lhs2TeX](http://www.andres-loeh.de/lhs2tex/).          

  