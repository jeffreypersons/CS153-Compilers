/**
 * <h1>StructuredDataGenerator</h1>
 *
 * <p>Generate code to allocate arrays, records, and strings.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef STRUCTUREDDATAGENERATOR_H_
#define STRUCTUREDDATAGENERATOR_H_

#include "../CodeGenerator.h"
#include "../../../intermediate/SymTabEntry.h"

namespace wci { namespace backend { namespace compiler { namespace generators {

class StructuredDataGenerator : public CodeGenerator
{
public:
    /**
     * Constructor.
     * @param the parent generator.
     */
    StructuredDataGenerator(CodeGenerator *parent);

    /**
     * Generate code to allocate the structured data of a program,
     * procedure, or function.
     * @param routine_id the symbol table entry of the routine's name.
     * @throw a string message if an error occurred.
     */
    void generate(SymTabEntry *routine_id) throw (string);
};

}}}}  // namespace wci::backend::compiler::generators

#endif /* STRUCTUREDDATAGENERATOR_H_ */
