package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import de.teambluebaer.patientix.xmlParser.Element;
import de.teambluebaer.patientix.xmlParser.Row;
import de.teambluebaer.patientix.xmlParser.WhiteSpace;

/**
 * Created by Simon on 29.05.2015.
 */
public class RowTest extends TestCase {

    Row row,row2;

    @Override
    protected void setUp() throws Exception {
        row = new Row("","","");
        row2 = new Row("1","2","3");
    }

    public void testConstructor(){
        assertEquals(new ArrayList<Row>(),row.getElements());
    }

    public void testAddElemnet(){
        row.addElement(new WhiteSpace());
        List<Element> list = new ArrayList<>();
        list.add(new WhiteSpace());
        assertEquals(list,row.getElements());

    }

    public void testToXMLString() throws Exception {
        String rowXML = row.toXMLString();
        String testXML = "<row></row>";
        assertEquals(testXML,rowXML);

        rowXML = row2.toXMLString();
        testXML = "<row comment=\"1\" mtraComment=\"2\" docComment=\"3\"></row>";
        assertEquals(testXML,rowXML);
    }
}