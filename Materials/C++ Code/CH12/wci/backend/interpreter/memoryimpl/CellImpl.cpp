/**
 * <h1>CellImpl</h1>
 *
 * <p>The interpreter's runtime memory cell.</p>
 *
 * <p>Copyright (c) 2009 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include "CellImpl.h"
#include "../Cell.h"
#include "../../../DataValue.h"

namespace wci { namespace backend { namespace interpreter { namespace memoryimpl {

using namespace wci;

CellImpl::CellImpl() : cell_value(nullptr)
{
}

CellImpl::CellImpl(CellValue *value) : cell_value(value)
{
}

CellImpl::~CellImpl()
{
    delete cell_value;
}

CellValue *CellImpl::get_value() const { return cell_value; }

void CellImpl::set_value(CellValue *new_value) { cell_value = new_value; }

}}}}  // namespace wci::backend::interpreter::memoryimpl
