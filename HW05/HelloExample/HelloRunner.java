import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class HelloRunner
{
    public static void main( String[] args) throws Exception
    {
        // Create a CharStream that reads from standard input.
        ANTLRInputStream input = new ANTLRInputStream( System.in);

        // Create a lexer that feeds off of input CharStream.
        HelloLexer lexer = new HelloLexer(input);

        // Create a buffer of tokens pulled from the lexer.
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Print the tokens.
        System.out.println("\nTokens:");
        tokens.fill();
        for (Token token : tokens.getTokens())
        {
                        System.out.println(token.toString());

        // Create a parser that feeds off the tokens buffer.
        HelloParser parser = new HelloParser(tokens);
        ParseTree tree = parser.r(); // begin parsing at rule 'r'

        System.out.println("\nParse tree (Lisp format):");
        System.out.println(tree.toStringTree(parser)); // print LISP-style tree
    }
}