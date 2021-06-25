package com.scen.engine;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import com.scen.datastr.BeanListSingleton;
import com.GlobalCounter;
import com.scen.datastr.QueueFactory;
import com.scen.engface.IDataSaver;

public class DataSaver implements Runnable {
    public final Logger Log = Logger.getLogger(DataSaver.class.getName());
    static CopyOnWriteArrayList<String[]> BeanQueue = null;
    PreparedStatement ps;
    IDataSaver ids;


    int ONETIMESUB = 100, COUNTER_ONETIME = 0, count_thread = 0;
    private boolean MORE = true;
    String threadid;

    public DataSaver(IDataSaver _ids) {
        ids = _ids;
        ps = ids.getPS();
    }

    public int getSubNum() {
        return ONETIMESUB;
    }

    public void setONETIMESUB(int ONETIMESUB) {
        this.ONETIMESUB = ONETIMESUB;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        threadid = Long.toString(Thread.currentThread().getId() + 700);
        Log.info("Load Thread started!!!");
        BeanQueue = BeanListSingleton.getBeanQueue();

        try {
            while (MORE) {
                //System.out.println("Saver One!!!!===============");
                subdata(ONETIMESUB);
                adddata();
                continueCycle();
                Thread.sleep(10);
            }
            //System.out.println("Cycle Done =       ==========================");
        } catch (Exception e) {
            System.out.println(MORE + ": " + BeanQueue.size());
            e.printStackTrace();
        } finally {
            subdata(0);
            Log.info(Integer.toString(BeanListSingleton.NUM_SUB.get()));
            CloseThisThread();
        }
        Log.info("Load Thread Stop!!!" + Thread.currentThread());
        Log.info("Thread: " + Thread.currentThread().getId() + " handle num:  " + count_thread);
    }

    private void continueCycle() {

        if (BeanListSingleton.DONE_Getdata.get() <= 0 && BeanListSingleton.getBeanQueue().size() <= 0) {
            MORE = false;
        }
        //System.out.println("cycle: " + BeanListSingleton.DONE_Getdata.get() + ": " + BeanListSingleton.getBeanQueue().size() + ":" + MORE);
        // a = (BeanListSingleton.DONE_Getdata > 0 || BeanListSingleton.getBeanQueue().size() > 0);
        // if (a)
        // System.out.println("Have more!!!!=============================" +
        // BeanListSingleton.getDONE_Getdata() + ":" + BeanQueue.size());
        //  return a;
    }

    private void CloseThisThread() {
        try {
            synchronized (ps) {
                if (ps != null)
                    ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BeanListSingleton.NUM_SUB.decrementAndGet();
            System.out.println("Close One SAVER!!!");
        }
        if (BeanListSingleton.NUM_SUB.get() <= 0) {
            Log.info("Stop All DataSave threads!!!");
            Log.info("Finished!!");
            QueueFactory.SaverDone();
        }

    }

    private void subdata(int top) {

        if (COUNTER_ONETIME > top) {
            try {
                ps.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            COUNTER_ONETIME = 0;

            // System.out.println("do insert of " + counter_onetime + " : " +
            // insert_time);
            // System.out.println("Saver Thread: " +
            // Thread.currentThread().getId());
            // System.out.println(" : " +
            // Long.toString(Thread.currentThread().getId()));
            // int[] xx = ps.executeBatch();
            // for (int a = 0; a < xx.length; a++)
            // System.out.println("INSERT RESULT:  " + xx[a]);

            // ps.close();

            // System.out.println("do insert of 222: " + counter_onetime + " : "
            // + insert_time);
            // synchronized (ps) {
            /*
             * if (log_flag) { System.out.println("Sub Once! ==> " +
             * BeanQueue.size() + Thread.currentThread()); } else
             * Log.info("Sub Once! ==> " + BeanQueue.size() +
             * Thread.currentThread());
             */
        }
    }

    private String[] getone() {
        String[] bean_tmp = null;

        synchronized (BeanQueue) {
            if (!BeanQueue.isEmpty()) {
                bean_tmp = (String[]) BeanQueue.remove(0);
                GlobalCounter.GLOBAL_REMOVE.incrementAndGet();
            }
        }

        return bean_tmp;
    }

    private void adddata() {
        String[] bean_tmp = getone();
        // lock1.lock();
//        synchronized (BeanQueue) {
//            //  try {
//            if (!BeanQueue.isEmpty()) {
//                bean_tmp = (String[]) BeanQueue.remove(0);
//                GlobalCounter.GLOBAL_REMOVE++;
//
//            }
////        } finally {
////            lock1.unlock();
////        }
//        }


        if (bean_tmp != null) {
            count_thread++;
            // System.out.println("///" + bean_tmp[1] + bean_tmp[2] +
            // bean_tmp[40] + ": " + Thread.currentThread().getId());
//            System.out.println("///" + bean_tmp[1] + bean_tmp[2] +
//                    bean_tmp[40]);
            // System.out.println("do insert of " + counter_onetime + " : " +
            // bean_tmp[2]);
            int one = ids.PrepRecord(bean_tmp, threadid);
            COUNTER_ONETIME++;
            if (one > 0)
                // synchronized (GlobalCounter.COUNTER_SAVED_GLOBAL) {
                GlobalCounter.COUNTER_SAVED_GLOBAL.incrementAndGet();
            //}
        }
    }

//    private void LogPrint(String msg) {
//        if (log_flag) {
//            System.out.println(msg);
//        } else
//            Log.info(msg);
//    }
}
