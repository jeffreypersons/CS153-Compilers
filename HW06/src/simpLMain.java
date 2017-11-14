import org.antlr.v4.runtime.*;
import java.io.FileInputStream;
import java.io.InputStream;
import org.antlr.v4.runtime.tree.*; 
//import

public class simpLMain
{
	public static void main(String[] args) throws Exception
	{
		simpLLexer lexer = new simpLLexer(CharStreams.fromFileName("test_program.txt"));
    	simpLParser parser = new simpLParser( new CommonTokenStream( lexer ) );
    	parser.addParseListener(new simpLBaseListener());
    	parser.program();
	}
}