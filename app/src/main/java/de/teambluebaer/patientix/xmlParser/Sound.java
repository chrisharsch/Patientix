package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 */
public class Sound implements Element {
    private String soundSource;

    public Sound(String src){
        soundSource = src;
    }

    @Override
    public void addToView() {

    }
}
