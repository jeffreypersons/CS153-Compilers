/**
 * <h1>BackendFactory</h1>
 *
 * <p>A factory class that creates compiler and interpreter components.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef WCI_BACKEND_BACKENDFACTORY_H_
#define WCI_BACKEND_BACKENDFACTORY_H_

#include <iostream>
#include <string>
#include "Backend.h"
#include "interpreter/Cell.h"
#include "interpreter/Debugger.h"
#include "../intermediate/TypeSpec.h"

namespace wci { namespace backend {

using namespace std;
using namespace wci::backend::interpreter;

class BackendFactory
{
public:
    /**
     * Create a compiler or an interpreter back end component.
     * @param operation either "compile" or "execute"
     * @parm data_stream the input data stream
     * @return a compiler or an interpreter back end component.
     * @throw a string message if an error occurred.
     */
    static Backend *create_backend(string operation, istream &data_stream)
        throw (string);

    /**
     * Create a debugger.
     * @param type the type of debugger (COMMAND_LINE or GUI).
     * @param backend the backend.
     * @param runtimeStack the runtime stack
     * @return the debugger
     */
    static Debugger *create_debugger(DebuggerType type, Backend *backend,
                                     RuntimeStack *runtime_stack);

    /**
     * Return the default value for a data type.
     * @param typespec the data type.
     * @return the default value.
     */
    static CellValue *default_value(TypeSpec *typespec);
};

}} // namespace wci::backend

#endif /* WCI_BACKEND_BACKENDFACTORY_H_ */
