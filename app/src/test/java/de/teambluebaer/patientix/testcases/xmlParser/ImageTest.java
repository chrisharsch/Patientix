package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Image;

/**
 * Created by Simon on 28.05.2015.
 */
public class ImageTest extends TestCase{

    Image imageWithSRC;
    Image imageWithoutSRC;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        imageWithSRC = new Image("TESTSRC");
        imageWithoutSRC = new Image("");

    }

    public void testConstructor(){
        assertEquals("TESTSRC", imageWithSRC.getImageSource());
    }

    public void testToXMLString(){
        assertEquals("<picture src=\"TESTSRC\" />", imageWithSRC.toXMLString());
        assertEquals("<picture src=\"\" />", imageWithoutSRC.toXMLString());
    }
}
