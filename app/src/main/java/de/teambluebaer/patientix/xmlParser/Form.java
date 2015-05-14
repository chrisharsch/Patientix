package de.teambluebaer.patientix.xmlParser;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Simon on 06.05.2015.
 *
 * Singilton, represents any kind of Form and hold <code>Pages</code>
 * and is part of <code>MetaandForm</code>
 * @see Page
 * @see MetaandForm
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
    private ListIterator<Page> iterator;

    /**
     * Private constructor
     */
    private Form() {
        pageList = new ArrayList<Page>();
        iterator = pageList.listIterator();
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

    public Page getNextPage(){
        return iterator.next();
    }

    public Page getPreviousPage(){
        return iterator.previous();
    }
     public Page getFirstPage(){
         return pageList.get(0);
     }

    public int getcurrendPageNumber(){
        return iterator.previousIndex() +1 ;
    }
}
