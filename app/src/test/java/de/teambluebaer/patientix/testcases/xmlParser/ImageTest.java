package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Image;

/**
 * Created by Simon on 28.05.2015.
 */
public class ImageTest extends TestCase{

    Image image;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        image = new Image("TESTSRC");

    }

    public void testConstructor(){
        assertEquals("TESTSRC",image.getImageSource());
    }

    public void testToXMLString(){
        assertEquals("<picture src=\"TESTSRC\" />",image.toXMLString());
    }
}
