package src;

import com.jsyn.scope.swing.AudioScopeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class FormDesigner {
    public JPanel panel1;
    private JButton recorderButton;

    public JSlider slider1;
    private JButton stopButton;
    private JButton openButton;
    private JButton playButton;
    public AudioScopeView audioView;
    private JLabel playinfLabel;
    public JLabel playingfName;
    public JLabel timeLabel;

    public JLabel duration;
    public FormDesigner formDesigner;

    public FormDesigner() {
        formDesigner =this;
        slider1.setValue(0);

        recorderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recAction();
            }
        });
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAction();
            }
        });
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playAction();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopAction();
            }
        });
    }

    public void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public abstract void recAction();
    protected abstract void openAction();
    protected abstract void playAction();
    protected abstract void stopAction();
}
