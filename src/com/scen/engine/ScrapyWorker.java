package com.scen.engine;

import java.sql.SQLException;
import java.util.logging.Logger;

import com.scen.datastr.BeanListSingleton;
import com.GlobalCounter;
import com.scen.datastr.QueueFactory;
import com.scen.engface.IScrapyWorker;
import com.scen.engface.IStockPool;

public class ScrapyWorker implements Runnable {
    private static final Logger Log = Logger.getLogger(ScrapyWorker.class.getName());
    public boolean more = true;

    static IStockPool isp;
    IScrapyWorker worker;
    String threadid;

    public ScrapyWorker(IStockPool _isp, IScrapyWorker isw) {
        isp = _isp;
        worker = isw;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        threadid = Long.toString(Thread.currentThread().getId() + 500);
        Log.info("This scripy thread id is: " + threadid);
        if (isp.getPool() != null) {

            try {
                while (more) {
                    getStockCode();
                    Thread.sleep(10L);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                closeRS();
            }
        }
    }

    private void closeRS() {
        BeanListSingleton.DONE_Getdata.decrementAndGet();
        Log.info("Stop thread: " + Thread.currentThread() + " left: " + BeanListSingleton.DONE_Getdata);
        if (BeanListSingleton.DONE_Getdata.get() <= 0) {
            //  (QueueFactory.getXXXcount() >= 1) {
            synchronized (isp) {
                if (!isp.isClosed()) {
                    isp.closePool();
                    Log.info("Stop All Scrapy threads!!!");
                }
            }
            QueueFactory.ScrapyDone();
        }
    }

    private String getCode() {
        String scode = null;
        synchronized (isp) {
            if (isp.hasnext()) {
                scode = isp.getnext();
            }
        }
        return scode;
    }

    private void getStockCode() {
        String scode = getCode();
        if (scode != null) {
            //  synchronized (GlobalCounter.READ_SUM) {
            GlobalCounter.READ_SUM.incrementAndGet();
            //}
            //Log.info("Cycle###########################################" + scode + " :" + threadid);
            workOnce(scode);
        } else {
            more = false;
        }
    }

    private void workOnce(String stock_code) {
        if (stock_code != null && stock_code != "") {
            // printMsg(stock_code);
            int i = worker.startToWork(stock_code, threadid);
           // synchronized (GlobalCounter.SCRAPY_SUM) {
                GlobalCounter.SCRAPY_SUM.addAndGet(i);
            //}
        }
    }

//    private void LogPrint(String msg) {
////        if (log_flag) {
////            Log.info(msg);
////        } else
////            Log.info(msg);
//    }
////    private void printMsg(String stock_code) {
////        if (log_flag) {
////            Log.info(stock_code + " " + Thread.currentThread());
////        } else
////            Log.info(stock_code + " " + Thread.currentThread());
////    }
}
