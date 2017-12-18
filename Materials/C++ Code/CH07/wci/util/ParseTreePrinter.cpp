/**
 * <h1>ParseTreePrinter</h1>
 *
 * <p>Print a parse tree.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <iostream>
#include <string>
#include <vector>
#include "ParseTreePrinter.h"
#include "../intermediate/ICode.h"
#include "../intermediate/ICodeNode.h"
#include "../intermediate/icodeimpl/ICodeNodeImpl.h"

namespace wci { namespace util {

using namespace std;
using namespace wci::intermediate;
using namespace wci::intermediate::icodeimpl;

const int ParseTreePrinter::INDENT_WIDTH = 4;
const int ParseTreePrinter::LINE_WIDTH = 80;
string ParseTreePrinter::INDENT = string(INDENT_WIDTH, ' ');

ParseTreePrinter::ParseTreePrinter() : length(0), indentation("")
{
}

/**
 * Print the intermediate code as a parse tree.
 * @param iCode the intermediate code.
 */
void ParseTreePrinter::print(ICode *icode)
{
    cout << endl << "===== INTERMEDIATE CODE =====" << endl << endl;

    line = "";
    indentation = "";
    print_node(icode->get_root());
    print_line();
}

/**
 * Print a parse tree node.
 * @param node the parse tree node.
 */
void ParseTreePrinter::print_node(ICodeNode *node)
{
    // Opening tag.
    string type_name =
            ICodeNodeImpl::NODE_TYPE_NAMES[
                           (ICodeNodeTypeImpl) node->get_type()];
    append(indentation);
    append("<" + type_name);

    print_attributes(node);
    print_type_spec(node);

    vector<ICodeNode *> child_nodes = node->get_children();

    // Print the node's children followed by the closing tag.
    if (child_nodes.size() > 0)
    {
        append(">");
        print_line();

        print_child_nodes(child_nodes);
        append(indentation); append("</" + type_name + ">");
    }

    // No children: Close off the tag.
    else
    {
        append(" "); append("/>");
    }

    print_line();
}

/**
 * Print a parse tree node's attributes.
 * @param node the parse tree node.
 */
void ParseTreePrinter::print_attributes(ICodeNode *node)
{
    string save_indentation = indentation;
    indentation += INDENT;

    map<ICodeKey, NodeValue *>& contents =
            ((ICodeNodeImpl *) node)->get_contents();
    map<ICodeKey, NodeValue *>::iterator it;
    for (it = contents.begin(); it != contents.end(); it++)
    {
        print_attribute((ICodeKey) it->first, it->second);
    }

    indentation = save_indentation;
}

/**
 * Print a node attribute as key="value".
 * @param keyString the key string.
 * @param value the value.
 */
void ParseTreePrinter::print_attribute(ICodeKey key, NodeValue *node_value)
{
    DataValue *data_value = node_value->value;
    string key_name = ICodeNodeImpl::NODE_KEY_NAMES[(ICodeKeyImpl) key];
    string value_string;

    switch ((ICodeKeyImpl) key)
    {
        case ID:
        {
            value_string = node_value->id->get_name();
            break;
        }

        case LINE:
        case LEVEL:
        case VALUE:
        {
            value_string = data_value->display();
            break;
        }
    }

    string text = key_name + "=\"" + value_string + "\"";
    append(" "); append(text);

    // Include an identifier's nesting level.
    if (key == (ICodeKey) ID)
    {
        int level = node_value->id->get_symtab()->get_nesting_level();
        NodeValue v;
        v.value = new DataValue(level);
        print_attribute((ICodeKey) LEVEL, &v);
    }
}

/**
 * Print a parse tree node's child nodes.
 * @param child_nodes the array list of child nodes.
 */
void ParseTreePrinter::print_child_nodes(vector<ICodeNode *> child_nodes)
{
    string saveIndentation = indentation;
    indentation += INDENT;

    for (ICodeNode *child : child_nodes) print_node(child);

    indentation = saveIndentation;
}

/**
 * Print a parse tree node's type specification.
 * @param node the parse tree node.
 */
void ParseTreePrinter::print_type_spec(ICodeNode *node)
{
}

/**
 * Append text to the output line.
 * @param text the text to append.
 */
void ParseTreePrinter::append(string text)
{
    int text_length = text.length();
    bool line_break = false;

    // Wrap lines that are too long.
    if (length + text_length > LINE_WIDTH)
    {
        print_line();
        line += indentation;
        length = indentation.length();
        line_break = true;
    }

    // Append the text.
    if (!(line_break && (text == " ")))
    {
        line += text;
        length += text_length;
    }
}

/**
 * Print an output line.
 */
void ParseTreePrinter::print_line()
{
    if (length > 0)
    {
        cout << line << endl;
        line = "";
        length = 0;
    }
}

}}  // namespace wci::util
