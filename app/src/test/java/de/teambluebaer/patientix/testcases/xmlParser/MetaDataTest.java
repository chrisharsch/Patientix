package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.MetaData;

/**
 * Created by Simon on 29.05.2015.
 */
public class MetaDataTest extends TestCase {

    MetaData meta;
    MetaData meta2;

    public void setUp() throws Exception {
        meta = new MetaData();
        meta.setexameName("MRT");
        meta.setPatientFirstName("Hans");
        meta.setPatientLastName("Peter");
        meta.setPatientID("1234");
        meta.setPatientBithDate("12.12.2012");

        meta2 = new MetaData();
    }

    public void testConstructor(){
        assertEquals("",meta2.getExameName());
        assertEquals("",meta2.getPatientFirstName());
        assertEquals("",meta2.getPatientLastName());
        assertEquals("Unbekannt",meta2.getPatientBithDate());
        assertEquals("",meta2.getPatientID());
    }

    public void testToXMLString() throws Exception {
        String metaXML = meta.toXMLString();
        String testXML = "<meta><pID>1234</pID><pFirstName>Hans</pFirstName><pLastName>Peter</pLastName><pDate>12.12.2012</pDate><name>MRT</name></meta>";
        assertEquals(testXML,metaXML);
    }
}