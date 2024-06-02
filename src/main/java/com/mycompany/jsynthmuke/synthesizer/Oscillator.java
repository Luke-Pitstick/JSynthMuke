package com.mycompany.jsynthmuke.synthesizer;

import com.jsyn.unitgen.*;

public class Oscillator extends UnitOscillator {
    private WaveType type;

    public Oscillator(WaveType type) {
        this.type = type;
    }

    @Override
    public void generate(int start, int limit) {
        if (type == WaveType.SAWTOOTH) {
            generateSawtooth(start, limit);
        } else if (type == WaveType.SQUARE) {
            generateSquare(start, limit);
        }
    }


    private void generateSawtooth(int start, int limit) {
        double[] frequencies = frequency.getValues();
        double[] amplitudes = amplitude.getValues();
        double[] outputs = output.getValues();

        // Variables have a single value.
        double currentPhase = phase.getValue();

        for (int i = start; i < limit; i++) {
            /* Generate sawtooth phasor to provide phase for sine generation. */
            double phaseIncrement = convertFrequencyToPhaseIncrement(frequencies[i]);
            currentPhase = incrementWrapPhase(currentPhase, phaseIncrement);
            outputs[i] = currentPhase * amplitudes[i];
        }

        // Value needs to be saved for next time.
        phase.setValue(currentPhase);
    }

    private void generateSquare(int start, int limit) {
        double[] frequencies = frequency.getValues();
        double[] amplitudes = amplitude.getValues();
        double[] outputs = output.getValues();

        // Variables have a single value.
        double currentPhase = phase.getValue();

        for (int i = start; i < limit; i++) {
            /* Generate sawtooth phasor to provide phase for square generation. */
            double phaseIncrement = convertFrequencyToPhaseIncrement(frequencies[i]);
            currentPhase = incrementWrapPhase(currentPhase, phaseIncrement);

            double ampl = amplitudes[i];
            // Either full negative or positive amplitude.
            outputs[i] = (currentPhase < 0.0) ? -ampl : ampl;
        }

        // Value needs to be saved for next time.
        phase.setValue(currentPhase);
    }

    public void setType(WaveType type) {
        this.type = type;
    }
}