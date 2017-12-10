import java.io.IOException;


// todo: add output directory command option for compiled files (eg -d <dirname>)

/** High level client class for running the SimpL Compiler. */
public class SimpLMain
{
    /** Compile and run a simpl file (jasmin and class file generated in same directory as input). */
    public static void main(String[] args) throws IOException
    {
        if (args.length != 1)
        {
            System.out.println("**Error processing SimpL input file**");
            System.out.println("  Invalid number of arguments");
            System.out.println("  Run as $ java SimpLMain <simpl_filepath>");
            System.out.println();
            System.exit(1);
        }

        SimpLCompiler compiler = new SimpLCompiler(args[0]);
        compiler.compile();
    }
}
