/**
 * <h1>LocalVariables</h1>
 *
 * <p>Maintain a method's local variables array.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef LOCALVARIABLES_H_
#define LOCALVARIABLES_H_

#include <vector>

namespace wci { namespace backend { namespace compiler {

using namespace std;

class LocalVariables
{
public:
    /**
     * Constructor.
     * @param index initially reserve local variables 0 through index-1.
     */
    LocalVariables(int index);

    /**
     * Reserve a local variable.
     * @return the index of the newly reserved variable.
     */
    int reserve();

    /**
     * Release a local variable that's no longer needed.
     * @param index the index of the variable.
     */
    void release(int index);

    /**
     * Return the count of local variables needed by the method.
     * @return the count.
     */
    int count();

private:
    // List of booleans to keep track of reserved local variables. The ith
    // element is true if the ith variable is being used, else it is false.
    // The final size of the list is the total number of local variables
    // used by the method.
    vector<bool> reserved;
};

}}}  // namespace wci::backend::compiler

#endif /* LOCALVARIABLES_H_ */
