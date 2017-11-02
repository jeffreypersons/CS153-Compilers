#include <iostream>

#include "HelloLexer.h"
#include "antlr4-runtime.h"
#include "HelloParser.h"
#include "HelloBaseListener.h"

using namespace antlr4;
using namespace std;


void main(int args, char* argc[])
{
	string in = " ";
	if(args == 1) in = "hello man\nhello man";
	else
	{
		for(int x = 1; x < args; x++)
		{
			in += argc[x];
			in += " ";
		}
	}
	CharStream *input = new ANTLRInputStream(in);
	HelloLexer *lexer = new HelloLexer(input);
	CommonTokenStream *tokens = new CommonTokenStream(lexer);
	HelloParser *parser = new HelloParser(tokens);

	parser->setBuildParseTree(false);

	Token* m;
	m = parser->consume();
	while(m->getText() != "<EOF>") 
	{
		cout << m->getText() << endl;
		m = parser->consume();
	}
	// add methods in parser
}