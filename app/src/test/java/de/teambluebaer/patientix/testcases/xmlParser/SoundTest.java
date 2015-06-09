package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Sound;

/**
 * Created by Simon on 29.05.2015.
 */
public class SoundTest extends TestCase {

    Sound sound,sound2;

    public void setUp() throws Exception {
        sound = new Sound("TESTSRC");
        sound2 = new Sound("");
    }

    public void testConstructor (){
        assertEquals("TESTSRC",sound.getSoundSource());
        assertEquals("",sound2.getSoundSource());
    }

    public void testToXMLString() throws Exception {
        assertEquals("<audio src=\"TESTSRC\" />",sound.toXMLString());
        assertEquals("<audio src=\"\" />",sound2.toXMLString());
    }
}