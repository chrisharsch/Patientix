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