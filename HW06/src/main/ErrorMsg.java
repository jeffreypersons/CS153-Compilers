package main;

import gen.SimpLParser;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

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

        throw new RuntimeException();
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

        for(int i = 0; i < charPosInLine - 1; i++)
            System.err.print(" ");

        int start = ctx.getStop().getStartIndex();
        int stop = ctx.getStop().getStopIndex();
        if(start >= 0 && stop >= 0)
            for(int i = start; i <= stop; i++)
                System.err.print("^");
        System.err.println();

        throw new RuntimeException();
    }
}
