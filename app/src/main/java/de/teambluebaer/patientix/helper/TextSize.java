package de.teambluebaer.patientix.helper;

/**
 * Created by Simon on 22.05.2015.
 */
public enum TextSize {
    TITEL(80,100),SUBTITEL(65,85),TEXT(55,75);

    public final float normalSize;
    public final float zoomedSize;

    private TextSize(float normSize,float zoomSize){
        normalSize = normSize;
        zoomedSize = zoomSize;
    }
}
