package com.mycompany.jsynthmuke.synthesizer;

import com.jsyn.*;
import com.jsyn.unitgen.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MukeSynthesizer {
    public Synthesizer synth;
    public Oscillator oscillator;
    public LinearRamp lag;
    public Filter filter;
    public LineOut lineOut;
    public int attack;
    public int decay;
    public double sustain;
    public int release;
    public double defaultAmp;
    public boolean noteOn;
    public boolean startComplete;

    public MukeSynthesizer() {
        // initializes synth and the chain of components
        synth = JSyn.createSynthesizer();

        // creates Oscillator. Adds to chain
        oscillator = new Oscillator(WaveType.SAWTOOTH);
        synth.add(oscillator);

        // creates a LinearRamp to smooth out amplitude changes and avoid pops. Adds to chain
        //lag = new LinearRamp();
        //synth.add(lag);

        // Add the High Pass Filter. Adds to chain.
        filter = new Filter();
        synth.add(filter);

        // Add an output mixer. Adds to chain
        lineOut = new LineOut();
        synth.add(lineOut);

        // Set the minimum, current and maximum values for the port.
        //lag.output.connect(oscillator.amplitude);
        //lag.input.setup(0.0, 0.5, 1.0);
        //lag.time.set(0.2);
        
        oscillator.output.connect(filter.input);
        
        filter.output.connect(0, lineOut.input, 0);
        filter.output.connect(0, lineOut.input, 1);

        synth.start();
        oscillator.frequency.set(0);
        
        noteOn = false;
        startComplete = false;
    }

    // plays the freq for certain amount of time
    public void playNoteForTime(double freq, int time) {
        oscillator.frequency.set(freq);

        lineOut.start();

        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lineOut.stop();

    }

    public void startNote(double freq) {
        if (startComplete) {
            return;
        }
        if (!noteOn) {
            stopNote();
            return;
        }
        oscillator.amplitude.set(0);
        oscillator.frequency.set(freq);
        lineOut.start();
        
        if (attack > 0) {
            for (int i = 0; i < 100; i++) {
                double currentAmp = oscillator.amplitude.get();

                try {
                    Thread.sleep(attack / 100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MukeSynthesizer.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (noteOn) {
                    oscillator.amplitude.set(currentAmp + defaultAmp / 100);
                } else {
                    stopNote();
                    return;
                }
            }
        }
        
        if (decay > 0) {
            for (int i = 0; i < 100; i++) {
                double currentAmp = oscillator.amplitude.get();

                try {
                    Thread.sleep(decay / 100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MukeSynthesizer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (noteOn) {
                    oscillator.amplitude.set(currentAmp - (defaultAmp * (sustain / 100)) / 100);
                } else {
                    stopNote();
                    return;
                }
            }
        }
        
        startComplete = true;
    }

    public void stopNote() {
        double startingAmp = oscillator.amplitude.get();
        if (release > 0) {
            for (int i = 0; i < 100; i++) {
                double currentAmp = oscillator.amplitude.get();
                try {
                    Thread.sleep(release / 100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MukeSynthesizer.class.getName()).log(Level.SEVERE, null, ex);
                }
                oscillator.amplitude.set(currentAmp - startingAmp / 100);
            }
        }
        oscillator.frequency.set(0);
        oscillator.amplitude.set(defaultAmp);
        lineOut.stop();
        startComplete = false;
    }
    
    public Synthesizer getSynth() {
        return synth;
    }
    
    public void setDefaultAmp(double defaultAmp) {
        this.defaultAmp = defaultAmp;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDecay(int decay) {
        this.decay = decay;
    }

    public void setSustain(double sustain) {
        this.sustain = sustain;
    }

    public void setRelease(int release) {
        this.release = release;
    }
    
    public void setNoteOn(boolean noteOn) {
        this.noteOn = noteOn;
    }
}