package com.scen.engine;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.scen.engface.IStockPool;

public class QueuePool implements IStockPool {

    volatile static CopyOnWriteArrayList<String> ls = null;

    public QueuePool(CopyOnWriteArrayList<String> sl) {
        ls = sl;
    }

    public void setList(CopyOnWriteArrayList<String> sl) {
        ls = sl;
    }

    @Override
    public boolean hasnext() {
        // TODO Auto-generated method stub
        return !ls.isEmpty();
    }

    @Override
    public String getnext() {
        // TODO Auto-generated method stub
//        String code = "";
//        Iterator<String> iterator = ls.iterator();
//        if (iterator.hasNext()) {
//            code = iterator.next();
//            iterator.remove();//使用迭代器的删除方法删除
//        } else {
//            System.out.println("///LOGIC ERROR!!");
//        }
        return ls.remove(0);
    }

    @Override
    public void closePool() {
        // TODO Auto-generated method stub

    }

    @Override
    public Object getPool() {
        // TODO Auto-generated method stub
        return ls;
    }

    @Override
    public boolean isClosed() {
        // TODO Auto-generated method stub
        return ls.isEmpty();
    }

}
