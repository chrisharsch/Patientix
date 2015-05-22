package de.teambluebaer.patientix.helper;

/**
 * Created by Simon on 22.05.2015.
 */
public enum TextSize {
    TITEL(95,115),SUBTITEL(80,100),TEXT(65,85);

    public final float normalSize;
    public final float zoomedSize;

    private TextSize(float normSize,float zoomSize){
        normalSize = normSize;
        zoomedSize = zoomSize;
    }
}
