import java.util.ArrayList;

import wci.intermediate.*;
import wci.intermediate.symtabimpl.*;
import wci.util.*;

import static wci.intermediate.symtabimpl.SymTabKeyImpl.*;
import static wci.intermediate.symtabimpl.DefinitionImpl.*;


public class CompilerVisitor extends PclBaseVisitor<Integer>
{
    private SymTabStack symTabStack;
    private SymTabEntry programId;
    private ArrayList<SymTabEntry> variableIdList;
    private TypeSpec dataType;

    public CompilerVisitor()
    {
        // Create and initialize the symbol table stack.
        symTabStack = SymTabFactory.createSymTabStack();
        Predefined.initialize(symTabStack);
    }

    @Override
    public Integer visitProgram(PclParser.ProgramContext ctx)
    {
        System.out.println("Visiting program");
        Integer value = visitChildren(ctx);

        // Print the cross-reference table.
        CrossReferencer crossReferencer = new CrossReferencer();
        crossReferencer.print(symTabStack);

        return value;
    }

    @Override
    public Integer visitHeader(PclParser.HeaderContext ctx)
    {
        String programName = ctx.IDENTIFIER().toString();
        System.out.println("Program name = " + programName);

        programId = symTabStack.enterLocal(programName);
        programId.setDefinition(DefinitionImpl.PROGRAM);
        programId.setAttribute(ROUTINE_SYMTAB, symTabStack.push());
        symTabStack.setProgramId(programId);

        return visitChildren(ctx);
    }

    @Override
    public Integer visitDecl(PclParser.DeclContext ctx)
    {
        System.out.println("Visiting dcl");
        return visitChildren(ctx);
    }

    @Override
    public Integer visitVar_list(PclParser.Var_listContext ctx)
    {
        System.out.println("Visiting variable list");
        variableIdList = new ArrayList<SymTabEntry>();

        return visitChildren(ctx);
    }

    @Override
    public Integer visitVar_id(PclParser.Var_idContext ctx)
    {
        String variableName = ctx.IDENTIFIER().toString();
        System.out.println("Declared Id = " + variableName);

        SymTabEntry variableId = symTabStack.enterLocal(variableName);
        variableId.setDefinition(VARIABLE);
        variableIdList.add(variableId);

        return visitChildren(ctx);
    }

    @Override
    public Integer visitType_id(PclParser.Type_idContext ctx)
    {
        String typeName = ctx.IDENTIFIER().toString();
        System.out.println("Type = " + typeName);

        dataType = typeName.equalsIgnoreCase("integer")
                ? Predefined.integerType
                : typeName.equalsIgnoreCase("real")
                ? Predefined.realType
                : null;

        for (SymTabEntry id : variableIdList) id.setTypeSpec(dataType);

        return visitChildren(ctx);
    }
}