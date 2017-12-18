/**
 * <h1>BackendFactory</h1>
 *
 * <p>A factory class that creates compiler and interpreter components.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <iostream>
#include <string>
#include "BackendFactory.h"
#include "compiler/CodeGenerator.h"
#include "interpreter/Executor.h"
#include "interpreter/Debugger.h"
#include "interpreter/debuggerimpl/CommandLineDebugger.h"
#include "../intermediate/symtabimpl/Predefined.h"

namespace wci { namespace backend {

using namespace std;
using namespace wci::backend::compiler;
using namespace wci::backend::interpreter;
using namespace wci::backend::interpreter::debuggerimpl;
using namespace wci::intermediate::symtabimpl;

Backend *BackendFactory::create_backend(string operation,
                                        istream &data_stream)
    throw (string)
{
    if (operation == "compile")
    {
        return new CodeGenerator();
    }
    else if (operation == "execute")
    {
        return new Executor(data_stream);
    }
    else
    {
        throw new string("Backend factory: Invalid operation '" +
                         operation + "'");
    }
}

Debugger *BackendFactory::create_debugger(
    DebuggerType type, Backend *backend, RuntimeStack *runtime_stack)
{
    switch (type)
    {
        case COMMAND_LINE:
        {
            return new CommandLineDebugger(backend, runtime_stack);
        }

        case GUI: return nullptr;

        default:  return nullptr;
    }
}

CellValue *BackendFactory::default_value(TypeSpec *typespec)
{
    typespec = typespec->base_type();

    if (typespec == Predefined::integer_type)
    {
        return new CellValue(0);
    }
    else if (typespec == Predefined::real_type)
    {
        return new CellValue(0.0f);
    }
    else if (typespec == Predefined::boolean_type)
    {
        return new CellValue(false);
    }
    else if (typespec == Predefined::char_type)
    {
        return new CellValue('#');
    }
    else // string
    {
        return new CellValue("#");
    }
}

}} // namespace wci::backend
