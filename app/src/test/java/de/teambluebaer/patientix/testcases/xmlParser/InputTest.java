package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Input;

/**
 * Created by Simon on 29.05.2015.
 */
public class InputTest extends TestCase {

    Input ipNCHintUnhiglighted;
    Input ipCNHintHighleited;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ipNCHintUnhiglighted = new Input("","HIER WAS EINGEBEN","false");
        ipCNHintHighleited = new Input("IRGENTWAS","","true");
    }

    public void testConstructor() {
        assertEquals("HIER WAS EINGEBEN",ipNCHintUnhiglighted.getInputText());
        assertEquals("bitte hier eingeben",ipNCHintUnhiglighted.getHint());
        assertEquals("",ipNCHintUnhiglighted.getPatientInput());
        assertEquals(false,ipNCHintUnhiglighted.isHighlight());

        assertEquals("",ipCNHintHighleited.getInputText());
        assertEquals("bitte hier eingeben",ipCNHintHighleited.getHint());
        assertEquals("IRGENTWAS",ipCNHintHighleited.getPatientInput());
        assertEquals(true,ipCNHintHighleited.isHighlight());

    }

    public void testToXMLString() throws Exception {
        String inputXML = ipNCHintUnhiglighted.toXMLString();
        String testXML = "<input text=\"HIER WAS EINGEBEN\" patientInput=\"\" highlight=\"false\" />";
        assertEquals(testXML,inputXML);
        inputXML = ipCNHintHighleited.toXMLString();
        testXML = "<input text=\"\" patientInput=\"IRGENTWAS\" highlight=\"true\" />";
        assertEquals(testXML,inputXML);
    }
}