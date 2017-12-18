/**
 * <h1>StructuredDataGenerator</h1>
 *
 * <p>Generate code to allocate arrays, records, and strings.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include "StructuredDataGenerator.h"
#include "../CodeGenerator.h"
#include "../../../intermediate/SymTabEntry.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

StructuredDataGenerator::StructuredDataGenerator(CodeGenerator *parent)
    : CodeGenerator(parent)
{
}

void StructuredDataGenerator::generate(SymTabEntry *routine_id)
    throw (string)
{
}

}}}}  // namespace wci::backend::compiler::generators
