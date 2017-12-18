package main;

import java.util.ArrayList;

public class FuncInfo
{
    private String funcName;
    private String funcType;
    private int numOfParam;
    private ArrayList<String> paramTypeInfo;

    public FuncInfo()
    {
        funcName = "";
        funcType = "";
        numOfParam = 0;
        paramTypeInfo = new ArrayList<String>();
    }

    public void setFuncName(String newName) { this.funcName = newName; }
    public void setNumOfParam(int newNum) { this.numOfParam = newNum; }
    public void setFuncType(String newType) { this.funcType = newType; }
    public void addParamType(String type) { this.paramTypeInfo.add(type); }
    public void incrementNumOfParam() { this.numOfParam++; }

    public String getFuncName() { return funcName; }
    public int getNumOfParam() { return numOfParam; }
    public String getFuncType() { return funcType; }
    public ArrayList<String> getParamTypeInfo() { return paramTypeInfo; }
}
