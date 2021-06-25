package com.scen.datastr;

import java.util.List;

import com.GlobalCounter;
import com.GlobalTimer;
import com.scen.engface.IControl;
import com.scen.engface.IStockPool;
import com.scen.engface.ITasks;

import java.util.logging.Logger;

public class QueueFactory extends FactoryBase {
    private static final Logger Log = Logger.getLogger(QueueFactory.class.getName());

    public static List<String[]> BeanQueue = BeanListSingleton.getBeanQueue();
    private static List<String[]> closeQueue = IUBeanSingleton.getCLOSEQueue();
    private static List<String[]> updateQueue = IUBeanSingleton.getUPDATEQueue();
    private static List<String[]> insertQueue = IUBeanSingleton.getINSERTQueue();
    private static List<String> CodeQueue = CodeListSingleton.getCodeQueue();

    public static ThreadGroup xxx, yyy;
    public static boolean xxx_stop = false, yyy_stop = false, MORE = false;
    private int LONGEST_RUN_TIME = 3;

    private String TODAY_DATE = GlobalTimer.getToday(), FUNC = "";

    private boolean log_flag = true;
    ITasks task;
    IControl ctl;
    IStockPool isp;

    public QueueFactory() {
        GlobalCounter.iniCounters();
    }

    public QueueFactory(ThreadGroup[] xy, String dt, ITasks t, IControl control, String f) {
        xxx = xy[0];
        yyy = xy[1];
        FUNC = f;
        TODAY_DATE = dt;
        GlobalCounter.iniCounters();
        task = t;
        ctl = control;
        printQueueSize();
        isp = null;
        //Log.info(tmp);
    }

    public QueueFactory(ThreadGroup[] xy, String dt, ITasks t, IControl control, IStockPool i, String f) {
        xxx = xy[0];
        yyy = xy[1];
        FUNC = f;
        TODAY_DATE = dt;
        GlobalCounter.iniCounters();
        task = t;
        ctl = control;
        isp = i;

        printQueueSize();
    }

    public void printQueueSize() {
        Log.info("CodeQueue: " + CodeQueue.size());
        Log.info("BeanQueue: " + BeanQueue.size());
    }

//    public boolean checkScrapyStop() {
//        if (!CodeQueue.isEmpty() ||
//                !BeanQueue.isEmpty()
//                || xxx_stop || yyy_stop) {
//            return true;
//        } else {
//            Log.info("All Closed!!! ");
//            // more = false;
//            return false;
//        }
//    }

    public boolean checkScrapySaveStop(IStockPool isp) {
        if (!CodeQueue.isEmpty() || xxx_stop) {
            Log.info(Integer.toString(CodeQueue.size()));
            return true;
        } else {
            Log.info("All Closed!!! ");
            // more = false;
            return false;
        }
    }

    public boolean countTime() {
        return GlobalTimer.compareCountDateTime(current_time_start, GlobalTimer.getDayTime(), LONGEST_RUN_TIME);
    }
//    {
//        return !MORE && !xxx_stop && !yyy_stop ? this.countTime() : true;
//    }

    @Override
    public boolean checkMonitorStop() {
        if (MORE || xxx_stop || yyy_stop) {
            return true;
        } else {
            //return countTime();
            //// Log.info("All Closed!!! ");
            return false;
        }
    }

    @Override
    public void HeartBeatPrint() {
        if (log_flag) {
            // Log.info();
            // Log.info("BeanQueue Number: " + BeanQueue.size() + ": "
            // + xxx.activeCount() + ":" + yyy.activeCount());
            Log.info("%$#@  (⊙ˍ⊙)  CodeQueue Number: " + CodeQueue.size() +
                    " (BeanQueue: " + BeanQueue.size() +
                    " InsertQ/UpdateQ/CloseQ: " + insertQueue.size() +
                    "/" + updateQueue.size() + "/" + closeQueue.size() +
                    ") Saved: " + GlobalCounter.COUNTER_SAVED_GLOBAL +  //.getCOUNTER_SAVED_GLOBAL() +
                    " xxx:   " + xxx.activeCount() + " " + BeanListSingleton.DONE_Getdata +
                    " yyy:   " + yyy.activeCount() + " " + BeanListSingleton.NUM_SUB +
                    "\n Stop:  " + xxx_stop + " / " + yyy_stop + " / " + MORE);
            Log.info(":" + BeanListSingleton.DONE_Getdata + ":" + BeanListSingleton.getBeanQueue().size() + ":" + BeanListSingleton.NUM_SUB);
//            if (isp != null) {
//                Log.info(isp.isClosed() + ":" + isp.hasnext());
            //  }
        } else {
            /*
             * Log.info("CodeQueue Number: " + CodeQueue.size());
             * Log.info("BeanQueue Number: " + BeanQueue.size());
             */
        }
    }

    public static int getXXXcount() {
        return xxx.activeCount();
    }

    private void PrintReport(String closets) {

        int total_i = 0;
        if (ctl != null)
            total_i = ctl.doControl();
        GlobalCounter.COUNTER_SAVED_CHECK.addAndGet(total_i);

        Log.info("BeanQueue left: " + BeanQueue.size() + "  " + Thread.currentThread());
        Log.info("=================================================== ");
        Log.info("======  " + " : " + TODAY_DATE + " Report  ============= ");
        Log.info("=================================================== ");

        if (log_flag) {

            reportModel(FUNC);
            // Log.info("readed queue : " + golbal_reader);
        } else {
            /*
             * Log.info("***Successful Run for Get Stock List Data!!!---");
             * Log.info("checked : " + checked_count); Log.info("inserted : " +
             * insert_count + "/" + total_i); Log.info("delete : " +
             * delete_count); Log.info("*****======---");
             */
        }
        if (ctl != null)
            ctl.doLastTask();
        ;
        iniParms();
    }

    public void iniParms() {
        GlobalCounter.iniCounters();
        xxx_stop = false;
        yyy_stop = false;
    }

    public Thread startMoniter() {
        MORE = true;
        Thread thread = new Thread(new QueueMoniter());
        thread.start();
        return thread;
    }

    public void stopMonitor() {
        ScrapyDone();
        SaverDone();
        MORE = false;
    }

    public static void ScrapyStart() {
        xxx_stop = true;
    }

    public static void SaverStart() {
        yyy_stop = true;
    }

    public static void ScrapyDone() {
        //ScrapyDone();
        xxx_stop = false;
        Log.info("1 + Done Scrapy: " + xxx_stop + ": " + yyy_stop + ": " + MORE);
        if (!yyy_stop) {
            Log.info("2  +Done Scrapy: " + xxx_stop + ": " + yyy_stop + ": " + MORE);
            MORE = false;
        }
    }

    public static void SaverDone() {
        yyy_stop = false;
        MORE = false;
    }

    @Override
    public void CloseAndPrintDoneInfo(String start_ts, String stop_ts) {

        Log.info("========================================== ");
        Log.info("===  " + stop_ts + " Report ====== Used ** " + GlobalTimer.usedtime(start_ts, stop_ts) + "  ** ======= ");

        PrintReport(stop_ts);

        if (task != null)
            task.TaskNect();
        else {
            //  ConnectionManager.CloseAllConnection();
            Log.info("Stop time: " + GlobalTimer.getTimestamp());
            task.TaskClose();
        }

        // finished();
    }

    public void setLONGEST_RUN_TIME(int time) {
        LONGEST_RUN_TIME = time;
    }
}
