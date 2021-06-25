package com.scen.datastr;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class BeanListSingleton {
    static CopyOnWriteArrayList<String[]> BeanQueue = new CopyOnWriteArrayList<String[]>();
    static BeanListSingleton bls = null;
    static ResultSet RESULTSET;

    public static AtomicInteger DONE_Getdata = new AtomicInteger(0);
    public static AtomicInteger NUM_SUB = new AtomicInteger(0);

    public BeanListSingleton() {
        getInstence();
    }

    public static BeanListSingleton getInstence() {
        if (bls == null) {
            bls = new BeanListSingleton();
            System.out.println("+++++++++++++++++++++++++++++++++=new BeanList!!!");
        }
        return bls;
    }

    public static CopyOnWriteArrayList<String[]>  getBeanQueue() {
        return BeanQueue;
    }

    public static ResultSet getRESULTSET() {
        return RESULTSET;
    }

    public static void setRESULTSET(ResultSet rESULTSET) {
        RESULTSET = rESULTSET;
    }

    // public static int getNUM_SUB() {
    // return NUM_SUB;
    // }

    // public static void setNUM_SUB(int nUM_SUB) {
    // NUM_SUB = nUM_SUB;
    // }

}
