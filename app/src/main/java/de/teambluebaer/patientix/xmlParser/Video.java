package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 */
public class Video implements Element {
    private String videoSource;

    public Video(String src){
        videoSource = src;
    }

    @Override
    public void addToView() {

    }

}
