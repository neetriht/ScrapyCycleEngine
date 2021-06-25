package com.scen.datastr;

import com.GlobalConnectStatus;
import com.GlobalCounter;
import com.GlobalEnv;
import com.GlobalTimer;
import com.files.FileRecorder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * describe:
 *
 * @author scott dai
 * @date 2018/11/27
 */
public abstract class FactoryBase {


    private long SLEEP_TIME = 5000;
    public String current_time_start, current_time_stop;

    public abstract boolean checkMonitorStop(

    );

    public abstract void HeartBeatPrint();

    public abstract void CloseAndPrintDoneInfo(String start_ts, String stop_ts);

    private Date start_time, current_time;

    public class QueueMoniter implements Runnable {

        public void run() {
            current_time_start = GlobalTimer.getDayTime(); //.datetimeformat.format(new Date());
            System.out.println("Start to get new stocks!!!==" + GlobalTimer.getTimestamp());

            try {
                while (checkMonitorStop()) {
                    HeartBeatPrint();
                    Thread.sleep(SLEEP_TIME);
                }
                HeartBeatPrint();
                current_time_stop = GlobalTimer.getDayTime();

                CloseAndPrintDoneInfo(current_time_start, current_time_stop);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void reportModel(String func) {
        String common01 = "*************************************************** ";
        String txt01 = "*** Successful Run for Get Stock List Data!!!---";
        String txt02 = "*** checked       : " + GlobalCounter.READ_SUM;
        String txt03 = "*** missed        : " + GlobalCounter.GLOBAL_MISSED;
        String txt04 = "*** inserted      : " + GlobalCounter.COUNTER_SAVED_GLOBAL + " / " + GlobalCounter.COUNTER_SAVED_CHECK;
        String txt05 = "*** Fresh i/u     : " + GlobalCounter.Fresher_Insert_No + " / " + GlobalCounter.Fresher_Update_No;
        String txt06 = "*** remove queue  : " + GlobalCounter.GLOBAL_REMOVE;
        String txt07 = "*-------------------------------------------------- ";
        String txt08 = "*** HBASE CHECK   : " + GlobalCounter.HBASE_CHECK;
        String txt09 = "*** HBASE ADD     : " + GlobalCounter.HBASE_ADD;
        String txt10 = "*-------------------------------------------------- ";

        String txt11 = "*** Redis ADD     : " + GlobalCounter.REDIS_ADD;
        String txt12 = "*-------------------------------------------------- ";
        String txt13 = "*** MongoDB ADD   : " + GlobalCounter.Mongo_ADD;
        String txt14 = "*** MongoDB CHECK : " + GlobalCounter.Mongo_CHECK;
        String txt15 = "*************************************************** ";
        List<String> informationvalues = new ArrayList<String>();
        informationvalues.add(txt01);
        informationvalues.add(txt02);
        informationvalues.add(txt03);
        informationvalues.add(txt04);
        informationvalues.add(txt05);
        informationvalues.add(txt06);
        informationvalues.add(txt07);
        informationvalues.add(txt08);
        informationvalues.add(txt09);
        informationvalues.add(txt10);
        informationvalues.add(txt11);
        informationvalues.add(txt12);
        informationvalues.add(txt13);
        informationvalues.add(txt14);
        informationvalues.add(txt15);
        for (String a : informationvalues)
            System.out.println(a);

        if (func.length() > 0)
            saveReport(informationvalues, func);


    }

    public void saveReport(List<String> values, String filenameparm) {
        String report_path = "";
        if (GlobalEnv.isLinux())
            report_path = "/home/hadoop/scott/9_batch/report/";
        else
            report_path = "C:\\scott\\5_Stock\\report\\";
        String reportname = filenameparm + "_D" + GlobalTimer.getShortToday();
        try {
            new FileRecorder().outputreport(values, report_path + reportname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //new FileRecorder();

    }

    public void setSLEEP_TIME(long SLEEP_TIME) {
        this.SLEEP_TIME = SLEEP_TIME;
    }

}
