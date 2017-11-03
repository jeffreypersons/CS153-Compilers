PROGRAM sample;

VAR
    i, j : integer;
    alpha, beta5x : real;

BEGIN
    REPEAT
        j := 3;
        i := 2 + 3*j
    UNTIL i >= j + 2;

    IF i <= j THEN i := j;

    IF j > i THEN i := 3*j
    ELSE BEGIN
        alpha := 9;
        beta5x := alpha/3 - alpha*2;
    END
END.