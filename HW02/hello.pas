PROGRAM assign1(input, output);

CONST
    FIELD_SEPARATOR    = ',';
    MAX_NAME_LENGTH    = 25;
    MAX_EMPLOYEE_COUNT = 100;
TYPE
    nameString = PACKED ARRAY [1..MAX_NAME_LENGTH] OF char;
    employeeRecord = RECORD
        stateID : integer;
        plantID : integer;
        deptID  : integer;
        empID : integer;
        name  : nameString;
        count : integer;
    END;

VAR
    employeeCount : integer;
    employees     : PACKED ARRAY [1..MAX_EMPLOYEE_COUNT] of employeeRecord;


{
 Read integer from standard input and return it.

 Assumes digits from current position terminated by field separator
 or end of line.
}
FUNCTION readInteger : integer;
    VAR
        num : integer;
        ch  : char;
    BEGIN
        {For each char, extract the digit and add to the inferred integer.}
        num := 0;
        REPEAT
            read(ch);
            if (ch <> FIELD_SEPARATOR) THEN BEGIN
                num := 10*num + (ord(ch) - ord('0'));
            END;
        UNTIL eoln OR (ch = FIELD_SEPARATOR);
        readInteger := num;
    END;


{
 Read a name from standard input (as an array of chars) into given reference.

 Assumes letters from current position are terminated by a field separator
 or end of line.
}
PROCEDURE readName(VAR name : nameString);
    VAR
        i : integer;
        ch : char;
    BEGIN
        {For each char until terminal or max length, read character into name.}
        i := 0;
        name := ' ';
        REPEAT
            IF NOT eoln THEN BEGIN
                read(ch);
                IF  (ch <> FIELD_SEPARATOR) AND (i <= MAX_NAME_LENGTH) THEN BEGIN
                    i := i + 1;
                    name[i] := ch;
                END;
            END;
        UNTIL eoln OR (ch = FIELD_SEPARATOR) OR (i = MAX_NAME_LENGTH);

        {Read the rest of the name if more than MAX_STRING_LENGTH characters.}
        IF i = MAX_NAME_LENGTH THEN BEGIN
            WHILE (NOT eoln) AND (ch <> FIELD_SEPARATOR) DO BEGIN
                read(ch);
            END;
        END;
    END;


{
 Read employee CSV data from standard input into a sequence of employee records.

 Assumes input is hierarchical sorted in increasing order, with each line after
 the header listing employee fields delimited by FIELD_SEPARATOR.
}
PROCEDURE readEmployees;
    VAR
        i : integer;
    BEGIN
        {For each input line after the header, initialize an employeeRecord.}
        i := 0;
        readln();
        WHILE (NOT eof) AND (i < MAX_EMPLOYEE_COUNT) DO BEGIN
            i := i + 1;
            WITH employees[i] DO BEGIN
                stateID := readInteger;
                plantID := readInteger;
                deptID  := readInteger;
                empID := readInteger;
                readName(name);
                count := readInteger;
                readln();
            END;
        END;
        employeeCount := i;
    END;
