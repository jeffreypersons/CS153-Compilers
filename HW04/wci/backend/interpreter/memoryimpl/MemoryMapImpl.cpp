/**
 * <h1>MemoryMapImpl</h1>
 *
 * <p>The interpreter's runtime memory map.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <vector>
#include <map>
#include "CellImpl.h"
#include "MemoryMapImpl.h"
#include "../Cell.h"
#include "../MemoryFactory.h"
#include "../../../intermediate/SymTab.h"
#include "../../../intermediate/symtabimpl/SymTabEntryImpl.h"
#include "../../../intermediate/TypeSpec.h"
#include "../../../intermediate/typeimpl/TypeSpecImpl.h"
#include "../../../DataValue.h"

namespace wci { namespace backend { namespace interpreter { namespace memoryimpl {

using namespace std;
using namespace wci;
using namespace wci::intermediate;
using namespace wci::intermediate::symtabimpl;
using namespace wci::intermediate::typeimpl;
using namespace wci::backend::interpreter;

MemoryMapImpl::MemoryMapImpl()
{
}

MemoryMapImpl::MemoryMapImpl(SymTab *symtab)
{
    vector<SymTabEntry *> entries = symtab->sorted_entries();

    // Loop for each entry of the symbol table.
    for (SymTabEntry *entry : entries)
    {
        Definition defn = entry->get_definition();
        Cell *cell;

        switch ((DefinitionImpl) defn)
        {
            // Not a VAR parameter: Allocate cells for the data type
            //                      in the memory map.
            case DF_VARIABLE:
            case DF_FUNCTION:
            case DF_VALUE_PARM:
            case DF_FIELD:
            {
                string name = entry->get_name();
                TypeSpec *typespec = entry->get_typespec();
                contents[name] =
                    (CellImpl *) MemoryFactory::create_cell(
                                        allocate_cell_value(typespec));
                cell = contents[name];
                break;
            }

            // VAR parameter: Allocate a single cell for a reference.
            case DF_VAR_PARM:
            {
                string name = entry->get_name();
                contents[name] =
                        (CellImpl *) MemoryFactory::create_cell(nullptr);
                cell = contents[name];
                break;
            }

            default: break;  // should never get here
        }
    }
}

map<string, Cell *> *MemoryMapImpl::get_contents()
{
    return &contents;
}

MemoryMapImpl::~MemoryMapImpl()
{
    map<string, Cell *>::iterator it;

    for (it = contents.begin(); it != contents.end(); it++)
    {
        delete it->second;
    }
}

Cell *MemoryMapImpl::get_cell(const string name)
{
    return contents[name];
}

vector<string> MemoryMapImpl::get_all_names()
{
    vector<string> list;
    map<string, Cell *>::iterator it;

    for (it = contents.begin(); it != contents.end(); it++)
    {
        list.push_back(it->first);
    }

    return list;
}

CellValue *MemoryMapImpl::allocate_cell_value(TypeSpec *typespec)
{
    TypeForm form = typespec->get_form();

    switch ((TypeFormImpl) form)
    {
        case TF_ARRAY:  return allocate_array_cells(typespec);
        case TF_RECORD: return allocate_record_map(typespec);

        default: return nullptr;  // uninitialized scalar value
    }
}

CellValue *MemoryMapImpl::allocate_array_cells(TypeSpec *typespec)
{
    TypeValue *type_value =
                typespec->get_attribute((TypeKey) ARRAY_ELEMENT_COUNT);
    int elmt_count = type_value->value->i;
    type_value = typespec->get_attribute((TypeKey) ARRAY_ELEMENT_TYPE);
    TypeSpec *elmt_typespec = type_value->typespec;
    Cell **allocation = new Cell*[elmt_count];

    for (int i = 0; i < elmt_count; ++i)
    {
        allocation[i] =
            MemoryFactory::create_cell(allocate_cell_value(elmt_typespec));
    }

    return new CellValue(allocation, elmt_count);
}

/**
 * Allocate the memory map for a record.
 * @param type the record type.
 * @return the allocation.
 */
CellValue *MemoryMapImpl::allocate_record_map(TypeSpec *typespec)
{
    TypeValue *type_value =
                typespec->get_attribute((TypeKey) RECORD_SYMTAB);
    SymTab *symtab = type_value->symtab;
    MemoryMap *memory_map = MemoryFactory::create_memory_map(symtab);
    return new CellValue(memory_map);
}

}}}}  // namespace wci::backend::interpreter::memoryimpl
