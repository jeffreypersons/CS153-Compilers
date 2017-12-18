#ifndef SIMPLEDFASCANNER_H_
#define SIMPLEDFASCANNER_H_

#include <iostream>
#include <fstream>
#include <string>

using namespace std;

/**
 * <h1>SimpleDFAScanner</h1>
 *
 * <p>A simple DFA scanner that recognizes Pascal identifers and numbers.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
class SimpleDFAScanner
{
public:
    SimpleDFAScanner(string source_path);
    virtual ~SimpleDFAScanner();

    /**
     * Scan the source file.
     */
    void scan() throw(string);

private:
    // Input characters.
    static const int LETTER = 0;
    static const int DIGIT  = 1;
    static const int PLUS   = 2;
    static const int MINUS  = 3;
    static const int DOT    = 4;
    static const int E      = 5;
    static const int OTHER  = 6;

    // Error state.
    static const int ERR = -99999;

    // State-transition matrix (acceptance states < 0)
    static const int matrix[13][7];

    char ch;    // current input character
    int state;  // current state
    ifstream reader;
    string line;
    int line_number;
    int line_pos;

    /**
     * Extract the next token from the source file.
     * @return name of the next token
     * @throw a string message if an error occurs.
     */
    string next_token() throw(string);

    /**
     * Return the character type.
     * @param ch the character.
     * @return the type.
     */
    int type_of(char ch);

    /**
     * Get the next character form the source file.
     * @throw a string message if an error occurs.
     */
    void next_char() throw(string);
};

#endif /* SIMPLEDFASCANNER_H_ */
