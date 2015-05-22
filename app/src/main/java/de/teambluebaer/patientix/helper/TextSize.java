package de.teambluebaer.patientix.helper;

/**
 * Created by Simon on 22.05.2015.
 */
public enum TextSize {
    TITEL(70,90),SUBTITEL(50,70),TEXT(40,50);

    public final float normalSize;
    public final float zoomedSize;

    private TextSize(float normSize,float zoomSize){
        normalSize = normSize;
        zoomedSize = zoomSize;
    }
}
