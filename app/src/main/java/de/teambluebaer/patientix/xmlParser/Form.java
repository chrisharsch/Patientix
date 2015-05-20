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

    private List<Page> pageList;
    private ListIterator<Page> iterator;


    /**
     * Private constructor
     */
    public Form() {
        pageList = new ArrayList<Page>();

    }

     /**
     * adds new Page to an Form
     * @param newPage Page that you want to add
     */
    public void addPage (Page newPage){
        pageList.add(newPage);
    }

    public Page getFirstPage(){
        iterator = pageList.listIterator();
        return iterator.next();
    }
    public String getNumberOfPages(){
        return ""+ pageList.size();
    }

    public Page getNextPage(){
        if(iterator.hasNext()) {
            return iterator.next();
        }else{
            return iterator.previous();
        }

    }

    public Page getPreviousPage(){
        return iterator.previous();
    }

    public String getcurrendPageNumber(){
        return "Seite "+iterator.nextIndex() +" von "+ pageList.size() ;
    }
    public int getcurrentPageNumber(){
        return iterator.nextIndex();
    }
    public int getLastPageNumber(){
        return pageList.size();
    }

}
