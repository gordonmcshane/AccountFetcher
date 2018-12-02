package com.gordon;

class DelayedService {

    protected void incurOverhead() {
        try {
            Thread.sleep(10);
        }
        catch (InterruptedException ignored)
        {}
    }
}
