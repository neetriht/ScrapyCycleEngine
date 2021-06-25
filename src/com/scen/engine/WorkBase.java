package com.scen.engine;

/**
 * describe:
 *
 * @author scott dai
 * @date 2018/11/27
 */
public abstract class WorkBase implements Runnable {

    public static boolean more = true;

    private String threadid;

    //@ need to set more whether have more data
    abstract void doCycleTask(String tid);

    abstract void closeCycle(String tid);

    @Override
    public void run() {
        // TODO Auto-generated method stub

        threadid = Long.toString(Thread.currentThread().getId() + 500);
        System.out.println("This scripy thread id is: " + threadid);
        try {
            while (more) {
                doCycleTask(threadid);
                Thread.sleep(10L);
            }
            closeCycle(threadid);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
