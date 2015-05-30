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
        form1.addPage(page3);

        form2 = new Form();

    }

    public void testConstructor(){
        assertEquals(new ArrayList<Page>(),form2.getPageList());
    }

    public void testGetPages(){
        assertEquals(page1,form1.getFirstPage());
        assertEquals(page2,form1.getNextPage());
        assertEquals(page1,form1.getPreviousPage());
        assertEquals(3,form1.getLastPage());
        assertEquals(1,form1.getCurrentPageNumber());
        assertEquals("Seite 1 von 3",form1.getCurrentPageText());
        assertEquals(page1,form1.getcurrenPage());
    }

    public void testToXMLString(){

        assertEquals("<form></form>",form2.toXMLString());
    }
}
