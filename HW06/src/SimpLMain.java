
// todo: split up run compile functionality as command line option (via java program args)

/** High level client class for running the SimpL Compiler. */
public class SimpLMain
{
    /** Takes simpl filepath, and generates jasmin code in the same directory. */
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("**Error processing SimpL input file**");
            System.out.println("  Invalid number of arguments");
            System.out.println("  Run as $ java SimpLMain <simpl_filepath>");
            System.exit(1);
        }
        System.out.println();

        SimpLCompiler compiler = new SimpLCompiler(args[0]);
        compiler.compile();
    }
}
