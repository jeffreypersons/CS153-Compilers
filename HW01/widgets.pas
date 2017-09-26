{ Assign1 }
{ Jeffrey Persons }
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


{Pretty print all the felds in a single employee record.}
PROCEDURE prettyPrintEmployee(VAR employee : employeeRecord);
BEGIN
    WITH employee DO BEGIN
        write('   ', stateID);
        write('    ', plantID);
        write('   ', deptID);
        write('   ', empID);
        IF count < 10 THEN BEGIN  {If single digit pad a space on the left.}
            write(' ');
        END;
        write('    ', count);
        write(' ', name);
    END;
END;


{Print the given total, on a newline with indentation.}
PROCEDURE prettyPrintWidgetTotal(VAR widgetTotal : integer);
BEGIN
    writeln();
    write('                          ');
    IF widgetTotal < 10 THEN BEGIN  {If single digit, add extra space}
        write(' ');
    END;
END;


{
 Pretty print a report listing widget totals by individual and category.

 Each line displays either an employee's fields or the total widget count per
 category if a change in state, plant, or dept occurs.
}
PROCEDURE prettyPrintEmployees;

    {-------------- inner helpers for printing ------------}
    PROCEDURE printDeptWidgetTotal(widgetTotal : integer; id : integer);
    BEGIN
        prettyPrintWidgetTotal(widgetTotal);
        write(widgetTotal,  ' TOTAL FOR DEPT  ', id,  ' *');
    END;

    PROCEDURE printPlantWidgetTotal(widgetTotal : integer; id : integer);
    BEGIN
        prettyPrintWidgetTotal(widgetTotal);
        write(widgetTotal, ' TOTAL FOR PLANT ', id, ' **');
    END;

    PROCEDURE printStateWidgetTotal(widgetTotal : integer; id : integer);
    BEGIN
        prettyPrintWidgetTotal(widgetTotal);
        write(widgetTotal, ' TOTAL FOR STATE ', id, ' ***');
    END;

    PROCEDURE printGrandWidgetTotal(widgetTotal : integer);
    BEGIN
        prettyPrintWidgetTotal(widgetTotal);
        write(widgetTotal, ' GRAND TOTAL        ***');
    END;


    VAR
        i : integer;
        prevState : integer;
        prevPlant : integer;
        prevDept  : integer;
        widgetCountState : integer;
        widgetCountPlant : integer;
        widgetCountDept  : integer;
        widgetGrandTotal : integer;
    BEGIN
        prevState := employees[1].stateID;
        prevPlant := employees[1].plantID;
        prevDept  := employees[1].deptID;
        widgetCountState := 0;
        widgetCountPlant := 0;
        widgetCountDept  := 0;
        widgetGrandTotal := 0;

        {For each employee, print its fields, and if change occurs print total.}
        writeln('STATE PLANT DEPT EMPID COUNT NAME');
        FOR i := 1 TO employeeCount + 1 DO BEGIN
            {Show info including widget total for individual employee.}

            {Determine totals and highest level change before we print anything.}
            WITH employees[i] DO BEGIN

                {-------------- display totals if change occurred -------------}
                IF stateID <> prevState THEN BEGIN
                    {State change means that plant and dept changes too.}
                    writeln();
                    printDeptWidgetTotal(widgetCountDept, prevDept);
                    printPlantWidgetTotal(widgetCountPlant, prevPlant);
                    printStateWidgetTotal(widgetCountState, prevState);
                    writeln();
                    widgetCountState := 0;
                    widgetCountPlant := 0;
                    widgetCountDept  := 0;
                END
                ELSE IF plantID <> prevPlant THEN BEGIN
                    {Plant change means that dept changes too.}
                    writeln();
                    printDeptWidgetTotal(widgetCountDept, prevDept);
                    printPlantWidgetTotal(widgetCountPlant, prevPlant);
                    writeln();
                    widgetCountPlant := 0;
                    widgetCountDept := 0;
                END
                ELSE IF deptID <> prevDept THEN BEGIN
                    {Dept changes but nothing else (lowest lvl above employee).}
                    writeln();
                    printDeptWidgetTotal(widgetCountDept, prevDept);
                    writeln();
                    widgetCountDept := 0;
                END;

                {-------------- display individual employee fields ------------}
                {Skip the last employee, as it will be blank, else print it.}
                IF i <> (employeeCount + 1) THEN
                BEGIN
                    writeln();
                    prettyPrintEmployee(employees[i]);
                END;

                {----------------- prepare for next iteration -----------------}
                widgetCountState := widgetCountState + count;
                widgetCountPlant := widgetCountPlant + count;
                widgetCountDept  := widgetCountDept  + count;
                widgetGrandTotal := widgetGrandTotal + count;
                prevState := stateID;
                prevPlant := plantID;
                prevDept  := deptID;
            END;
        END;
        printGrandWidgetTotal(widgetGrandTotal);
        writeln();
    END;


{Program main.}
BEGIN
    readEmployees();
    prettyPrintEmployees();
END.

