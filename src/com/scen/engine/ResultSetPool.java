package com.scen.engine;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.scen.engface.IStockPool;

public class ResultSetPool implements IStockPool {

    static ResultSet rs = null;

    public ResultSetPool(ResultSet rs) {
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
    public String getnext() {
        // TODO Auto-generated method stub
        try {
            // return rs.getString(1);
            synchronized (rs) {
                if (!rs.isClosed()) {
                    return rs.getString(1);
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

//	@Override
//	public void setPool(Object o) {
//		// TODO Auto-generated method stub
//		rs = (ResultSet) o;
//	}
}
