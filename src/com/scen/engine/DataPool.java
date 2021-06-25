package com.scen.engine;

import com.scen.engface.IDataPool;
import com.scen.engface.IStockPool;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: neetriht
 * Date: 2018-05-29
 * Time: 14:19
 * <p>
 * Description:
 */
public class DataPool implements IDataPool {


    static ResultSet rs = null;

    public DataPool(ResultSet rs) {
        this.rs = rs;
    }


    @Override
    public boolean hasnext() {
        try {
            synchronized (rs) {
                if (!rs.isClosed())
                    return rs.next();
                return false;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("====================Error has next=============================");
            return false;
        }
    }

    @Override
    public String[] getnext(int[] position) {
        try {
            // return rs.getString(1);
            synchronized (rs) {
                if (!rs.isClosed()) {
                    String[] backvalues = new String[position.length];
                    for (int i = 0; i < position.length; i++) {
                        backvalues[i] = rs.getString(position[i]);
                    }

                    return backvalues;
                }
                return null;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("====================Error get next=============================");
            return null;
        }
    }

    @Override
    public void closePool() {
        try {
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Object getPool() {
        return rs;
    }

    @Override
    public boolean isClosed() {
        try {
            return rs.isClosed();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return true;
        }
    }
}
