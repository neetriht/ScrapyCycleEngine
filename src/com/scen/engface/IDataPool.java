package com.scen.engface;

/**
 * Created with IntelliJ IDEA.
 * User: neetriht
 * Date: 2018-05-29
 * Time: 14:10
 * <p>
 * Description:
 */
public interface IDataPool {

    public boolean hasnext();

    public String[] getnext(int[] position);

    public void closePool();

    public Object getPool();

    public boolean isClosed();
}
