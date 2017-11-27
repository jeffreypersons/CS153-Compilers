import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import gen.simpLBaseListener;
import gen.simpLLexer;
import gen.simpLParser;
import main.CodeEmitter;
import main.cVisitor;
import utils.FileUtils;

class SourceFileNotFoundException extends RuntimeException {}

/**
 * Client class for wrapping all the compiler components (lexer/parser/parseTree/symTabs/etc).
 */
public class simpLCompiler
{
    // todo: changes can be made here in future, since backend won't need to change every time...
    // but the lexer/parser wilL!
    private final simpLLexer lexer;
    private final simpLParser parser;
    private final ParseTree parseTree;
    private final cVisitor visitor;
    private final String sourceFileName;
    private final List<String> program = new ArrayList<>();

    public simpLCompiler(String sourceFileName)
    {
        try
        {
            lexer = new simpLLexer(CharStreams.fromFileName(sourceFileName));
        }
        catch (IOException e)
        {
            throw new SourceFileNotFoundException();
        }
        this.sourceFileName = sourceFileName;
        parser = new simpLParser(new CommonTokenStream(lexer));
        parser.addParseListener(new simpLBaseListener());

        parseTree = parser.program();
        visitor = new cVisitor();
    }

    /**
     * Generate jasmine code for given SimpL code file.
     */
    public void generateObjectCode()
    {
        // todo: require an extension like .sla or something, instead of the basename manipulation
        String assemblyFileName = FileUtils.getBaseName(sourceFileName) + ".j";

        visitor.visit(parseTree);
        FileUtils.writeText(assemblyFileName, CodeEmitter.Program(sourceFileName));
        FileUtils.writeLines(assemblyFileName, visitor.getText());
        FileUtils.writeText(assemblyFileName, "\nreturn\n" + CodeEmitter.EndMethod());
    }
}
