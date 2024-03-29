package src;

import com.jsyn.unitgen.VariableRateDataReader;

import javax.swing.*;
import java.io.IOException;

public class Form {

    private JFrame frame;
    private Recorder recorder;
    private AudioViz viz;
    private FormDesigner formDesigner;
    private MedPl medPl;
    private Thread th;
    private Runnable runnable;
    private PlayingTimer timer;
    private long startTime;
    private VariableRateDataReader samplePlayer;

    // Initializing the app UI, implementing tha abstract methods for event handling.
    Form() {
        viz = new AudioViz();
        medPl = new MedPl(viz.getSynth(), viz.getLineOut(), viz.getSamplePlayer());
        frame = new JFrame();
        timer = new PlayingTimer();

        formDesigner = new FormDesigner() {
            @Override
            public void createUIComponents() {
                audioView = viz.getScope().getView();
            }

            @Override
            public void recAction() {
                tryOnRec();
            }

            @Override
            public void openAction() {
                String result = null;
                try {
                    result = medPl.openFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (result != null) {
                    playingfName.setText(result);
                    duration.setText(medPl.getDur());
                    slider1.setMaximum(((int)medPl.sample.getNumFrames())/((int)medPl.sample.getFrameRate()));

                }
            }

            @Override
            public void playAction() {
                th = new Thread() {
                    @Override
                    public void run() {

                        try {
                            medPl.playInit();
                            samplePlayer = medPl.getSamplePlayer();
                            startTime = System.currentTimeMillis();

                            do {
                                viz.synth.sleepFor(1.0);
                                timeLabel.setText(timer.setTimeLab(startTime));
                                slider1.setValue(timer.setSlider(samplePlayer));

                            } while (medPl.getSamplePlayer().dataQueue.hasMore());
                            medPl.playStop();
                            timeLabel.setText("00:00:00");
                            slider1.setValue(0);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                th.start();
            }

            @Override
            public void stopAction() {
                medPl.getSamplePlayer().dataQueue.clear();
                viz.getLineOut().stop();
            }
        };
        frame.setContentPane(formDesigner.panel1);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Form(String title) {
        this();
        frame.setTitle(title);
    }

    private void OnRec() throws IOException {
        recorder = new Recorder(viz.getSynth(), viz.getLineIn1());
        recorder.rec();
    }

    private void tryOnRec() {
        try {
            OnRec();
        } catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

}
