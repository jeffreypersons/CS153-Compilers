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
import main.CodeEmitter;
import main.CVisitor;
import utils.FileUtils;
import main.UnderlineListener;

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

    public SimpLCompiler(String filepath) throws IOException
    {
        FileUtils.ensureFileExists(filepath);
        FileUtils.ensureFileExtension(filepath, ".simpl");
        CharStream sourceFileStream;
        try
        {
            sourceFileStream = CharStreams.fromFileName(filepath);
        }
        catch (IOException e)
        {
            throw new IOException("Internal IOException occurred while decoding source file " + filepath, e);
        }

        this.workingDirectory = FileUtils.getParentDir(filepath);
        this.sourceFilename = FileUtils.getBaseName(filepath);

        this.lexer = new SimpLLexer(sourceFileStream);
        this.parser = new SimpLParser(new CommonTokenStream(lexer));
        this.parser.addParseListener(new SimpLBaseListener());
        this.parser.removeErrorListeners(); // remove ConsoleErrorListener
        this.parser.addErrorListener(new UnderlineListener());  // add our error listener
        this.parseTree = parser.program();
        this.visitor = new CVisitor();
    }

    /**
     * Compile single SimpL source file.
     * Note: Jasmin and class files are generated in same directory as SimpL file.
     */
    public void compile()
    {
        // todo: move the code emitter details to itself, and make it private not static based
        String jasminPath = FileUtils.joinPaths(workingDirectory, sourceFilename + ".j");
        String classPath = FileUtils.joinPaths(workingDirectory, sourceFilename + ".class");
        FileUtils.delete(jasminPath);
        FileUtils.delete(classPath);

        FileUtils.appendText(jasminPath, CodeEmitter.program(FileUtils.getBaseName(jasminPath)));
        visitor.visit(parseTree);
        FileUtils.appendLines(jasminPath, visitor.getText());
        FileUtils.appendText(jasminPath, "\nreturn\n" + CodeEmitter.endMethod());

        // equivalent to `$ jasmin <jasmin_filepath> -d <working_directory> -g`
        jasmin.Main.main(new String[]{jasminPath, "-d", workingDirectory, "-g"});
    }
}
