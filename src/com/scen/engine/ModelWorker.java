package com.scen.engine;

import com.GlobalCounter;
import com.scen.datastr.BeanListSingleton;
import com.scen.datastr.QueueFactory;
import com.scen.engface.IScrapyWorker;
import com.scen.engface.IStockPool;

/**
 * describe:
 *
 * @author scott dai
 * @date 2018/11/27
 */
public class ModelWorker extends WorkBase {

    IStockPool isp;
    IScrapyWorker worker;

    @Override
    void doCycleTask(String tid) {
        String scode = null;
        synchronized (isp) {
            if (isp.hasnext()) {
                scode = isp.getnext();
            } else {
                more = false;
            }
        }
        if (scode != null) {
            GlobalCounter.READ_SUM.incrementAndGet();
            workOnce(scode, tid);
        }
    }

    @Override
    void closeCycle(String tid) {
        synchronized (BeanListSingleton.DONE_Getdata) {
            BeanListSingleton.DONE_Getdata.decrementAndGet();
            //            if (log_flag) {
            LogPrint("Stop thread: " + Thread.currentThread() + " left: " + BeanListSingleton.DONE_Getdata);
//            } else
//                Log.info("Stop thread: " + Thread.currentThread() + " left: " + BeanListSingleton.DONE_Getdata);
        }


        if (BeanListSingleton.DONE_Getdata.get() == 0) {
            synchronized (isp) {
                if (!isp.isClosed()) {
                    isp.closePool();
                    LogPrint("Stop All Scrapy threads!!!");
                }
            }
            QueueFactory.ScrapyDone();
        }
    }


    private void workOnce(String stock_code, String threadid) {
        if (stock_code != null && stock_code != "") {
            // printMsg(stock_code);
            int i = worker.startToWork(stock_code, threadid);
            synchronized (GlobalCounter.SCRAPY_SUM) {
                GlobalCounter.SCRAPY_SUM.addAndGet(i);
            }
        }
    }

    private void LogPrint(String msg) {
//        if (log_flag) {
//            System.out.println(msg);
//        } else
//            Log.info(msg);
    }
//    private void printMsg(String stock_code) {
//        if (log_flag) {
//            System.out.println(stock_code + " " + Thread.currentThread());
//        } else
//            Log.info(stock_code + " " + Thread.currentThread());
//    }
}
