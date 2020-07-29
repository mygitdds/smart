package com.shennong.smart.resource.manager.entry;

public class CreateCodeStatus {
    public CreateCodeStatus(Boolean status) {
        this.status = status;
    }

    private Boolean status;

    private int count;



    public synchronized void countNum(){
        count --;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public Boolean getStatus() {
        return status;
    }

    public synchronized void setStatus(Boolean status) {
        this.status = status;
    }
}
