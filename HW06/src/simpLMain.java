/** High level client class for running the SimpL Compiler. */
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
        String fileName = args[0];
        simpLCompiler simpLCompiler = new simpLCompiler(fileName);
        simpLCompiler.generateObjectCode();
    }
}
