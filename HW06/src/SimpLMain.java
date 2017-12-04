import utils.FileUtils;


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

        String simpFilepath = args[0];
        if (!FileUtils.isFile(simpFilepath))
        {
            System.out.println("**Error processing SimpL input file**");
            System.out.println("  Invalid file path " + FileUtils.getLastExtension(simpFilepath));
            System.out.println("  File does not exist");
            System.exit(1);
        }
        if (!FileUtils.getLastExtension(simpFilepath).equals("simpl"))
        {
            System.out.println("**Error processing SimpL input file**");
            System.out.println("  Invalid file extension for file " + FileUtils.getLastExtension(simpFilepath));
            System.out.println("  Only .simpl files are supported");
            System.exit(1);
        }
        System.out.println();
        SimpLCompiler simpLCompiler = new SimpLCompiler(simpFilepath);
        simpLCompiler.generateObjectCode();
    }
}
