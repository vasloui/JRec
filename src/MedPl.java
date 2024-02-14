package src;

import com.jsyn.JSyn;
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

public class MedPl {

    private Synthesizer synth;
    private VariableRateDataReader samplePlayer;
    private LineOut lineOut;
    private File sampleFile;
    private FloatSample sample;

    MedPl(Synthesizer synth, LineOut lineOut, VariableRateMonoReader samplePlayer, FloatSample sample){
        this.synth = synth;
        this.lineOut = lineOut;
        this.samplePlayer = samplePlayer;
    }

    public void play() {

        //synth = JSyn.createSynthesizer();


        try {
            // Add an output mixer.
            //synth.add(lineOut);

            // Load the sample and display its properties.
            SampleLoader.setJavaSoundPreferred(false);
            sample = SampleLoader.loadFloatSample(sampleFile);
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

            // Start synthesizer using default stereo output at 44100 Hz.
            //synth.start();

            samplePlayer.rate.set(sample.getFrameRate());

            // We only need to start the LineOut. It will pull data from the
            // sample player.
            lineOut.start();

            // We can simply queue the entire file.
            // Or if it has a loop we can play the loop for a while.
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

            // Wait until the sample has finished playing.
            do {
                synth.sleepFor(1.0);
            } while (samplePlayer.dataQueue.hasMore());

            synth.sleepFor(0.5);

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Stop everything.
        lineOut.stop();
    }

    public void openFile() {
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

        int userChoice = fileChooser.showOpenDialog(null);
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            sampleFile = new File(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

}
