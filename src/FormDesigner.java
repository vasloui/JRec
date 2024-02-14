package src;

import com.jsyn.scope.swing.AudioScopeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class FormDesigner {
    public JPanel panel1;
    private JButton recorderButton;
    private JSlider slider1;
    private JButton stopButton;
    private JButton openButton;
    private JButton playButton;
    public AudioScopeView audioView;
    private JLabel playinfLabel;
    public JLabel playingfName;

    public FormDesigner() {
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
