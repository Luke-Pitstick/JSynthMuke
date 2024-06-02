package com.mycompany.jsynthmuke.synthesizer;

import com.jsyn.unitgen.*;

public class Filter extends FilterHighPass {

    double freq;

    double amp;

    public Filter (double frequency, double amplitude) {
        this.freq = frequency;
        this.amp = amplitude;

        this.frequency.set(this.freq);
        this.amplitude.set(this.amp);
    }

    public Filter () {
        this.freq = 500;
        this.amp = 50;

        this.frequency.set(this.freq);
        this.amplitude.set(this.amp);
    }

}
