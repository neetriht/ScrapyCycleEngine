package com.scen.engine;

import com.scen.engface.IDataPool;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: neetriht
 * Date: 2018-05-29
 * Time: 14:11
 * <p>
 * Description:
 */
public class ValuesPool implements IDataPool{

    static ResultSet rs = null;

    public ValuesPool(ResultSet rs) {
        this.rs = rs;
    }

    @Override
    public boolean hasnext() {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        try {
            // return rs.getString(1);
            synchronized (rs) {
                if (!rs.isClosed()) {
                    int s = position.length;
                    String[] backvalues = new String[s];
                    for (int i = 0; i < s; i++) {
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
        // TODO Auto-generated method stub
        try {
            rs.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Object getPool() {
        // TODO Auto-generated method stub
        return rs;
    }

    @Override
    public boolean isClosed() {
        // TODO Auto-generated method stub
        try {
            return rs.isClosed();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return true;
        }
    }
}
