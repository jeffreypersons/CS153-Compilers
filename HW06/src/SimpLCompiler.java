import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jasmin.Main;
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
    private final String sourceFilename;
    private final String workingDirectory;
    private final List<String> program = new ArrayList<>();

    public SimpLCompiler(String filepath)
    {
        this.workingDirectory = FileUtils.getParentDir(filepath);
        this.sourceFilename = FileUtils.getBaseName(filepath);
        try
        {
            this.lexer = new SimpLLexer(CharStreams.fromFileName(filepath));
        }
        catch (IOException e)
        {
            throw new SourceFileNotFoundException();
        }
        this.parser = new SimpLParser(new CommonTokenStream(lexer));
        this.parser.addParseListener(new SimpLBaseListener());
        this.parseTree = parser.program();
        this.visitor = new CVisitor();
    }
    /**
     * Generate jasmine code for given SimpL code file.
     */
    public void compile()
    {
        String jasminPath = FileUtils.joinPaths(workingDirectory, sourceFilename + ".j");
        String classPath = FileUtils.joinPaths(workingDirectory, sourceFilename + ".class");
        FileUtils.delete(jasminPath);
        FileUtils.delete(classPath);

        FileUtils.appendText(jasminPath, CodeEmitter.program(jasminPath));
        visitor.visit(parseTree);
        FileUtils.appendLines(jasminPath, visitor.getText());
        FileUtils.appendText(
            jasminPath, "\nreturn\n" + CodeEmitter.endMethod()
        );
        System.out.println(jasminPath);
        jasmin.Main.main(new String[]{jasminPath});
    }
}
