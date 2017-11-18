import java.io.FileInputStream;
import java.io.InputStream;
import org.antlr.v4.runtime.tree.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;

public class simpLMain
{
	static final String test_program = "TEST";
	public static void main(String[] args) throws Exception
	{

		simpLLexer lexer = new simpLLexer(CharStreams.fromFileName("test_program.txt"));
    	simpLParser parser = new simpLParser( new CommonTokenStream( lexer ) );
    	parser.addParseListener(new simpLBaseListener());
    	//parser.program();
    	ParseTree tree = parser.program();
    	sVisitor visitor = new sVisitor();
    	visitor.visit(tree);
	}
}