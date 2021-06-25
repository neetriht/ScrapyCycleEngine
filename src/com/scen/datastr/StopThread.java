/**
 * Created by neetriht on 2017-05-20.
 */

package com.scen.datastr;

import com.scen.engine.DataSaver;
import com.scen.engine.ScrapyWorker;


public class StopThread {

    public StopThread() {
//        ThreadGroup xx = qf.xxx;
//        ThreadGroup yy = qf.yyy;
//        Thread[] threads = new Thread[xx.activeCount()];

        //ScrapyWorker.more = false;
        QueueFactory.xxx_stop = false;
        System.out.println("####S01#### Stopped All XXX threads");

        //DataSaver.more = false;
        QueueFactory.yyy_stop = false;
        System.out.println("####S02#### Stopped All YYY threads");

        QueueFactory.MORE = false;
        System.out.println("####S03#### Stopped Monitor");
    }
}
