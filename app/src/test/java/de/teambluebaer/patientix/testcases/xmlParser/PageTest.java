package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import de.teambluebaer.patientix.xmlParser.Page;
import de.teambluebaer.patientix.xmlParser.Radio;
import de.teambluebaer.patientix.xmlParser.Row;

/**
 * Created by Simon on 29.05.2015.
 */
public class PageTest extends TestCase {

    Page page,page2;
    Row row;
    Radio radio;

    @Override
    protected void setUp() throws Exception {
        page =new Page();
        page2 = new Page();
        row = new Row("","","");
        radio = new Radio("Ja","1","1");
        page2.addNewRow(row);
        row.addElement(radio);


    }

    public void testConstructor(){
        assertEquals(new ArrayList<Row>(),page.getRows());
    }

    public void testAddNewRow(){
        page.addNewRow(new Row("","",""));
        List<Row> list = new ArrayList<Row>();
        list.add(new Row("","",""));
        assertEquals(list, page.getRows());
    }

    public void testToXMLString() throws Exception {
        String pageXML = page.toXMLString();
        String testXML = "<page></page>";
        assertEquals(testXML,pageXML);
    }

    public void testIsRelevant() throws Exception {
        assertEquals(false,page.isRelevant());
        assertEquals(true, page2.isRelevant());
    }
}