import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Pcl2 
{
    public static void main(String[] args) throws Exception 
    {
        String inputFile = null;
        
        if (args.length > 0) inputFile = args[0];
        InputStream is = (inputFile != null)
                                ? new FileInputStream(inputFile)
                                : System.in;
        
        ANTLRInputStream input = new ANTLRInputStream(is);
        Pcl2Lexer lexer = new Pcl2Lexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Pcl2Parser parser = new Pcl2Parser(tokens);
        ParseTree tree = parser.program();
        
        Pass1Visitor pass1 = new Pass1Visitor();
        pass1.visit(tree);
        
        PrintWriter jFile = pass1.getAssemblyFile();

        Pass2Visitor pass2 = new Pass2Visitor(jFile);
        pass2.visit(tree);
    }
}
