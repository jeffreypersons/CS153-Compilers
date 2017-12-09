import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import gen.SimpLBaseListener;
import gen.SimpLLexer;
import gen.SimpLParser;
import exceptions.InvalidSourceFileException;
import main.CodeEmitter;
import main.CVisitor;
import utils.FileUtils;


// todo: move lexer/parser/visitor/tree creation/code generation details to another wrapper class and use here

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
        if (!FileUtils.isFile(filepath))
        {
            throw new InvalidSourceFileException("Source file does not exist");
        }
        if (!FileUtils.getEntireFileExtension(filepath).equals(".simpl"))
        {
            throw new InvalidSourceFileException("Source file must end with .simpl extension");
        }
        CharStream sourceFileStream;
        try
        {
            sourceFileStream = CharStreams.fromFileName(filepath);
        }
        catch (IOException e)
        {
            throw new InvalidSourceFileException("Internal IOException occurred while lexing program");
        }

        this.workingDirectory = FileUtils.getParentDir(filepath);
        this.sourceFilename = FileUtils.getBaseName(filepath);

        this.lexer = new SimpLLexer(sourceFileStream);
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

        FileUtils.appendText(jasminPath, CodeEmitter.program(FileUtils.getBaseName(jasminPath)));
        visitor.visit(parseTree);
        FileUtils.appendLines(jasminPath, visitor.getText());
        FileUtils.appendText(
            jasminPath, "\nreturn\n" + CodeEmitter.endMethod()
        );
        jasmin.Main.main(new String[]{jasminPath, "-d", workingDirectory});  // todo: move to a utility class...
    }
}
