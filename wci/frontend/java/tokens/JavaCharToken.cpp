/**
 * <h1>JavaStringToken</h1>
 *
 * <p> Java string tokens.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include "JavaCharToken.h"
#include "../JavaError.h"

namespace wci { namespace frontend { namespace java { namespace tokens {

using namespace std;
using namespace wci::frontend;
using namespace wci::frontend::java;

JavaCharToken::JavaCharToken(Source *source) throw (string)
    : JavaToken(source)
{
    extract();
}

void JavaCharToken::extract() throw (string)
{
    string value_str = "";

    char current_ch = next_char();  // consume initial quote
    text += '\'';
    // Get string characters.
    if (isspace(current_ch)) current_ch = ' ';
    else if (current_ch == '\\'){
        text += current_ch;
        //value_str += current_ch;
        current_ch = next_char();
        switch(current_ch){
            case '\\': case '\'': case '"': text += current_ch;
                                             value_str += current_ch;
                                             break;    
            case 'n':                        text += current_ch;
                                             value_str += '\n';
                                             break;        
            case 't':                        text += current_ch;
                                             value_str += "\t";
                                             break;              
            default: type = (TokenType) PT_ERROR;
                     value = new DataValue((int) UNEXPECTED_TOKEN);
        }
    }
    else{
        text += current_ch;
        value_str += current_ch;
    }
    
    current_ch = next_char();

    if (current_ch == '\'')
    {
        text += current_ch;
        next_char();  // consume final quote
        type = (TokenType) PT_CHAR;
        value = new DataValue(value_str);
    }
    else
    {
        type = (TokenType) PT_ERROR;
        value = new DataValue((int) UNEXPECTED_TOKEN);
    }
}

}}}}  // namespace wci::frontend::Java::tokens
