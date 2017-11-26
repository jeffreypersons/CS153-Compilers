import java.io.PrintWriter;
import java.nio.file.Paths;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;


public class simpLMain
{
	public static void main(String[] args) throws Exception
	{
        if(args.length == 0)
        { 
            System.out.println("Invalid number of arguments");
            System.out.println("Usage: java simpLMain");
            return;
        }
        String filename = args[0];
		simpLLexer lexer = new simpLLexer(CharStreams.fromPath(Paths.get(filename)));
    	simpLParser parser = new simpLParser( new CommonTokenStream( lexer ) );
    	parser.addParseListener(new simpLBaseListener());
    	ParseTree tree = parser.program();
    	cVisitor visitor = new cVisitor();

        PrintWriter a = new PrintWriter(filename + ".j");
        a.write(CodeEmitter.Program(filename));
    	visitor.visit(tree);
        for(String str : visitor.getText())
        {
            a.write("\n" + str);
        }
    	a.write("\nreturn\n" + CodeEmitter.EndMethod());
        a.close();
	}
}
