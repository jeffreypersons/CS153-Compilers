package main;

import gen.SimpLParser;

public class ErrorMsg {
    public void throwError(SimpLParser.DeclarationContext ctx, String msg)
    {
        System.err.println("line " + ctx.getStart().getLine() + ":" +
                ctx.getStop().getStartIndex() + " " + msg);

        for(int i = 0; i < ctx.getChildCount(); i++)
            System.err.print(ctx.getChild(i).getText() + " ");

        for(int i = 0; i < ctx.getStop().getStartIndex() - 2; i++)
            System.err.print(" ");

        int start = ctx.getStop().getStartIndex();
        int stop = ctx.getStop().getStopIndex();
        if(start >= 0 && stop >= 0)
            for(int i = start; i <= stop; i++)
                System.err.print("^");
        System.err.println();
    }
}
