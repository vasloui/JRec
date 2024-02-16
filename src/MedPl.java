package src;

import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateDataReader;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;

// This is a class controlling the audio playback and partly
// handling information about the complete-present duration of the loaded audio file, using the PlayingTimer class
public class MedPl {

    private Synthesizer synth;

    public VariableRateDataReader getSamplePlayer() {
        return samplePlayer;
    }

    private VariableRateDataReader samplePlayer;
    private LineOut lineOut;
    private File sampleFile;
    public FloatSample sample;
    private PlayingTimer timer;

    MedPl(Synthesizer synth, LineOut lineOut, VariableRateMonoReader samplePlayer){
        this.synth = synth;
        this.lineOut = lineOut;
        this.samplePlayer = samplePlayer;
        this.timer = new PlayingTimer();
    }

    public void playInit() throws InterruptedException, IOException {

        SampleLoader.setJavaSoundPreferred(false);
        System.out.println(lineOut.getPorts().toString());
        if (sample.getChannelsPerFrame() == 1) {
            synth.add(samplePlayer = new VariableRateStereoReader());
            samplePlayer.output.connect(0, lineOut.input, 0);
        } else if (sample.getChannelsPerFrame() == 2) {
            synth.add(samplePlayer = new VariableRateStereoReader());
            samplePlayer.output.connect(0, lineOut.input, 0);
            samplePlayer.output.connect(1, lineOut.input, 1);
        } else {
            throw new RuntimeException("Can only play mono or stereo samples.");
        }
        samplePlayer.rate.set(sample.getFrameRate());

        lineOut.start();

        if (sample.getSustainBegin() < 0) {
            System.out.println("queue the sample");
            samplePlayer.dataQueue.queue(sample);
        } else {
            System.out.println("queueOn the sample for a short time");
            samplePlayer.dataQueue.queueOn(sample);
            synth.sleepFor(8.0);
            System.out.println("queueOff the sample");
            samplePlayer.dataQueue.queueOff(sample);
        }
    }

    public void playStop() throws InterruptedException {
        synth.sleepFor(0.5);
        lineOut.stop();
    }
    public String openFile() throws IOException {
        JFileChooser fileChooser = new JFileChooser();

        FileFilter wavFilter = new FileFilter() {
            @Override
            public String getDescription() {
                return "Sound file (*.WAV)";
            }

            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    return file.getName().toLowerCase().endsWith(".wav");
                }
            }
        };


        fileChooser.setFileFilter(wavFilter);
        fileChooser.setDialogTitle("Open Audio File");
        fileChooser.setAcceptAllFileFilterUsed(false);

        String fileName = "";
        int userChoice = fileChooser.showOpenDialog(null);
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().getAbsolutePath();
            sampleFile = new File(fileName);
            sample = SampleLoader.loadFloatSample(sampleFile);

            return fileName;
        } else {
            return null;
        }
    }

    public String getDur(){
        return timer.getDuration(sample);
    }
}
