package src;

import com.jsyn.scope.AudioScope;
import com.jsyn.scope.swing.AudioScopeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form implements ActionListener {


    private JFrame frame;
    private Recorder recorder;
    private AudioViz viz;

//    Form(){
//        viz = new AudioViz();
////        formDesigner = new FormDesigner(viz.scope.getView());
//        frame = new JFrame("FormDesigner");
//        frame.setContentPane(panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//        this.scope = viz.scope.getView();
//    }

    Form() {
        frame = new JFrame();
        // the clickable button
        JButton button = new JButton("Click Me");
        button.addActionListener(this);

        // Mic audio visualization graph
        viz = new AudioViz();

        // Instantiate recorder object
        recorder = new Recorder();

        // the panel with the button and text
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(button);
        panel.add(viz.scope.getView());
        //panel.add(label);

        // set up the frame and display it
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.pack();
        frame.setVisible(true);
    }


    Form(String title){
        this();
        frame.setTitle(title);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        recorder.rec(viz.synth);
        viz = new AudioViz();

    }

//    @Override
//    public void createUIComponents(){
//    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        recorder.rec(audioViz.synth);
//    }

}
