import org.antlr.v4.runtime.*;
import java.io.FileInputStream;
import java.io.InputStream;
import org.antlr.v4.runtime.tree.*; 
//import simpL.utils.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class simpLMain
{
	static final String test_program = "TEST";
	public static void main(String[] args) throws Exception
	{
		/*BufferedWriter writer = new BufferedWriter(new FileWriter(test_program + ".j"));
		writer.write(CodeEmitter.Program("TEST"));
		writer.write(CodeEmitter.Printi("PRINT ME MAN"));
		writer.write(CodeEmitter.EndMethod());
		writer.close();*/
		simpLLexer lexer = new simpLLexer(CharStreams.fromFileName("test_program.txt"));
    	simpLParser parser = new simpLParser( new CommonTokenStream( lexer ) );
    	//parser.addParseListener(new simpLBaseListener());
    	ParseTree tree = parser.program();
    	sVisitor visitor = new sVisitor();
    	visitor.visit(tree);
	}
}