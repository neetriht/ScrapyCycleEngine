package com.scen.engface;

public interface IStockPool {

    //public void setPool(Object o);

    public boolean hasnext();

    public String getnext();

    public void closePool();

    public Object getPool();

    public boolean isClosed();
}
