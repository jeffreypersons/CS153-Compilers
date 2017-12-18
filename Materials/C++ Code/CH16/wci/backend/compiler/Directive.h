/**
 * <h1>Directive</h1>
 *
 * <p>Jasmin directives.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#ifndef DIRECTIVE_H_
#define DIRECTIVE_H_

#include <map>

namespace wci { namespace backend { namespace compiler {

using namespace std;

enum class DirCode
{
    CLASS_PUBLIC,
    END_CLASS,
    SUPER,
    FIELD_PRIVATE_STATIC,
    METHOD_PUBLIC,
    METHOD_STATIC,
    METHOD_PUBLIC_STATIC,
    METHOD_PRIVATE_STATIC,
    END_METHOD,
    LIMIT_LOCALS,
    LIMIT_STACK,
    VAR,
    LINE,
};

constexpr DirCode CLASS_PUBLIC = DirCode::CLASS_PUBLIC;
constexpr DirCode END_CLASS = DirCode::END_CLASS;
constexpr DirCode SUPER = DirCode::SUPER;
constexpr DirCode FIELD_PRIVATE_STATIC = DirCode::FIELD_PRIVATE_STATIC;
constexpr DirCode METHOD_PUBLIC = DirCode::METHOD_PUBLIC;
constexpr DirCode METHOD_STATIC = DirCode::METHOD_STATIC;
constexpr DirCode METHOD_PUBLIC_STATIC = DirCode::METHOD_PUBLIC_STATIC;
constexpr DirCode METHOD_PRIVATE_STATIC = DirCode::METHOD_PRIVATE_STATIC;
constexpr DirCode END_METHOD = DirCode::END_METHOD;
constexpr DirCode LIMIT_LOCALS = DirCode::LIMIT_LOCALS;
constexpr DirCode LIMIT_STACK = DirCode::LIMIT_STACK;
constexpr DirCode VAR = DirCode::VAR;
constexpr DirCode LINE = DirCode::LINE;

class Directive
{
public:
    /**
     * Constructor
     */
    Directive();

    /**
     * Print a directive.
     */
    friend ostream& operator << (ostream& ofs, const DirCode& dircode);

    /**
     * Initialize the static maps.
     */
    static void initialize();

private:
    static map<DirCode, string> TEXT;

    static bool INITIALIZED;
};

}}}  // namespace wci::backend::compiler

#endif /* DIRECTIVE_H_ */
