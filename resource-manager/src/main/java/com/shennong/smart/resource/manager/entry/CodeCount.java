package com.shennong.smart.resource.manager.entry;

public class CodeCount {
    private int codeCountNum;

    public CodeCount(int codeCountNum) {
        this.codeCountNum = codeCountNum;
    }

    public void addOne(){
        codeCountNum++;
    }

    public int getCodeCountNum() {
        return codeCountNum;
    }
}
