package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Radio;

/**
 * Created by Simon on 29.05.2015.
 */
public class RadioTest extends TestCase {

    Radio radio;

    public void setUp() throws Exception {
        radio = new Radio("Ja","false","1");
    }

    public void testConstructor(){
        assertEquals("Ja",radio.getRadioText());

    }

    public void testToXMLString() throws Exception {

        String radioXML = radio.toXMLString();
        String testXML = "<radiobutton text=\"Ja\" checked=\"0\" highlight=\"true\" />";
        assertEquals(testXML,radioXML);

    }
}