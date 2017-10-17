PROGRAM BaseTest;

VAR
   x: real; 
   y: complex;
   
BEGIN {ComplexTest}
    x := 10;
    y.im := -10 + x;
    y := y + x; {test addition of real and complex}
    y.re := 10;
    writeln('y.re = ', y.re); 
    writeln('y.im = ', y.im);
END {ComplexTest}.
