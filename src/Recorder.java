package src;

import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineIn;
import com.jsyn.util.WaveRecorder;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class Recorder {

    private Synthesizer synth;
    private LineIn lineIn;
    private WaveRecorder recorder;
    boolean ready;

    Recorder(Synthesizer synth, LineIn lineIn){
        this.synth = synth;
        this.lineIn = lineIn;
    }

    public void rec() throws IOException {
        ready = false;
        recorder = null;
        int response = JOptionPane.showConfirmDialog(null, "Start recording?");
        if (Objects.equals(response, 0)) {
            ready = true;
        } else {
            return;
        }
        startRec(ready, synth);
        JOptionPane.showMessageDialog(null, "Click \"Ok\" to stop recording");
        stopRec();
    }


    private void startRec(boolean ready, Synthesizer synth) throws FileNotFoundException {
        File waveFile = new File("temp_recording.wav");
        recorder = new WaveRecorder(synth, waveFile);
        lineIn.output.connect(0, recorder.getInput(), 0);
        lineIn.output.connect(0, recorder.getInput(), 1);
        lineIn.start();
        recorder.start();
        // Default is stereo, 16 bits.
        System.out.println("Writing to WAV file " + waveFile.getAbsolutePath());
    }


    private void stopRec() throws IOException {
        if (Objects.equals(recorder, null)) {
            return;
        }
        recorder.stop();
        recorder.close();
        lineIn.stop();
    }


}
