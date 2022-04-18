- [OpenClassroom](http://openclassroom.stanford.edu/MainFolder/HomePage.php)

​				[ 				    ![Compilers](http://openclassroom.stanford.edu/MainFolder/courses/Compilers/Compilers-logo.png)  				](http://openclassroom.stanford.edu/MainFolder/CoursePage.php?course=Compilers) 			

[Compilers](http://openclassroom.stanford.edu/MainFolder/CoursePage.php?course=Compilers)

## Alex Aiken

# Programming Assignments

There are five programming assignments and one optional assignment  for this course. To get started on the programming assignments, download and extract the starter code for your particular machine. The  individual source files are also available individually, but we **strongly** recommend that you use the tarball if one is available for your  architecture. Many of the source files you will use are actually linked  from other directories in the tree—make sure not to disturb the overall  structure of the source tree. You should extract the tarball into an  empty directory in a convenient location; when the assignment  documentation refers to `[cool root]` it is referring to this location.

​    There are several dependancies you will need to get the programming  assignments to work correctly. Specifically, you will need `g++`, `csh`, `flex`, and `bison`. If you are using Linux and have the `apt` package manager installed, you can get them all with a single command:    

```bash
sudo apt-get install g++ csh flex bison    
```

​    If you are completing the Java version of the assignments, you will also need `javac`, which you can install as follows:    

```bash
sudo apt-get install openjdk-6-jdk  
```



| Download                                                     |
| ------------------------------------------------------------ |
| [Linux (i686)](http://openclassroom.stanford.edu/MainFolder/courses/Compilers/docs/src/pa.linux.i686.tar.gz) — compiled and tested on Ubuntu 10.04 |
| Mac OS X (coming soon)                                       |
| [Download individual files](http://openclassroom.stanford.edu/MainFolder/courses/Compilers/docs/pa_src.html) (coming soon) |

Once you extract it, you will find the `[cool root]` directory is broken down into several subdirectories:    

| `assignments`                  | –    | The root working directory for each assignment can be found here. The `README` found in each of these directories will offer more information  regarding each specific assignment, as will that assignment's handout. |      |
| ------------------------------ | ---- | ------------------------------------------------------------ | ---- |
| `handouts`                     | –    | This directory contains all of the assignment handouts, as well as  documentation for the Cool programming language. These are the same  handouts that can be found in the Handouts section of the course  website. |      |
| `examples`                     | –    | This directory contains many example programs written in the Cool programming language. |      |
| `bin`                          | –    | This directory contains the `spim`, `xspim`, and `coolc` binaries, compiled for your particular architecture. To be able to run  these applications from any location. You may want to add the following  lines to your `.bashrc` file:                `                          alias spim="[cool root]/bin/spim"        alias xspim="[cool root]/bin/xspim"        alias coolc="[cool root]/bin/coolc"` |      |
| `etc`, `include`, `lib`, `src` | –    | These directories contain various source and helper files that are used by the assignment makefiles, `spim`, and elsewhere. Many of these source files may be symbolically linked  into the working directory for each programming assignment. You should  not need to access these directories directly to complete the  assignments. |      |

### Resources

- [Syllabus](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/syllabus.html) 
- [Video Slides](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/slides.html) 
- [Handouts](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/handouts.html) 
- [Programming Assignments](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/pa.html) 
- [Other Materials](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/other_materials.html) 
- [FAQ](http://openclassroom.stanford.edu/MainFolder/DocumentPage.php?course=Compilers&doc=docs/faq.html) 