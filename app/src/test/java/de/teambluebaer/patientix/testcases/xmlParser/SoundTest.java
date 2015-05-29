package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Sound;

/**
 * Created by Simon on 29.05.2015.
 */
public class SoundTest extends TestCase {

    Sound sound;

    public void setUp() throws Exception {
        sound = new Sound("TESTSRC");
    }

    public void testConstructor (){
        assertEquals("TESTSRC",sound.getSoundSource());
    }

    public void testToXMLString() throws Exception {
        assertEquals("<audio src=\"TESTSRC\" />",sound.toXMLString());
    }
}