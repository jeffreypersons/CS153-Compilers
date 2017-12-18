/**
 * <h1>CellTypePair</h1>
 *
 * <p>Memory cell and data type pair used by the debugger.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef CELLTYPEPAIR_H_
#define CELLTYPEPAIR_H_

#include <string>
#include <vector>
#include <set>
#include "../Debugger.h"
#include "../Cell.h"
#include "../MemoryMap.h"
#include "../memoryimpl/MemoryMapImpl.h"
#include "../../../frontend/pascal/PascalToken.h"
#include "../../../intermediate/TypeSpec.h"

namespace wci { namespace backend { namespace interpreter { namespace debuggerimpl {

using namespace std;
using namespace wci::intermediate;
using namespace wci::backend::interpreter;
using namespace wci::backend::interpreter::memoryimpl;

class CellTypePair
{
public:
    /**
     * Constructor.
     * @param typespec the data type.
     * @param cell the memory cell.
     * @param debugger the parent debugger.
     * @throw a string message if an error occurred.
     */
    CellTypePair(TypeSpec *typespec, Cell *cell, Debugger *debugger)
        throw (string);

    /**
     * Getter.
     * @return the memory cell.
     */
    Cell *get_cell() const;

    /**
     * Getter.
     * @return the data type.
     */
    TypeSpec *get_typespec() const;

    /**
     * Parse a variable in the command to obtain its memory cell.
     * @param type the variable's data type.
     * @param cell the variable's memory cell.
     * @throw a string message if an error occurred.
     */
    void parse_variable() throw (string);

    /**
     * Set the value of the cell.
     * @param cell_value the value.
     * @throw a string message if an error occurred.
     */
    void set_value(CellValue *cell_value) throw (string);

private:
    Cell *cell;          // memory cell
    TypeSpec *typespec;  // data type
    Debugger *debugger;  // parent debugger

    // Synchronization set for variable modifiers.
    static set<PascalTokenType> MODIFIER_SET;

    static bool INITIALIZED;

    /**
     * Initialize the static map.
     */
    static void initialize();

    /**
     * Parse an array variable.
     * @param cell_array the array of cells.
     * @throw a string message if an error occurred.
     */
    void parse_array_variable(Cell **cell_array) throw (string);

    /**
     * Parse a record variable.
     * @param memory_map the memory map to use.
     * @throw a string message if an error occurred.
     */
    void parse_record_variable(MemoryMap *memory_map) throw (string);

    /**
     * Do a range check on an integer value.
     * @param value the value.
     * @param type the data type.
     * @param error_message the error message if an exception is thrown.
     * @throw a string message if an error occurred.
     */
    void range_check(int value, TypeSpec *typespec, string error_message)
        throw (string);
};

}}}}  // namespace wci::backend::interpreter::debuggerimpl

#endif /* CELLTYPEPAIR_H_ */
