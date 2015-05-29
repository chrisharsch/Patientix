package de.teambluebaer.patientix.xmlParser;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * Created by Simon on 07.05.2015.
 Represents a WhiteSpace that could insert into a <code>Row</code>
 * @see Row
 * @see Element
 */
public class WhiteSpace implements Element{

    private String param;
    /**
    * Constructor
    */
    public WhiteSpace(){
        param = "";
    }



    @Override
    public void addToView(Context context, LinearLayout layout) {

    }

    public String toXMLString() {
        return "";
    }

    public String getParam(){
        return param;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WhiteSpace that = (WhiteSpace) o;

        return !(param != null ? !param.equals(that.param) : that.param != null);

    }

    @Override
    public int hashCode() {
        return param != null ? param.hashCode() : 0;
    }
}
