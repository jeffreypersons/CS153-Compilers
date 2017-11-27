/** High level client class for running the SimpL Compiler. */
public class SimpLMain
{
    public static void main(String[] args) throws Exception
    {
        if (args.length == 0)
        {
            System.out.println("Invalid number of arguments");
            System.out.println("Usage: java SimpLMain test.txt");
            System.exit(1);
        }
        String fileName = args[0];
        SimpLCompiler simpLCompiler = new SimpLCompiler(fileName);
        simpLCompiler.generateObjectCode();
    }
}
