PROGRAM BaseTest;

VAR
   x: real; 
   y: complex;
   
BEGIN {ComplexTest}
    x := 10;
    y.im := -10;
    y.re := 10;
    writeln('y.re = ', y.re);
    writeln('y.im = ', y.im);
END {ComplexTest}.
