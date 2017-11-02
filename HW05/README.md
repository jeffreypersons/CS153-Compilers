Running and Building with antlr4 with the Hello example
First set up antlr4 following the directions in the following link:
https://github.com/antlr/antlr4/blob/master/doc/getting

Creating a working C++ example:
If you don't want to mess with classpaths, I have set up several bat files. The only thing needed is the antlr4 jar, rename to antlr.jar, in the HelloExample folder. then run the following commands
- antlr.bat -Dlanguage=Cpp Hello.g4
- mkdir build
- cd build
- cmake ..
	cmake will require the paths to the include directory of antlr4 and the C++ libraries for antlr4 as well
simple run the resulting file

NOTES: for the C++ build the parse tree results in crashes so it is disabled in the Hello.cpp file. We cannot use the GUI with the CPP version - antlr isn't set up for it.