package src;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineIn;

public class AudioViz {
    private Synthesizer synth;
    private LineIn lineIn;
    private LineIn lineIn1;
    private AudioScope scope;


    AudioViz(){
        synthInit();
        scopeInit();

    }

    private void synthInit(){
        synth = JSyn.createSynthesizer();
        lineIn = new LineIn();
        lineIn1 = new LineIn();
        synth.add(lineIn);
        synth.add(lineIn1);

        int numInputChannels = 2;
        int numOutputChannels = 2;
        synth.start(44100, AudioDeviceManager.USE_DEFAULT_DEVICE, numInputChannels,
                AudioDeviceManager.USE_DEFAULT_DEVICE, numOutputChannels);
        lineIn.start();
    }

    private void scopeInit(){
        scope = new AudioScope(synth);
        scope.addProbe(lineIn.output);
        scope.start();
        scope.getView().setControlsVisible(true);
    }

    public Synthesizer getSynth(){
        return synth;
    }

    public AudioScope getScope(){
        return scope;
    }

    public LineIn getLineIn1(){
        return lineIn1;
    }
}
