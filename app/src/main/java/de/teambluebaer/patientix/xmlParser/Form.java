package de.teambluebaer.patientix.xmlParser;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Simon on 06.05.2015.
 *
 * Singilton, represents any kind of Form and hold <code>Pages</code>
 * and is part of <code>MetaAndForm</code>
 * @see Page
 * @see MetaAndForm
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

     /**
     * Give back all <code>Pages</code> of a Form
     * @return List of all <code>Pages</code> in a Form
     */
    public List<Page> getPageList(){
        return pageList;
    }

    /**
     * Should be used to get the first Page of the Form, whene it used the first Time, also initialiset the Iterator
     * @return the First Page of a Form
     */
    public Page getFirstPage(){
        iterator = pageList.listIterator();
        return iterator.next();
    }

    /**
     * Always give back the next <code>Page</code> of a Form
     * @return the next <code>Page</code> of a Form
     */
    public Page getNextPage(){
        return iterator.next();

    }

    /**
     * Always give back the privios <code>Page</code> of a Form
     * @return the privios <code>Page</code> of a Form
     */
    public Page getPreviousPage(){
        iterator.previous();
        Page page = iterator.previous();
        iterator.next();
        return page;
    }

    /**
     * Always give back the an String, in the Form currentPage von numberOfAllPages
     * @return the a <code>String</code> that contains the current Page number and the Number of all Pages
     */
    public String getCurrentPageText(){
        return "Seite "+iterator.nextIndex()+ " von "+ pageList.size() ;
    }

    /**
     * give back the Number of all Pages
     * @return Number of all Pages
     */
    public int getLastPage(){
        return pageList.size();
    }

    /**
     * give back the current Pagenumber
     * @return current Pagenumber
     */
    public int getCurrentPageNumber(){
        return iterator.nextIndex();
    }

    /**
     * Always give back the current <code>Page</code> of a Form
     * @return the current <code>Page</code> of a Form
     */
    public Page getcurrenPage(){
        Page page = iterator.previous();
        iterator.next();
        return page;
    }

    public String toXMLString(){
        String xmlString = new String();
        xmlString = xmlString + "<form>";
        for(Page page : pageList){
            xmlString = xmlString + page.toXMLString();
        }
        xmlString = xmlString + "</form>";

        return xmlString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Form form = (Form) o;

        if (pageList != null ? !pageList.equals(form.pageList) : form.pageList != null)
            return false;
        return !(iterator != null ? !iterator.equals(form.iterator) : form.iterator != null);

    }

    @Override
    public int hashCode() {
        int result = pageList != null ? pageList.hashCode() : 0;
        result = 31 * result + (iterator != null ? iterator.hashCode() : 0);
        return result;
    }
}