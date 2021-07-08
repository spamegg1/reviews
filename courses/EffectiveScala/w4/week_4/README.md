To run the Canvas demo, first compile the Scala code into
JavaScript with the following sbt invocation:

~~~ shell
sbt js/fastOptJS
~~~

Then, open the file `index.html` in a web browser.

The other demos are self-contained worksheets except
the last one (in directory
`jvm/src/main/scala/org/epfl/05_unit_testing`), which
you can run with the following command:

~~~ shell
sbt jvm/test
~~~
