package de.teambluebaer.patientix.helper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hasan on 08.12.2014.
 */
public class MapDataClass {

    public HashMap<String, String> parameterMap = new HashMap<String, String>();

    public String toString() {
        String output = "[";
        for (Map.Entry e : parameterMap.entrySet()) {
            output += "'" + e.getKey() + "'" + ":'" + e.getValue() + "'" + ", ";
        }
        if (output.endsWith(",")) {
            output = output.substring(0, output.length() - 1);
        }
        output += "]";
        return output;
    }
}