package com.scen.datastr;

import com.GlobalStock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StoredList {
    public static List<String> CodeQueue;
    ResultSet rs = null;
    public static boolean getdata = true;

    public StoredList(List<String> ls) {
        CodeQueue = ls;
    }

    public StoredList(ResultSet rs) {
        this.rs = rs;
    }

    public ResultSet getResultSet() {
        return rs;
    }

//    RedisUtil ru = new RedisUtil();

    public void cyclecode(String head, int start, int end) {
        getdata = true;
        for (int i = start; i < end; i++) {

            String code = ListStocks(head, i);
            CodeQueue.add(code);
            //ru.getJedis().rpush("stocklist" + GlobalTimer.getToday(), SerializeUtil.objectSerialiable(code));
            //System.out.println("CODE2: " +i +" -- "+ code + " Size: " + CodeQueue.size());
        }
    }

    public void cyclecode(ResultSet rs) {
        CodeQueue.clear();
        getdata = true;
        try {
            while (rs.next()) {
                CodeQueue.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static String ListStocks(String header, int stock_num) {
        String head = "sh60";

        head = header;
        if (head.length() > 4)
            return head + GlobalStock.composeNumChar2(stock_num);
        return head + GlobalStock.composeNumChar3(stock_num);
    }


    public static int checkCode(String stock_code) {
        // 15,39,50 jj
        int codetype = stock;
        switch (stock_code.substring(0, 2)) {
            case "15": {
                codetype = JJ;
                break;
            }
            case "39": {
                codetype = JJ;
                break;
            }
            case "50": {
                codetype = JJ;
                break;
            }
        }
        return codetype;
    }

    final static int JJ = 1;
    final static int stock = 0;
}
