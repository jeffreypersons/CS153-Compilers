/**
 * <h1>LocalVariables</h1>
 *
 * <p>Maintain a method's local variables array.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <vector>
#include "LocalVariables.h"

namespace wci { namespace backend { namespace compiler {

using namespace std;

LocalVariables::LocalVariables(int index)
{
    for (int i = 0; i <= index; ++i)
    {
        reserved.push_back(true);
    }
}

int LocalVariables::reserve()
{
    // Search for existing but unreserved local variables.
    for (int i = 0; i < reserved.size(); ++i)
    {
        if (!reserved[i])
        {
            reserved[i] = true;
            return i;
        }
    }

    // Reserved a new variable.
    reserved.push_back(true);
    return reserved.size() - 1;
}

void LocalVariables::release(int index)
{
    reserved[index] = false;
}

int LocalVariables::count()
{
    return reserved.size();
}

}}}  // namespace wci::backend::compiler
