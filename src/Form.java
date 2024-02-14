package src;

import com.jsyn.scope.AudioScope;
import com.jsyn.scope.swing.AudioScopeView;
import com.jsyn.unitgen.LineOut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Form {

    private JFrame frame;
    private Recorder recorder;
    private AudioViz viz;
    private FormDesigner formDesigner;
    private MedPl medPl;
    private Thread th;
    private Runnable runnable;


    Form() {
        viz = new AudioViz();
        medPl = new MedPl(viz.getSynth(), viz.getLineOut(), viz.getSamplePlayer(), viz.getSample());
        frame = new JFrame();

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
                String result = medPl.openFile();
                if (result != null) {
                    playingfName.setText(result);
                }
            }

            @Override
            public void playAction() {
                th = new Thread() {
                    @Override
                    public void run() {
                        medPl.play();
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
