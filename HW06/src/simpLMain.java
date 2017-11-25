import java.io.FileInputStream;
import java.io.InputStream;
import org.antlr.v4.runtime.tree.*;
import java.io.PrintWriter;
import java.io.FileWriter;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;
import java.util.ArrayList;

public class simpLMain
{
	static final String test_program = "test_program.txt";
	public static void main(String[] args) throws Exception
	{
        ArrayList<String> program = new ArrayList<String>();
		simpLLexer lexer = new simpLLexer(CharStreams.fromFileName(test_program));
    	simpLParser parser = new simpLParser( new CommonTokenStream( lexer ) );
    	parser.addParseListener(new simpLBaseListener());
    	ParseTree tree = parser.program();
    	cVisitor visitor = new cVisitor();

        PrintWriter a = new PrintWriter("test_program.j");
        a.write(CodeEmitter.Program("test_program"));
    	visitor.visit(tree);
        for(String str : visitor.getText())
        {
            a.write("\n" + str);
        }
    	a.write("\nreturn\n" + CodeEmitter.EndMethod());
        a.close();
        //System.out.println("necessary locals: " + visitor.getMemory().size() + "\n\t" + visitor.getMemory());

        // create program as list of statements/text
       /** program.add(CodeEmitter.Program("test_program"));
        program.add(CodeEmitter.SetStack(visitor.getStackSize()));
        program.add(CodeEmitter.EndMethod());
        for(String s : program)
        {
            System.out.println(s);
        }

        //write output program here
        PrintWriter s = new PrintWriter("test_program.j");
        for(String str : program) s.write(str);
        s.close();*/
	}
}