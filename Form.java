import com.jsyn.scope.AudioScope;
import com.jsyn.scope.swing.AudioScopeView;

import javax.swing.*;

public class Form {


    private JFrame frame;
    private Recorder recorder;
    private AudioViz viz;

//    Form(){
//        audioViz = new AudioViz();
//        scopeView = audioViz.scope.getView();
//        recorder = new Recorder();
//        createUIComponents();
//        //recorderButton.addActionListener(this);
//        app = new JFrame();
//        app.setContentPane(panel1);
//        app.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        app.pack();
//        app.setVisible(true);
//    }
//    Form(String title) {
//        this();
//        app.setTitle(title);
//
//    }
    Form(){
        viz = new AudioViz();
//        formDesigner = new FormDesigner(viz.scope.getView());
        frame = new JFrame("FormDesigner");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.scope = viz.scope.getView();
    }

    Form(String title){
        this();
        frame.setTitle(title);
    }

//    @Override
//    public void createUIComponents(){
//    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        recorder.rec(audioViz.synth);
//    }

}
