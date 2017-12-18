/**
 * <h1>CellTypePair</h1>
 *
 * <p>Memory cell and data type pair used by the debugger.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <vector>
#include <set>
#include <algorithm>
#include "CellTypePair.h"
#include "../Debugger.h"
#include "../Cell.h"
#include "../MemoryMap.h"
#include "../memoryimpl/MemoryMapImpl.h"
#include "../../../frontend/pascal/PascalToken.h"
#include "../../../intermediate/SymTab.h"
#include "../../../intermediate/SymTabEntry.h"
#include "../../../intermediate/symtabimpl/Predefined.h"
#include "../../../intermediate/TypeSpec.h"
#include "../../../intermediate/typeimpl/TypeSpecImpl.h"

namespace wci { namespace backend { namespace interpreter { namespace debuggerimpl {

using namespace std;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::intermediate::typeimpl;
using namespace wci::backend::interpreter::memoryimpl;
using namespace wci::backend::interpreter;

using wci::intermediate::SymTabEntry;

bool CellTypePair::INITIALIZED = false;

set<PascalTokenType> CellTypePair::MODIFIER_SET;

void CellTypePair::initialize()
{
    if (INITIALIZED) return;

    MODIFIER_SET.insert(PT_LEFT_BRACKET);
    MODIFIER_SET.insert(PT_DOT);

    INITIALIZED = true;
}

CellTypePair::CellTypePair(TypeSpec *typespec, Cell *cell,
                           Debugger *debugger) throw (string)
    : cell(cell), typespec(typespec), debugger(debugger)
{
    initialize();
    parse_variable();
}

Cell *CellTypePair::get_cell() const { return cell; }

TypeSpec *CellTypePair::get_typespec() const { return typespec; }

void CellTypePair::parse_variable() throw (string)
{
    TypeForm form = typespec->get_form();
    CellValue *cell_value = cell->get_value();

    // Loop to process array subscripts and record fields.
    while (MODIFIER_SET.find(
                (PascalTokenType) debugger->current_token()->get_type())
                    != MODIFIER_SET.end())
    {
        if (form == (TypeForm) TF_ARRAY)
        {
            parse_array_variable(cell_value->cell_array);
        }
        else if (form == (TypeForm) TF_RECORD)
        {
            parse_record_variable(cell_value->memory_map);
        }

        cell_value = cell->get_value();
        form = typespec->get_form();
    }
}

void CellTypePair::parse_array_variable(Cell **cell_array) throw (string)
{
    Token *token = debugger->next_token(nullptr);

    int index = debugger->get_integer("Integer index expected.");
    int min_value = 0;
    TypeSpec *index_typespec =
        typespec->get_attribute((TypeKey) ARRAY_INDEX_TYPE)->typespec;

    range_check(index, index_typespec, "Index out of range.");
    typespec =
        typespec->get_attribute((TypeKey) ARRAY_ELEMENT_TYPE)->typespec;

    if (index_typespec->get_form() == (TypeForm) TF_SUBRANGE)
    {
        min_value = index_typespec
                        ->get_attribute((TypeKey) SUBRANGE_MIN_VALUE)
                                                            ->value->i;
    }

    cell = cell_array[index - min_value];

    if (debugger->current_token()->get_type() == (TokenType) PT_RIGHT_BRACKET)
    {
        token = debugger->next_token(token);
    }
    else
    {
        throw string("] expected.");
    }
}

void CellTypePair::parse_record_variable(MemoryMap *memory_map) throw (string)
{
    MemoryMapImpl *mmap_impl = (MemoryMapImpl *) memory_map;
    debugger->next_token(nullptr);  // consume .

    string field_name = debugger->get_word("Field name expected.");
    transform(field_name.begin(), field_name.end(),
              field_name.begin(), ::tolower);

    map<string, Cell *> *contents = mmap_impl->get_contents();
    map<string, Cell *>::iterator it = contents->find(field_name);

    if (it != contents->end()) cell = it->second;
    else throw string("Invalid field name.");

    SymTab *symtab =
                typespec->get_attribute((TypeKey) RECORD_SYMTAB)->symtab;
    SymTabEntry *id = symtab->lookup(field_name);
    typespec = id->get_typespec();
}

void CellTypePair::set_value(CellValue *cell_value) throw (string)
{
    TypeSpec *base_type = typespec->base_type();
    DataValue *value = cell_value->value;

    if (   (    (base_type == Predefined::integer_type)
             && (value->type == INTEGER))
        || (    (base_type == Predefined::real_type)
             && (value->type == FLOAT))
        || (    (base_type == Predefined::boolean_type)
             && (value->type == BOOLEAN))
        || (    (base_type == Predefined::char_type)
             && (value->type == CHAR)))
    {
        if (base_type == Predefined::integer_type)
        {
            range_check(value->i, typespec, "Value out of range.");
        }

        cell->set_value(cell_value);
    }
    else throw string("Type mismatch.");
}

void CellTypePair::range_check(int value, TypeSpec *typespec,
                               string error_message)
    throw (string)
{
    TypeForm form = typespec->get_form();
    int min_value ;
    int max_value ;

    if (form == (TypeForm) TF_SUBRANGE)
    {
        min_value =
            typespec->get_attribute((TypeKey) SUBRANGE_MIN_VALUE)->value->i;
        max_value =
            typespec->get_attribute((TypeKey) SUBRANGE_MAX_VALUE)->value->i;
    }
    else if (form == (TypeForm) TF_ENUMERATION)
    {
        vector<SymTabEntry *> constants =
                typespec->get_attribute((TypeKey) ENUMERATION_CONSTANTS)->v;
        min_value = 0;
        max_value = constants.size() - 1;
    }
    else return;

    if ((value < min_value) || (value > max_value)) throw error_message;
}

}}}}  // namespace wci::backend::interpreter::debuggerimpl
