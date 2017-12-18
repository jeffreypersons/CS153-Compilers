package main;

import org.antlr.v4.runtime.*;

public class UnderlineListener extends BaseErrorListener
{
    public void syntaxError(Recognizer<?, ?> recognizer,
                            Object offendingSymbol,
                            int line, int charPosInLine,
                            String msg,
                            RecognitionException e)
    {
        System.err.println("line " + line + ":" + charPosInLine + " " + msg);
        underlineError(recognizer, (Token)offendingSymbol, line, charPosInLine);
    }

    protected void underlineError(Recognizer recognizer,
                                  Token offendingToken, int line,
                                  int charPosInLine)
    {
        CommonTokenStream tokens = (CommonTokenStream)recognizer.getInputStream();
        String input = tokens.getTokenSource().getInputStream().toString();
        String[] lines = input.split("\n");
        String errorLine = lines[line - 1];
        System.err.println(errorLine);
        for(int i = 0; i < charPosInLine; i++)
            System.err.print(" ");
        int start = offendingToken.getStartIndex();
        int stop = offendingToken.getStopIndex();
        if(start >= 0 && stop >= 0)
            for(int i = start; i <= stop; i++)
                System.err.print("^");
        System.err.println();
    }
}
