/**
 * <h1>Cell</h1>
 *
 * <p>Interface for the interpreter's runtime memory cell.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef CELL_H_
#define CELL_H_

#include <string>
#include "MemoryMap.h"
#include "../../DataValue.h"

namespace wci { namespace backend { namespace interpreter {

using namespace std;
using namespace wci;

// Forward declarations.
class Cell;

struct CellValue
{
    DataValue *value;
    Cell *cell;
    Cell **cell_array;
    MemoryMap *memory_map;

    CellValue()
        : value(nullptr), cell(nullptr), cell_array(nullptr),
          memory_map(nullptr) {}
    CellValue(DataValue *value)
        : value(new DataValue(*value)), cell(nullptr), cell_array(nullptr),
          memory_map(nullptr) {}
    CellValue(int i)
        : value(new DataValue(i)), cell(nullptr), cell_array(nullptr),
          memory_map(nullptr) {}
    CellValue(float f)
        : value(new DataValue(f)), cell(nullptr), cell_array(nullptr),
          memory_map(nullptr) {}
    CellValue(char c)
        : value(new DataValue(c)), cell(nullptr), cell_array(nullptr),
          memory_map(nullptr) {}
    CellValue(bool b)
        : value(new DataValue(b)), cell(nullptr), cell_array(nullptr),
          memory_map(nullptr) {}
    CellValue(string s)
        : value(new DataValue(s)), cell(nullptr), cell_array(nullptr),
          memory_map(nullptr) {}
    CellValue(Cell *cell)
        : value(nullptr), cell(cell), cell_array(nullptr),
          memory_map(nullptr) {}
    CellValue(Cell **array, int length)
        : value(new DataValue(length)), cell(nullptr), cell_array(array),
          memory_map(nullptr) {}
    CellValue(MemoryMap *map)
        : value(nullptr), cell(nullptr), cell_array(nullptr),
          memory_map(map) {}

    CellValue(const CellValue& orig)
    {
        this->cell = orig.cell;
        this->cell_array = orig.cell_array;
        this->memory_map = orig.memory_map;

        if (orig.value != nullptr)
        {
            this->value = new DataValue(*orig.value);
        }
    }

    ~CellValue()
    {
        if (cell_array != nullptr)
        {
            for (int i = 0; i < value->i; i++)
            {
                delete cell_array[i];
            }

            delete[] cell_array;
        }

        if (value != nullptr)
        {
            delete value;
        }

        if (memory_map != nullptr)
        {
            delete memory_map;
        }
    }
};

class Cell
{
public:
    /**
     * Destructor.
     */
    virtual ~Cell() {}

    /**
     * Defined by an implementation subclass.
     * @return the value in the cell.
     */
    virtual CellValue *get_value() const = 0;

    /**
     * Set a new value into the cell.
     * Defined by an implementation subclass.
     * @param new_value the new value.
     */
    virtual void set_value(CellValue *new_value) = 0;
};

}}}  // namespace wci::backend::interpreter

#endif /* CELL_H_ */
