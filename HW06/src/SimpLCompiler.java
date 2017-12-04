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

    public SimpLCompiler(String simplFilepath)
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
        this.jasminFilepath = FileUtils.joinPaths(
            FileUtils.getParentDir(simplFilepath), FileUtils.getBaseName(simplFilepath) + ".j"
        );
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
        FileUtils.delete(jasminFilepath);
        FileUtils.appendText(jasminFilepath, CodeEmitter.program(jasminFilepath));
        visitor.visit(parseTree);
        FileUtils.appendLines(jasminFilepath, visitor.getText());
        FileUtils.appendText(
            jasminFilepath, System.lineSeparator() + "return" + System.lineSeparator() + CodeEmitter.endMethod()
        );
    }
}
