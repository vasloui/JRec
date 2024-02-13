package src;

import com.jsyn.scope.AudioScope;
import com.jsyn.scope.swing.AudioScopeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form {


    private JFrame frame;
    private Recorder recorder;
    private AudioViz viz;
    private FormDesigner formDesigner;
//    Form() {
//        frame = new JFrame();
//        // the clickable button
//        JButton button = new JButton("Click Me");
//
//        // Mic audio visualization graph
//        viz = new AudioViz();
//
//        // Instantiate recorder object
//        recorder = new Recorder();
//
//        // the panel with the button and text
//        JPanel panel = new JPanel();
//        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
//        panel.setLayout(new GridLayout(0, 1));
//        panel.add(button);
//        panel.add(viz.scope.getView());
//        //panel.add(label);
//
//        // set up the frame and display it
//        frame.add(panel, BorderLayout.CENTER);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setTitle("GUI");
//        frame.pack();
//        frame.setVisible(true);
//    }

    Form(){

        viz = new AudioViz();
        frame = new JFrame();
        formDesigner = new FormDesigner(){
            @Override
            public void createUIComponents() {
                audioView = viz.scope.getView();
            }
        };
        frame.setContentPane(formDesigner.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Form(String title){
        this();
        frame.setTitle(title);
    }

}
