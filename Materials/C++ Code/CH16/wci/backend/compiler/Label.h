/**
 * <h1>Label</h1>
 *
 * <p>Jasmin instruction label.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef LABEL_H_
#define LABEL_H_

#include <string>

namespace wci { namespace backend { namespace compiler {

using namespace std;

class Label
{
public:
    /**
     * Constructor.
     */
    Label();

    /**
     * Getter.
     * @return the label's string.
     */
    string get_string() const;

    /**
     * @return a new instruction label.
     */
    static Label *new_label();

    /**
     * Print a label string.
     */
    friend ostream& operator << (ostream& ofs, const Label *label);

private:
    static int index;  // index for generating label strings
    string label;      // the label string
};

}}}  // namespace wci::backend::compiler

#endif /* LABEL_H_ */
