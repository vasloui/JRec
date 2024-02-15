package src;

import com.jsyn.data.FloatSample;
import com.jsyn.ports.UnitDataQueuePort;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PlayingTimer {
    //private long startTime;
    private long pauseTime;
    private DateFormat dateFormater;
//    private JLabel labelRecordTime;
//    private JSlider slider;
//    private UnitDataQueuePort dataQueue;
//    private VariableRateDataReader samplePlayer;
    private boolean isReset=false;
    private boolean isRunning=false;

//    PlayingTimer(JLabel labelRecordTime, JSlider slider, UnitDataQueuePort dataQueue, VariableRateDataReader samplePlayer) {
//        this.labelRecordTime = labelRecordTime;
//        this.slider = slider;
//        this.dataQueue = dataQueue;
//        this.samplePlayer = samplePlayer;
//    }

//    private void doTime(JLabel timeLabel,JSlider slider1, UnitDataQueuePort dataQueue, VariableRateDataReader samplePlayer) {
//
//        isRunning = true;
//        startTime = System.currentTimeMillis();
//
//        while(isRunning) {
//
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            if (dataQueue != null && dataQueue.hasMore()) {
//                timeLabel.setText(toTimeString());
//                int currentSecond = ((int)samplePlayer.dataQueue.getFrameCount())/((int)samplePlayer.getFrameRate());
//                slider1.setValue(currentSecond);
//            }
//            if (isReset) {
//                slider1.setValue(0);
//                timeLabel.setText("00:00:00");
//                isRunning = false;
//                break;
//            }
//
//        }
//    }
    PlayingTimer(){
        dateFormater = new SimpleDateFormat("HH:mm:ss");
    }

    void reset() {
        isReset = true;
        isRunning = false;
    }

    public String setTimeLab(long startTime){
        return toTimeString(startTime);
    }

    public int setSlider(VariableRateDataReader samplePlayer){
        return ((int)samplePlayer.dataQueue.getFrameCount())/((int)samplePlayer.getFrameRate());
    }

    public String getDuration(FloatSample sample){
        int duration = (((int)sample.getNumFrames())/((int)sample.getFrameRate()));
        Date current = new Date(duration*1000);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormater.format(current);
    }
    public String getDuration(int duration){
        Date current = new Date(duration);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormater.format(current);
    }

    private String toTimeString(long startTime) {
        long now = System.currentTimeMillis();
        Date current = new Date(now - startTime);
        dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormater.format(current);
    }
}
