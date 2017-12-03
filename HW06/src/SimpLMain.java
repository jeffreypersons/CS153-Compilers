/** High level client class for running the SimpL Compiler. */
public class SimpLMain
{
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("**Error processing SimpL input file**");
            System.out.println("  Invalid number of arguments");
            System.out.println("  Run as $ java SimpLMain <source_filepath> <jasmin_filepath>");
            System.exit(1);
        }
        String simpFilepath = args[0];
        String jasminFilepath = args[1];
        SimpLCompiler simpLCompiler = new SimpLCompiler(simpFilepath, jasminFilepath);
        simpLCompiler.generateObjectCode();
    }
}
