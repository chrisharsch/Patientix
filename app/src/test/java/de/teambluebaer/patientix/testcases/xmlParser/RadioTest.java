package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Radio;

/**
 * Created by Simon on 29.05.2015.
 */
public class RadioTest extends TestCase {

    Radio radio;
    Radio radio2;

    public void setUp() throws Exception {
        radio = new Radio("Ja","1","true");
        radio2 = new Radio("Nein","0","false");
    }

    public void testConstructor(){
        assertEquals("Ja",radio.getRadioText());
        assertEquals(true,radio.isChecked());
        assertEquals(true,radio.isHighlight());

        assertEquals("Nein",radio2.getRadioText());
        assertEquals(false,radio2.isChecked());
        assertEquals(false,radio2.isHighlight());
    }

    public void testToXMLString() throws Exception {

        String radioXML = radio.toXMLString();
        String testXML = "<radiobutton text=\"Ja\" checked=\"1\" highlight=\"true\" />";
        assertEquals(testXML,radioXML);

    }
}