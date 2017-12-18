#include "SimpleDFAScanner.h"

using namespace std;

const int SimpleDFAScanner::matrix[13][7] = {
    /*        letter digit   +    -    .    E other */
    /*  0 */ {   1,    4,    3,   3, ERR,   1, ERR },
    /*  1 */ {   1,    1,   -2,  -2,  -2,   1,  -2 },
    /*  2 */ { ERR,  ERR,  ERR, ERR, ERR, ERR, ERR },
    /*  3 */ { ERR,    4,  ERR, ERR, ERR, ERR, ERR },
    /*  4 */ {  -5,    4,   -5,  -5,   6,   9,  -5 },
    /*  5 */ { ERR,  ERR,  ERR, ERR, ERR, ERR, ERR },
    /*  6 */ { ERR,    7,  ERR, ERR, ERR, ERR, ERR },
    /*  7 */ {  -8,    7,   -8,  -8,  -8,   9,  -8 },
    /*  8 */ { ERR,  ERR,  ERR, ERR, ERR, ERR, ERR },
    /*  9 */ { ERR,   11,   10,  10, ERR, ERR, ERR },
    /* 10 */ { ERR,   11,  ERR, ERR, ERR, ERR, ERR },
    /* 11 */ { -12,   11,  -12, -12, -12, -12, -12 },
    /* 12 */ { ERR,  ERR,  ERR, ERR, ERR, ERR, ERR },
};

SimpleDFAScanner::SimpleDFAScanner(string source_path)
    : ch(0), state(-1), line(""), line_number(0), line_pos(-1)
{
    reader.open(source_path);
    if (reader.fail())
    {
        cout << "Failed to open source file " << source_path << endl;
        exit(0);
    }
}

SimpleDFAScanner::~SimpleDFAScanner()
{
}

string SimpleDFAScanner::next_token() throw(string)
{
    // Skip blanks.
    while (isspace(ch)) next_char();

    // At EOF?
    if (reader.fail()) return "";

    state = 0;  // start state
    string buffer;

    // Loop to do state transitions.
    while (state >= 0)  // not acceptance state
    {
        state = matrix[state][type_of(ch)];  // transition

        if ((state >= 0) || (state == ERR))
        {
            buffer += ch;  // build token string
            next_char();
        }
    }

    return buffer;
}

void SimpleDFAScanner::scan() throw(string)
{
    next_char();

    while (ch != 0)  // EOF?
    {
        string token = next_token();

        if (token != "")
        {
            cout << "=====> \"" << token << "\" ";
            string token_type =
                  (state ==  -2) ? "IDENTIFIER"
                : (state ==  -5) ? "INTEGER"
                : (state ==  -8) ? "REAL (fraction only)"
                : (state == -12) ? "REAL"
                :                  "*** ERROR ***";
            cout << token_type << endl;
        }
    }
}

int SimpleDFAScanner::type_of(char ch)
{
    return   (ch == 'E')  ? E
           : isalpha(ch)  ? LETTER
           : isdigit(ch)  ? DIGIT
           : (ch == '+')  ? PLUS
           : (ch == '-')  ? MINUS
           : (ch == '.')  ? DOT
           :                OTHER;
}

void SimpleDFAScanner::next_char() throw(string)
{
    if ((line == "") || (++line_pos >= line.length()))
    {
        getline(reader, line);
        if (reader.fail())
        {
            ch = 0;
            return;
        }

        if (line != "")
        {
            cout << "[" << ++line_number << "] " << line << endl;
            line += " ";
            line_pos = 0;
            ch = line[0];
        }
        else ch = 0;
    }
    else
    {
        ch = line[line_pos];
    }
}

int main()
{
    SimpleDFAScanner scanner("SimpleDFAInput.txt");
    scanner.scan();
}
