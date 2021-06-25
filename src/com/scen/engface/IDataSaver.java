package com.scen.engface;

import java.sql.PreparedStatement;

public interface IDataSaver
{

	public int PrepRecord(String[] oneBean, String threadid);

	public PreparedStatement getPS();

	public IDataSaver newInstance();

}
