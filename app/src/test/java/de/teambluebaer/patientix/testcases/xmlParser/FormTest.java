package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import java.util.ArrayList;

import de.teambluebaer.patientix.xmlParser.Form;
import de.teambluebaer.patientix.xmlParser.Page;

/**
 * Created by Simon on 28.05.2015.
 */
public class FormTest extends TestCase{

    Form form1;
    Form form2;
    Form form3;
    Page page1;
    Page page2;
    Page page3;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        page1 = new Page();
        page2 = new Page();
        page3 = new Page();

        form1 = new Form();
        form1.addPage(page1);
        form1.addPage(page2);

        form2 = new Form();

        form3 = new Form();
        form3.addPage(page3);



    }

    public void testConstructor(){
        assertEquals(new ArrayList<Page>(),form2.getPageList());
    }

    public void testGetPages(){
        Page testPage = form1.getFirstPage();
        assertEquals(page1,testPage);
        testPage = form1.getNextPage();
        assertEquals(page2,testPage);
        testPage = form1.getPreviousPage();
        assertEquals(page1, testPage);
        int testInt = form1.getLastPage();
        assertEquals(2,testInt);
        testInt = form1.getCurrentPageNumber();
        assertEquals(1,testInt);
        String testString = form1.getCurrentPageText();
        assertEquals("Seite 1 von 2", testString);
        testPage = form1.getcurrenPage();
        assertEquals(page1,testPage);
    }

    public void testToXMLString(){
        String testXML = "<form></form>";
        String vergleichsXML = form2.toXMLString();
        assertEquals(testXML,vergleichsXML);
        testXML = "<form><page></page></form>";
        vergleichsXML = form3.toXMLString();
        assertEquals(testXML,vergleichsXML);
    }
}
