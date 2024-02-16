package src;

import com.jsyn.data.FloatSample;

import com.jsyn.unitgen.VariableRateDataReader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

// For audio time measurements
public class PlayingTimer {
    private long pauseTime;
    private DateFormat dateFormater;
    private boolean isReset=false;
    private boolean isRunning=false;

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
