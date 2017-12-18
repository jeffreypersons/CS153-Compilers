/**
 * <h1>NameValuePair</h1>
 *
 * <p>Variable name and its value string pair used by the debugger.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <set>
#include <map>
#include "NameValuePair.h"
#include "../Debugger.h"
#include "../Cell.h"
#include "../MemoryMap.h"
#include "../memoryimpl/MemoryMapImpl.h"
#include "../../../DataValue.h"

namespace wci { namespace backend { namespace interpreter { namespace debuggerimpl {

using namespace std;
using namespace wci::backend::interpreter;
using namespace wci::backend::interpreter::memoryimpl;

int NameValuePair::MAX_DISPLAYED_ELEMENTS = 10;

NameValuePair::NameValuePair(string variable_name, CellValue *cell_value)
    : variable_name(variable_name), value_string(value_to_string(cell_value))
{
}

string NameValuePair::get_variable_name() const { return variable_name; }

string NameValuePair::get_value_string() const { return value_string; }

/**
 * Convert a value into a value string.
 * @param value the value.
 * @return the value string.
 */
string NameValuePair::value_to_string(CellValue *cell_value)
{
    string buffer = "";

    // Undefined value.
    if (cell_value == nullptr) buffer += "?";

    // Dereference a VAR parameter.
    else if (cell_value->cell != nullptr)
    {
        buffer += value_to_string(cell_value->cell->get_value());
    }

    // Array value.
    else if (cell_value->cell_array != nullptr)
    {
        array_value_string(cell_value, buffer);
    }

    // Record value.
    else if (cell_value->memory_map != nullptr)
    {
        record_value_string(cell_value, buffer);
    }

    // Character value.
    else if (cell_value->value->type == CHAR)
    {
        buffer += "'" + string(1, cell_value->value->c) + "'";
    }

    // Numeric or bool value.
    else
    {
        buffer += cell_value->value->display();
    }

    return buffer;
}

/**
 * Convert an array value into a value string.
 * @param array the array.
 * @param buffer the StringBuilder to use.
 */
void NameValuePair::array_value_string(CellValue *cell_value, string buffer)
{
    int element_count = 0;
    bool first = true;
    buffer.append("[");

    // Loop over each array element up to MAX_DISPLAYED_ELEMENTS times.
    for (int i = 0; i < cell_value->value->i; i++)
    {
        if (first) first = false;
        else       buffer.append(", ");

        if (++element_count <= MAX_DISPLAYED_ELEMENTS)
        {
            buffer += value_to_string(cell_value->cell_array[i]->get_value());
        }
        else {
            buffer += "...";
            break;
        }
    }

    buffer += "]";
}

/**
 * Convert a record value into a value string.
 * @param array the record.
 * @param buffer the StringBuilder to use.
 */
void NameValuePair::record_value_string(CellValue *cell_value, string buffer)
{
    MemoryMapImpl *memory_map = (MemoryMapImpl *) cell_value->memory_map;
    vector<string> names = memory_map->get_all_names();

    bool first = true;
    buffer += "{";

    for (string name : names)
    {
        Cell *map_cell = memory_map->get_cell(name);

        if (first) first = false;
        else       buffer.append(", ");

        CellValue *map_cell_value = map_cell->get_value();
        buffer += name + ": " + value_to_string(map_cell_value);
    }

    buffer += "}";
}

}}}}  // namespace wci::backend::interpreter::debuggerimpl
