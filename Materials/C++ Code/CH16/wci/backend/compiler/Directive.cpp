/**
 * <h1>Directive</h1>
 *
 * <p>Jasmin directives.</p>
 *
 * <p>Copyright (c) 2017 by Ronald Mak</p>
 * <p>For instructional purposes only.  No warranties.</p>
 */
#include <string>
#include <vector>
#include <map>
#include "Directive.h"

namespace wci { namespace backend { namespace compiler {

using namespace std;

bool Directive::INITIALIZED = false;
map<DirCode, string> Directive::TEXT;

void Directive::initialize()
{
    if (INITIALIZED) return;

    vector<DirCode> codes =
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

    vector<string> text =
    {
        ".class public",
        ".end class",
        ".super",
        ".field private static",
        ".method public",
        ".method static",
        ".method public static",
        ".method private static",
        ".end method",
        ".limit locals",
        ".limit stack",
        ".var",
        ".line",
    };

    for (int i = 0; i < codes.size(); i++) TEXT[codes[i]] = text[i];

    INITIALIZED = true;
}

Directive::Directive()
{
    initialize();
}

}}}  // namespace wci::backend::compiler
