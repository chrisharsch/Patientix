package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import de.teambluebaer.patientix.helper.TextSize;

/**
 * Created by Simon on 06.05.2015.
 * Represents a Video File that could insert into a <code>Row</code>
 *
 * @see Row
 * @see Element
 */
public class Video implements Element {
    private String videoSource;
    private int counter;

    /**
     * Constructor
     *
     * @param src URL-String represents Location of the Video you want to add
     */
    public Video(String src) {
        counter = 10;
        if(src != null && !src.isEmpty()){
            videoSource = src;
        }else{
            videoSource = "";
        }
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {

        if(!videoSource.isEmpty()) {
            try{
                RelativeLayout rl = new RelativeLayout(context);
                rl.setLayoutParams(new RelativeLayout.LayoutParams(1980, 1000));
                VideoView video = new VideoView(context);
                rl.setGravity(Gravity.CENTER);
                FrameLayout.LayoutParams layoutParameter = new FrameLayout.LayoutParams(1980, 1000);
                layoutParameter.gravity = Gravity.CENTER;
                video.setLayoutParams(layoutParameter);
                video.setVideoPath(videoSource);
                MediaController mediaController = new MediaController(video.getContext());
                mediaController.setAnchorView(video);
                mediaController.setMediaPlayer(video);
                video.requestFocus();
                video.setMediaController(mediaController);
                video.start();
                rl.addView(video);
                layout.addView(rl);
            }catch (Exception e){
                TextView textView = new TextView(context);
                textView.setText("Das Video konnte nicht geladen werden, bitte drücken Sie einmal Zurück ond Weiter um diese Seite erneut zu laden");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);

                layout.addView(textView);
            }

        }
    }

    public String toXMLString() {
        String xmlString = new String();
        xmlString = xmlString + "<video ";
        xmlString = xmlString + "src=\"" + this.videoSource + "\" ";
        xmlString = xmlString + "/>";

        return xmlString;
    }

    public String getVideoSource() {
        return videoSource;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Video video = (Video) o;

        return !(videoSource != null ? !videoSource.equals(video.videoSource) : video.videoSource != null);

    }

    @Override
    public int hashCode() {
        return videoSource != null ? videoSource.hashCode() : 0;
    }

    @Override
    public int getCounter() {
        return counter;
    }
}