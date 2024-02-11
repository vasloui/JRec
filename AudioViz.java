import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineIn;

public class AudioViz {
    public Synthesizer synth;
    private LineIn lineIn;
    public AudioScope scope;


    AudioViz(){
        synthInit();
        scopeInit();

    }

    private void synthInit(){
        synth = JSyn.createSynthesizer();
        lineIn = new LineIn();
        synth.add(lineIn);

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
}
