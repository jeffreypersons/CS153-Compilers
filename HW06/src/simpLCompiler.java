import gen.simpLBaseListener;
import gen.simpLLexer;
import gen.simpLParser;
import main.CodeEmitter;
import main.cVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

class SourceFileNotFoundException extends RuntimeException
{

}

public class simpLCompiler
{
    private simpLLexer lexer;
    simpLParser parser;

    public simpLCompiler(String sourceFile)
    {
        simpLLexer lexer = null;
        try
        {
            lexer = new simpLLexer(CharStreams.fromFileName(sourceFile));
        }
        catch (IOException e)
        {
            throw new SourceFileNotFoundException();
        }

        simpLParser parser = new simpLParser(new CommonTokenStream(lexer));
        parser.addParseListener(new simpLBaseListener());

        ParseTree tree = parser.program();
        cVisitor visitor = new cVisitor();

        generateCode(sourceFile);
    }

    /**
     * Generate code for given SimpL code file.
     */
    private static void generateCode(String filename)
    {
        PrintWriter a = new PrintWriter(filename + ".j");
        a.write(CodeEmitter.Program(filename));
        visitor.visit(tree);
        for (String str : visitor.getText())
            a.write("\n" + str);
        a.write("\nreturn\n" + CodeEmitter.EndMethod());
        a.close();
    }

    /**
     * TODO: MOVE THIS TO A UTILS CLASS
     * Return basename of given path, without extension or leading dot.
     * For example:
     *   ~/Users/.tmp => tmp, /.x.y.z/ => x, foo.tar.gz => foo
     */
    private static String getBaseName(String path)
    {
        String rawName = new File(path).getName();
        if (rawName.equals("") || rawName.equals("."))
            return "";

        // ignoring starting dots, and end at the first dot found
        int dotIndex = rawName.substring(1).indexOf('.');
        int start = rawName.startsWith(".")? 1 : 0;
        int end = dotIndex == -1? rawName.length() : dotIndex + 1;
        return rawName.substring(start, end);
    }
}
