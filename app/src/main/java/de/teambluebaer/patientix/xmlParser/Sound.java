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

import de.teambluebaer.patientix.R;
import de.teambluebaer.patientix.helper.TextSize;

/**
 * Created by Simon on 06.05.2015.
 *
 * Represents a Sound File that could insert into a <code>Row</code>
 * @see Row
 * @see Element
 */
public class Sound implements Element {
    private String soundSource;
    private int counter;

    /**
     * Constructor
     * @param src URL-String represents Location of the Sound you want to add
     */
    public Sound(String src){
        counter = 10;
        if(src != null && !src.isEmpty()){
            soundSource = src;
        }else{

            soundSource = "";
        }
    }

    @Override
    public void addToView(Context context, LinearLayout layout) {

        if(!soundSource.isEmpty()) {
            try {
                RelativeLayout rl = new RelativeLayout(context);
                rl.setLayoutParams(new RelativeLayout.LayoutParams(500, 500));
                VideoView sound = new VideoView(context);
                rl.setGravity(Gravity.CENTER);
                FrameLayout.LayoutParams layoutParameter = new FrameLayout.LayoutParams(500, 500);
                layoutParameter.gravity = Gravity.CENTER;
                sound.setLayoutParams(layoutParameter);
                sound.setVideoPath(soundSource);
                sound.setBackgroundResource(R.drawable.sound);
                MediaController mediaController = new MediaController(sound.getContext());
                mediaController.setAnchorView(sound);
                mediaController.setMediaPlayer(sound);
                sound.requestFocus();
                sound.setMediaController(mediaController);
                sound.start();
                rl.addView(sound);
                layout.addView(rl);
            }catch (Exception e){
                TextView textView = new TextView(context);
                textView.setText("Der Sound  konnte nicht geladen werden, bitte drücken Sie einmal Zurück ond Weiter um diese Seite erneut zu laden");
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, TextSize.SUBTITEL.zoomedSize);

                layout.addView(textView);
            }
        }
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

    @Override
    public int getCounter() {
        return counter;
    }
}