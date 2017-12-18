import gen.simpLBaseListener;
import gen.simpLParser;
import main.cVisitor;
import java.io.PrintWriter;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;
import java.util.ArrayList;
import gen.simpLLexer;
import main.CodeEmitter;
import org.antlr.v4.runtime.tree.ParseTree;

public class simpLMain
{
	public static void main(String[] args) throws Exception
	{
        if(args.length == 0)
        { 
            System.out.println("Improper arguments");
            return;
        }
        String filename = args[0];
        ArrayList<String> program = new ArrayList<String>();
		simpLLexer lexer = new simpLLexer(CharStreams.fromFileName(filename));
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