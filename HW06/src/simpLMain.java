import java.io.File;
import java.io.PrintWriter;

import main.CodeEmitter;
import main.cVisitor;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;


public class simpLMain
{
    public static void main(String[] args) throws Exception
    {
        if (args.length == 0)
        {
            System.out.println("Invalid number of arguments");
            System.out.println("Usage: java simpLMain test.txt");
            System.exit(1);
        }
        String baseFileName = getBaseName(args[0]);

        simpLLexer lexer = new simpLLexer(CharStreams.fromFileName(baseFileName));
        simpLParser parser = new simpLParser(new CommonTokenStream(lexer));
        parser.addParseListener(new simpLBaseListener());

        ParseTree tree = parser.program();
        cVisitor visitor = new cVisitor();

        PrintWriter a = new PrintWriter(baseFileName + ".j");
        a.write(CodeEmitter.Program(baseFileName));
        visitor.visit(tree);
        for (String str : visitor.getText())
        {
            a.write("\n" + str);
        }
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
