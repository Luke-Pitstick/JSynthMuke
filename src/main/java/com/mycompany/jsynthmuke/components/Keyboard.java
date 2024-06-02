package com.mycompany.jsynthmuke.components;

import java.util.HashMap;

public class Keyboard {
    
    /*takes inputted key on keyboard (octave and note name), outputs the frequency that the key 
    corresponds with as a double
    */
    
    private HashMap<String, String> keys;
    private HashMap notes;
    private double frequency;
    
    //builds a hashmap: key is a string (note) and frequency is unlocked
    public Keyboard (){
        notes = new HashMap();
        notes.put("cN", 32.703);
        notes.put("dF", 34.648);
        notes.put("dN", 36.708);
        notes.put("eF", 38.891);
        notes.put("eN", 41.203);
        notes.put("fN", 43.654);
        notes.put("gF", 46.249);
        notes.put("gN", 48.999);
        notes.put("aF", 51.913);
        notes.put("aN", 55.000);
        notes.put("bF", 58.270);
        notes.put("bN", 61.735);
        
        keys = new HashMap<String, String>() {{
            put("z", "cN1");
            put("s", "dF1");
            put("x", "dN1");
            put("d", "eF1");
            put("c", "eN1");
            put("v", "fN1");
            put("g", "gF1");
            put("b", "gN1");
            put("h", "aF1");
            put("n", "aN1");
            put("j", "bF1");
            put("m", "bN1");
            put("q", "cN2");
            put("2", "dF2");
            put("w", "dN2");
            put("3", "eF2");
            put("e", "eN2");
            put("r", "fN2");
            put("5", "gF2");
            put("t", "gN2");
            put("6", "aF2");
            put("y", "aN2");
            put("7", "bF2");
            put("u", "bN2");
            put("i", "cN3");
            put("9", "dF3");
            put("o", "dN3");
            put("0", "eF3");
            put("p", "eN3");
        }};
        
    }
    
    public Double getFrequency(String note, int octave) {
        frequency = (Double)notes.get(note) * Math.pow(2, octave - 1);
        return frequency;
    }
    
    public String getNoteName(String key) {
        return keys.get(key);
    }
}