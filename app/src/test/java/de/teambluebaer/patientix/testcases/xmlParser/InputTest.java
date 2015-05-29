package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Input;

/**
 * Created by Simon on 29.05.2015.
 */
public class InputTest extends TestCase {

    Input input;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Input input = new Input("","HIER WAS EINGEBEN","HÄÄ","","");
    }

    public void testConstructor() {
        assertEquals("HIER WAS EINGEBEN",input.getInputText());
        assertEquals("bitte Hier eingeben",input.getHint());
        assertEquals("",input.getPatientInput());
        assertEquals("", input.getDoctorCommentar());
        assertEquals("", input.getMtraCommentar());
        assertEquals("HÄÄ", input.getPatientCommentar());
    }

    public void testToXMLString() throws Exception {

    }
}