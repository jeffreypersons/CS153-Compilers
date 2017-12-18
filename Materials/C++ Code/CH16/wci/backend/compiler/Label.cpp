/**
 * <h1>Label</h1>
 *
 * <p>Jasmin instruction label.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <sstream>
#include <iomanip>
#include <string>
#include "Label.h"

namespace wci { namespace backend { namespace compiler {

using namespace std;

int Label::index = 0;  // index for generating label strings

Label::Label()
{
    stringstream ss;
    ss << setw(3) << setfill('0') << ++index;
    label = "L" + ss.str();
}

string Label::get_string() const { return label; }

Label *Label::new_label()
{
    return new Label();
}

}}}  // namespace wci::backend::compiler
