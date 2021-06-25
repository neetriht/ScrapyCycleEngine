package com.scen.datastr;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class IUBeanSingleton {
    static List<String[]> CLOSEQueue = Collections.synchronizedList(new LinkedList<String[]>());
    static List<String[]> UPDATEQueue = Collections.synchronizedList(new LinkedList<String[]>());
    static List<String[]> INSERTQueue = Collections.synchronizedList(new LinkedList<String[]>());
    static IUBeanSingleton bls = null;

    public IUBeanSingleton() {
    }

    public static IUBeanSingleton getInstence() {
        if (bls == null) {
            bls = new IUBeanSingleton();
            System.out.println("new BeanList!!!");
        }
        return bls;
    }

    public static List<String[]> getCLOSEQueue() {
        return CLOSEQueue;
    }

    public static List<String[]> getUPDATEQueue() {
        return UPDATEQueue;
    }

    public static List<String[]> getINSERTQueue() {
        return INSERTQueue;
    }

}
