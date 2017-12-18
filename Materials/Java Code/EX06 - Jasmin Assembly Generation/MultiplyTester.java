package test;
import engines.*;

public class MultiplyTester
{
    public static void main(String args[])
    {
        int op0 = Integer.parseInt(args[0]);
        int op1 = Integer.parseInt(args[1]);

        int prod = MultiplyEngine.multiply(op0, op1);

        System.out.println(op0 + " times " + op1 +
                                 " equals " + prod);
    }
}
