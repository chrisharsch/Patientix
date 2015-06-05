package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import de.teambluebaer.patientix.R;

/**
 * Created by Simon on 06.05.2015.
 *
 * Represents a Sound File that could insert into a <code>Row</code>
 * @see Row
 * @see Element
 */
public class Sound implements Element {
    private String soundSource;

    /**
     * Constructor
     * @param src URL-String represents Location of the Sound you want to add
     */
    public Sound(String src){
        soundSource = src;
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {
        RelativeLayout rl = new RelativeLayout(context);
        RelativeLayout.LayoutParams layoutParametersOne = new RelativeLayout.LayoutParams(500, 500);
        layoutParametersOne.addRule(RelativeLayout.CENTER_HORIZONTAL);
        rl.setLayoutParams(layoutParametersOne);
        rl.setGravity(Gravity.CENTER);

        VideoView video = new VideoView(context);
        FrameLayout.LayoutParams layoutParameter = new FrameLayout.LayoutParams(500, 500);

        layoutParameter.gravity = Gravity.CENTER;
        video.setBackgroundResource(R.drawable.sound);
        video.setLayoutParams(layoutParameter);
        video.setVideoPath(soundSource);
        MediaController mediaController =  new MediaController(context);
        mediaController.setAnchorView(video);
        video.requestFocus();
        video.setMediaController(mediaController);
        video.start();

        rl.addView(video);
        layout.addView(rl);
    }

    public String getSoundSource() {
        return soundSource;
    }

    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<audio ";
        xmlString = xmlString + "src=\"" + this.soundSource + "\" ";
        xmlString = xmlString + "/>";

        return xmlString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sound sound = (Sound) o;

        return !(soundSource != null ? !soundSource.equals(sound.soundSource) : sound.soundSource != null);

    }

    @Override
    public int hashCode() {
        return soundSource != null ? soundSource.hashCode() : 0;
    }
}