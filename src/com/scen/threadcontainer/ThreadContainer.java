package com.scen.threadcontainer;

import java.util.logging.Logger;

import com.scen.datastr.BeanListSingleton;
import com.scen.datastr.QueueFactory;
import com.scen.engface.IDataSaver;
import com.scen.engface.IScrapyWorker;
import com.scen.engface.IStockPool;
import com.scen.engine.DataSaver;
import com.scen.engine.ScrapyWorker;

public class ThreadContainer {
    public static final Logger Log = Logger.getLogger(ThreadContainer.class.getName());
    IScrapyWorker isw;
    IDataSaver ids;
    static IStockPool isp;
    //String Today;
    ThreadGroup xxx = null, yyy = null;

    //public static final Logger Log = Logger.getLogger(RedisThreadContainer.class.getName());

    public ThreadContainer(ThreadGroup[] xy, IScrapyWorker iswer, IDataSaver idser, IStockPool ispool, String td) {
        isw = iswer;
        ids = idser;
        isp = ispool;
        xxx = xy[0];
        yyy = xy[1];
        // Today = td;
    }

//    public RedisThreadContainer(ThreadGroup xy, IScrapyWorker iswer, IStockPool ispool, String td) {
//        isw = iswer;
//        isp = ispool;
//        xxx = xy;
//        Today = td;
//    }

    public void startThreads(int get_thread, int save_thread) {
        if (xxx != null && get_thread > 0) {
            QueueFactory.xxx_stop = true;
            //ScrapyWorker.more = true;
            startAllGetStockThread(get_thread);
        }
        if (yyy != null && save_thread > 0) {
            QueueFactory.yyy_stop = true;
            //DataSaver.more = true;
            startAllSaveStockThread(save_thread);
        }
    }

    public void startThreadsNotPara(int get_thread, int save_thread) {
        if (xxx != null)
            startAllGetStockThread(get_thread);
        try {
            while (BeanListSingleton.DONE_Getdata.get() != 0) {
                Thread.currentThread().sleep(3000);
                if (yyy != null)
                    startAllSaveStockThread(save_thread);
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void startAllGetStockThread(int get_thread_max) {
        for (int threadnum = 0; threadnum < get_thread_max; threadnum++) {
            //Thread thread = new Thread();
            new Thread(xxx, new ScrapyWorker(isp, isw.newInstance())).start();
            BeanListSingleton.DONE_Getdata.incrementAndGet();
            Log(threadnum + 1, "scrapy");
            //Log.info(Integer.toString(BeanListSingleton.DONE_Getdata) + " " + Integer.toString(BeanListSingleton.getBeanQueue().size()));
        }
    }

    private void startAllSaveStockThread(int save_thread_max) {

        for (int threadnum = 0; threadnum < save_thread_max; threadnum++) {
            new Thread(yyy, new DataSaver(ids.newInstance())).start();
            BeanListSingleton.NUM_SUB.incrementAndGet();
            this.Log(threadnum + 1, "saver");
            // Log.info(Integer.toString(BeanListSingleton.DONE_Getdata) + " " + Integer.toString(BeanListSingleton.getBeanQueue().size()));
        }
    }

    private void Log(int num, String desc) {
        Log.info("Start " + num + " thread about " + desc + ": " + " Total:" + BeanListSingleton.DONE_Getdata);
    }
}
