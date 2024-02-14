package src;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.data.FloatSample;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineIn;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.VariableRateMonoReader;

public class AudioViz {
    private Synthesizer synth;
    private LineIn lineIn;
    private LineIn lineIn1;
    private LineOut lineOut;
    private AudioScope scope;
    private VariableRateMonoReader samplePlayer;
    private FloatSample sample;


    AudioViz() {
        synthInit();
        scopeInit();
    }

    private void synthInit() {
        synth = JSyn.createSynthesizer();
        lineIn = new LineIn();
        lineIn1 = new LineIn();
        lineOut = new LineOut();
        synth.add(lineIn);
        synth.add(lineIn1);
        synth.add(lineOut);

        int numInputChannels = 2;
        int numOutputChannels = 2;
        synth.start(44100, AudioDeviceManager.USE_DEFAULT_DEVICE, numInputChannels,
                AudioDeviceManager.USE_DEFAULT_DEVICE, numOutputChannels);
        lineIn.start();
    }

    private void scopeInit() {
        scope = new AudioScope(synth);
        scope.addProbe(lineIn.output);
        scope.start();
        scope.getView().setControlsVisible(true);
    }

    public Synthesizer getSynth() {
        return synth;
    }

    public AudioScope getScope() {
        return scope;
    }

    public LineIn getLineIn1() {
        return lineIn1;
    }

    public LineOut getLineOut(){
        return lineOut;
    }

    public VariableRateMonoReader getSamplePlayer() {
        return samplePlayer;
    }

    public FloatSample getSample() {
        return sample;
    }
}
