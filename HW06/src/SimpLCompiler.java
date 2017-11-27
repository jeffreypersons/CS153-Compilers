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


class SourceFileNotFoundException extends RuntimeException {}

/**
 * Client class for wrapping all the compiler components (lexer/parser/parseTree/symTabs/etc).
 */
public class SimpLCompiler
{
    // todo: changes can be made here in future, since backend won't need to change every time...
    // but the lexer/parser wilL!
    private final SimpLLexer lexer;
    private final SimpLParser parser;
    private final ParseTree parseTree;
    private final CVisitor visitor;
    private final String sourceFileName;
    private final List<String> program = new ArrayList<>();

    public SimpLCompiler(String sourceFileName)
    {
        try
        {
            lexer = new SimpLLexer(CharStreams.fromFileName(sourceFileName));
        }
        catch (IOException e)
        {
            throw new SourceFileNotFoundException();
        }
        this.sourceFileName = sourceFileName;
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
        // todo: require an extension like .sla or something, instead of the basename manipulation
        String assemblyFileName = FileUtils.getBaseName(sourceFileName) + ".j";

        FileUtils.writeText(assemblyFileName, CodeEmitter.Program(sourceFileName));
        visitor.visit(parseTree);
        FileUtils.writeLines(assemblyFileName, visitor.getText());
        FileUtils.writeText(assemblyFileName, "\nreturn\n" + CodeEmitter.EndMethod());
    }
}
