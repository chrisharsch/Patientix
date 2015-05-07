package de.teambluebaer.patientix.xmlParser;

/**
 * Created by Simon on 06.05.2015.
 */
public class Image implements Element {
    private String imageSource;

    public Image(String src){
        imageSource = src;
    }

    @Override
    public void addToView() {

    }
}
