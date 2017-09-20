{ Sample Pascal Program. }
{ R. Mak }

PROGRAM EmployeeListing(input, output);

CONST
    MAX_EMPLOYEES   = 100;
    MAX_NAME_LENGTH = 10;
    FIELD_SEPARATOR = ':';

TYPE
    nameString = PACKED ARRAY [1..MAX_NAME_LENGTH] OF char;
    codeChar = 'A'..'Z';
    codeSet = SET OF codeChar;
    professionEnum = (SCIENTIST, TEACHER, UNKNOWN);

    employeeRec = RECORD
                      id : integer;
                      jobCode : codeChar;
                      lastName : nameString;
                      firstName : nameString;
                      profession : professionEnum;
                  END;

VAR
    employeeCount : integer;
    employees : ARRAY [1..MAX_EMPLOYEES] OF employeeRec;
    scientistCodes, teacherCodes : codeSet;  {initialized in main}

{Read the employee ID and return its integer value.}
FUNCTION readId : integer;

    VAR
        id : integer;
        ch : char;

    BEGIN
        id := 0;

        {Read the ID digits until the field separator.}
        REPEAT
            read(ch);  {ID digit}

            {Compute the integer value.}
            if (ch <> FIELD_SEPARATOR) THEN BEGIN
                id := 10*id + (ord(ch) - ord('0'));
            END;
        UNTIL ch = FIELD_SEPARATOR;
        readId := id;
    END;

{Read and return the job code.}
FUNCTION readJobCode : codeChar;

    VAR
        ch : char;

    BEGIN
        read(ch);  {job code}
        readJobCode := ch;

        read(ch);  {skip over the field separator}
    END;

{Read a first or last name and return it by reference.}
PROCEDURE readName(VAR name : nameString);

    VAR
        i : integer;
        ch : char;

    BEGIN
        i := 0;
        name := ' ';  {initialize to all blanks}

        {Loop to read name characters until the field separator or
         the end of line or MAX_NAME_LENGTH characters have been read.}
        REPEAT
            IF NOT eoln THEN BEGIN
                read(ch);  {name character}

                IF  (ch <> FIELD_SEPARATOR)
                AND (i <= MAX_NAME_LENGTH) THEN BEGIN
                    i := i + 1;
                    name[i] := ch;
                END;
            END;
        UNTIL eoln OR (ch = FIELD_SEPARATOR) OR (i = MAX_NAME_LENGTH);

        {Read the rest of the name if more than MAX_NAME_LENGTH characters.}
        IF i = MAX_NAME_LENGTH THEN BEGIN
            WHILE (NOT eoln) AND (ch <> FIELD_SEPARATOR) DO BEGIN
                read(ch);
            END;
        END;
    END;

{Compute the employee's profession based on the job code.}
FUNCTION computeProfession(jobCode : codeChar) : professionEnum;

    BEGIN
        IF jobCode IN scientistCodes THEN BEGIN
            computeProfession := SCIENTIST;
        END
        ELSE IF jobCode IN teacherCodes THEN BEGIN
            computeProfession := TEACHER;
        END
        ELSE BEGIN
            computeProfession := UNKNOWN;
        END;
    END;

{Read the employee records.}
PROCEDURE readEmployees;

    VAR
        i : integer;

    BEGIN
        i := 0;

        {Read to the end of file or until MAX_EMPLOYEES have been read.}
        WHILE (NOT eof) AND (i < MAX_EMPLOYEES) DO BEGIN
            i := i + 1;

            WITH employees[i] DO BEGIN
                id := readId;
                jobCode := readJobCode;
                readName(lastName);
                readName(firstName);
                profession := computeProfession(jobCode);
                readln;
            END;
        END;

        employeeCount := i;
    END;

{Sort the employee records by employee ID.}
PROCEDURE sortEmployees;

    VAR
        i, j : integer;
        temp : employeeRec;

    BEGIN
        FOR i := 1 TO employeeCount-1 DO BEGIN
            FOR j := i+1 TO employeeCount DO BEGIN
                IF employees[i].id > employees[j].id THEN BEGIN
                    temp         := employees[i];
                    employees[i] := employees[j];
                    employees[j] := temp;
                END;
            END;
        END;
    END;

{Print the employee records.}
PROCEDURE printEmployees;

    VAR
        i : integer;

    BEGIN
        FOR i := 1 TO employeeCount DO BEGIN
            writeln;

            WITH employees[i] DO BEGIN
                writeln('        Id: ', id);
                writeln('  Job code: ', jobCode);
                writeln(' Last name: ', lastName);
                writeln('First name: ', firstName);

                write('Profession: ');
                CASE profession OF
                    SCIENTIST: writeln('scientist');
                    TEACHER:   writeln('teacher');
                    UNKNOWN:   writeln('unknown');
                END;
            END;
        END;

        writeln;
        writeln(employeeCount:1, ' employees read.');
    END;

{Check for extra records.}
PROCEDURE checkForExtras;

    VAR
        extra : PACKED ARRAY [1..80] OF char;

    BEGIN
        IF NOT eof THEN BEGIN
            writeln;
            writeln('WARNING: Unprocessed records:');
            writeln;

            WHILE NOT eof DO BEGIN
                extra := ' ';
                readln(extra);
                writeln('    ', extra);
            END;
        END;
    END;

BEGIN {EmployeeListing}
    scientistCodes := ['B', 'C', 'P'];
    teacherCodes   := ['E', 'H', 'U'];

    readEmployees;
    sortEmployees;
    printEmployees;

    checkForExtras;
END.