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

    public List<Page> getPageList(){
        return pageList;
    }

    public Page getFirstPage(){
        iterator = pageList.listIterator();
        return iterator.next();
    }

    public Page getNextPage(){
        return iterator.next();

    }


    public Page getPreviousPage(){
        iterator.previous();
        Page page = iterator.previous();
        iterator.next();
        return page;
    }

    public String getCurrentPageText(){
        return "Seite "+iterator.nextIndex()+ " von "+ pageList.size() ;
    }
    public int getLastPage(){
        return pageList.size();
    }
    public int getCurrentPageNumber(){
        return iterator.nextIndex();
    }

    public Page getcurrenPage(){
        Page page = iterator.previous();
        iterator.next();
        return page;
    }

}
