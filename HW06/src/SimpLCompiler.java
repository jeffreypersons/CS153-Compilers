import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import gen.SimpLBaseListener;
import gen.SimpLLexer;
import gen.SimpLParser;
import main.CodeEmitter;
import main.CVisitor;
import utils.FileUtils;

// todo: reconsider what is source file dependent in SimpL compiler (instance) vs what is independent (static)

class SourceFileNotFoundException extends RuntimeException {}

/**
 * Client class for wrapping all the compiler components (lexer/parser/parseTree/symTabs/etc).
 */
public class SimpLCompiler
{
    private final SimpLLexer lexer;
    private final SimpLParser parser;
    private final ParseTree parseTree;
    private final CVisitor visitor;
    private final String simplFilepath;
    private final String jasminFilepath;
    private final List<String> program = new ArrayList<>();

    public SimpLCompiler(String simplFilepath, String jasminFilepath)
    {
        try
        {
            lexer = new SimpLLexer(CharStreams.fromFileName(simplFilepath));
        }
        catch (IOException e)
        {
            throw new SourceFileNotFoundException();
        }
        this.simplFilepath = simplFilepath;
        this.jasminFilepath = jasminFilepath;
        System.out.println(this.simplFilepath);
        System.out.println(this.jasminFilepath);
        parser = new SimpLParser(new CommonTokenStream(lexer));
        parser.addParseListener(new SimpLBaseListener());

        parseTree = parser.program();
        visitor = new CVisitor();
    }

    /**
     * Generate jasmine code for given SimpL code file.
     */
    public void generateObjectCode()
    {
        FileUtils.writeText(jasminFilepath, CodeEmitter.Program(simplFilepath));
        visitor.visit(parseTree);
        FileUtils.writeLines(jasminFilepath, visitor.getText());
        FileUtils.writeText(jasminFilepath, "\nreturn\n" + CodeEmitter.EndMethod());
    }
}
