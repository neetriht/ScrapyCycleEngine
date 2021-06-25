package com.scen.engface;

/**
 * describe:
 *
 * @author scott dai
 * @date 2018/11/19
 */
public abstract class ATasks {

    ITasks nexttask;

    public abstract void RunTask();

    public void TaskDone() {
        if (nexttask != null)
            nexttask.RunTask();
    }

    public void setTask(ITasks t) {
        nexttask = t;
    }
}
