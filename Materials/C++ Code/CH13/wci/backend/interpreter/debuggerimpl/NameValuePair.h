/**
 * <h1>NameValuePair</h1>
 *
 * <p>Variable name and its value string pair used by the debugger.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef NAMEVALUEPAIR_H_
#define NAMEVALUEPAIR_H_

#include "../Cell.h"
#include "../MemoryMap.h"
#include "../memoryimpl/MemoryMapImpl.h"
#include "../../../DataValue.h"

namespace wci { namespace backend { namespace interpreter { namespace debuggerimpl {

using namespace std;
using namespace wci::backend::interpreter;
using namespace wci::backend::interpreter::memoryimpl;

class NameValuePair
{
public:
    /**
     * Constructor.
     * @param variable_name the variable's name
     * @param cell_value the variable's current value
     */
    NameValuePair(string variable_name, CellValue *cell_value);

    /**
     * Getter.
     * @return the variable's name.
     */
    string get_variable_name() const;

    /**
     * Getter.
     * @return return the variable's value string.
     */
    string get_value_string() const;

    /**
     * Convert a value into a value string.
     * @param value the value.
     * @return the value string.
     */
    static string value_to_string(CellValue *cell_value);

private:
    string variable_name;  // variable's name
    string value_string;   // variable's value string

    static int MAX_DISPLAYED_ELEMENTS;

    /**
     * Convert an array value into a value string.
     * @param cell_value the cell's value.
     * @param buffer the string to use.
     */
    static void array_value_string(CellValue *cell_value, string buffer);

    /**
     * Convert a record value into a value string.
     * @param cell_value the cell's value.
     * @param buffer the string to use.
     */
    static void record_value_string(CellValue *cell_value, string buffer);

};

}}}}  // namespace wci::backend::interpreter::debuggerimpl

#endif /* NAMEVALUEPAIR_H_ */
