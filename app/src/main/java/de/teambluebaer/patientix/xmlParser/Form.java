package de.teambluebaer.patientix.xmlParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 06.05.2015.
 */
public class Form {
    private static Form ourInstance = new Form();

    public static Form getInstance() {
        return ourInstance;
    }

    private List<Page> pageList;

    private Form() {

    }

    public void refresh(){
        pageList = new ArrayList<Page>();
    }

    public void addPage (Page newPage){
        pageList.add(newPage);
    }
}
