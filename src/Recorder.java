package src;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineIn;
import com.jsyn.util.WaveRecorder;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class Recorder {

    private WaveRecorder recorder;
    boolean ready;

    public void rec(Synthesizer synth) {
        ready = false;
        recorder = null;
        try {

            int response = JOptionPane.showConfirmDialog(null, "Start recording?");
            if (Objects.equals(response, 0)){
                ready = true;
            } else {return;}
            startRec(ready, synth);
            JOptionPane.showMessageDialog(null, "Click \"Ok\" to stop recording");
            stopRec();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void startRec(boolean ready, Synthesizer synth) {
        if (!ready) {
            return;
        }
        synth.setRealTime(false);

        File waveFile = new File("temp_recording.wav");
        // Default is stereo, 16 bits.
        try {
            recorder = new WaveRecorder(synth, waveFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Writing to WAV file " + waveFile.getAbsolutePath());

    }

    private void stopRec() throws IOException {
        if (Objects.equals(recorder, null)) {
            return;
        }
        recorder.stop();
        recorder.close();
    }

}
