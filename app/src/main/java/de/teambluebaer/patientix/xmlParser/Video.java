package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

/**
 * Created by Simon on 06.05.2015.
 * Represents a Video File that could insert into a <code>Row</code>
 *
 * @see Row
 * @see Element
 */
public class Video implements Element {
    private String videoSource;

    /**
     * Constructor
     *
     * @param src URL-String represents Location of the Video you want to add
     */
    public Video(String src) {
        videoSource = src;
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {

        RelativeLayout rl = new RelativeLayout(context);
        rl.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));


        //Uri myUri = Uri.parse(videoSource);
        VideoView video = new VideoView(context);
        // video.setVideoURI(myUri);
        video.setLayoutParams(new FrameLayout.LayoutParams(550, 550));
        video.setVideoPath(
                videoSource);
        MediaController mediaController = new
                MediaController(context);
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);
        video.start();
        rl.addView(video);
        layout.addView(rl);


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
}