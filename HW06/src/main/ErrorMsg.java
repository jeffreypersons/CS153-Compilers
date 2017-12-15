package main;

import gen.SimpLParser;

public class ErrorMsg {

    public void throwError(SimpLParser.DeclarationContext ctx, String msg)
    {
        System.err.println("line " + ctx.getStart().getLine() + ": " + msg);

        for(int i = 0; i < ctx.getChildCount(); i++)
            System.err.print(ctx.getChild(i).getText() + " ");

        System.err.println();
    }

    public void throwError(SimpLParser.AssignmentContext ctx, String msg)
    {
        int charPosInLine = 0;
        for(int i = 0; i < ctx.getChildCount(); i++)
            if(ctx.getChild(i) instanceof SimpLParser.ExprContext)
                charPosInLine = ((SimpLParser.ExprContext) ctx.getChild(i)).getStart().getCharPositionInLine();

        System.err.println("line " + ctx.getStart().getLine() + ":" +
                (charPosInLine + 1) + " " + msg);

        for(int i = 0; i < ctx.getChildCount(); i++)
            System.err.print(ctx.getChild(i).getText() + " ");

        System.err.println();
    }

    public void throwError(SimpLParser.BlockContext ctx, String msg)
    {
        System.err.print(msg);
        for(int i = 0; i < ctx.getChildCount(); i++)
            System.err.print(ctx.getChild(i).getText() + " ");

        System.err.println();
    }

    public void throwError(SimpLParser.ExprContext ctx, String msg)
    {
        System.err.println("line " + ctx.getStart().getLine() + ": " + msg);

        for(int i = 0; i < ctx.getChildCount(); i++)
            System.err.print(ctx.getChild(i).getText() + " ");

        System.err.println();
        System.err.println();

    }

    public void throwError(SimpLParser.Func_callContext ctx, String msg)
    {
        System.err.println("line " + ctx.getStart().getLine() + ": " + msg);
        for(int i = 0; i < ctx.getChildCount(); i++)
            System.err.print(ctx.getChild(i).getText() + " ");

        System.err.println();
        System.err.println();

    }
}
