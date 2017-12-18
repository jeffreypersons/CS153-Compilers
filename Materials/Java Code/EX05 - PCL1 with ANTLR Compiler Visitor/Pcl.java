/***
 * Excerpted from "The Definitive ANTLR 4 Reference",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpantlr2 for more book information.
***/
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class Pcl 
{
    public static void main(String[] args) throws Exception 
    {
        String inputFile = null;
        
        if (args.length > 0) inputFile = args[0];
        InputStream is = (inputFile != null)
                                ? new FileInputStream(inputFile)
                                : System.in;
        
        ANTLRInputStream input = new ANTLRInputStream(is);
        PclLexer lexer = new PclLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PclParser parser = new PclParser(tokens);
        ParseTree tree = parser.program();

        CompilerVisitor compiler = new CompilerVisitor();
        compiler.visit(tree);
    }
}
