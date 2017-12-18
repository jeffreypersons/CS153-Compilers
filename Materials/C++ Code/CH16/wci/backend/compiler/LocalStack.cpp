/**
 * <h1>LocalStack</h1>
 *
 * <p>Maintain a method's local runtime stack.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <algorithm>
#include "LocalStack.h"

namespace wci { namespace backend { namespace compiler {

using namespace std;

LocalStack::LocalStack() : size(0), max_size(0)
{
}

int LocalStack::get_size() const { return size; }

void LocalStack::increase(int amount)
{
    size += amount;
    max_size = max(max_size, size);
}

void LocalStack::decrease(int amount)
{
    size -= amount;
}

void LocalStack::use(int amount)
{
    increase(amount);
    decrease(amount);
}

void LocalStack::use(const int amount_increase, const int amount_decrease)
{
    increase(amount_increase);
    decrease(amount_decrease);
}

int LocalStack::capacity() const { return max_size; }

}}}  // namespace wci::backend::compiler
