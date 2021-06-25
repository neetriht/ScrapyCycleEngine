package com.scen.datastr;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CodeListSingleton {

    static CodeListSingleton bls = null;
    public static AtomicInteger DONE_Getdata = new AtomicInteger(0);
    public static AtomicInteger NUM_SUB = new AtomicInteger(0);
    //static Vector<String> CodeQueue = new Vector<String>();
    //static List<String> CodeQueue = Collections.synchronizedList(new Vector<String>());
    static CopyOnWriteArrayList<String> CodeQueue = new CopyOnWriteArrayList<String>();
    //CopyOnWriteArrayList
    static ResultSet RESULTSET;

    public CodeListSingleton() {
    }

    public static CodeListSingleton getInstence() {
        if (bls == null) {
            bls = new CodeListSingleton();
            System.out.println("new BeanList!!!");
        }
        return bls;
    }

    public static CopyOnWriteArrayList<String> getCodeQueue() {
        return CodeQueue;
    }

    public static ResultSet getRESULTSET() {
        return RESULTSET;
    }

    public static void setRESULTSET(ResultSet rESULTSET) {
        RESULTSET = rESULTSET;
    }

}
