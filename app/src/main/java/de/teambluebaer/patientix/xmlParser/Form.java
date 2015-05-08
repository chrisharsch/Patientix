package de.teambluebaer.patientix.xmlParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 06.05.2015.
 *
 * Singilton, represents any kind of Form and hold <code>Pages</code>
 * and is part of <code>MetaAndForm</code>
 * @see Page
 * @see MetaAndForm
 */
public class Form {
    private static Form ourInstance = new Form();

    /**
     * give access to the Singleton Instance
     * @return a Instance of Form
     */
    public static Form getInstance() {
        return ourInstance;
    }

    private List<Page> pageList;

    /**
     * Private constructor
     */
    private Form() {
        pageList = new ArrayList<Page>();
    }

    /**
     * remove reference to old Form Objects
     */
    public void refresh(){
        pageList = new ArrayList<Page>();
    }

    /**
     * adds new Page to an Form
     * @param newPage Page that you want to add
     */
    public void addPage (Page newPage){
        pageList.add(newPage);
    }
}
