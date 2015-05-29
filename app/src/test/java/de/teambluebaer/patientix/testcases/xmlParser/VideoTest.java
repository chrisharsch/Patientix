package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Video;

/**
 * Created by Simon on 29.05.2015.
 */
public class VideoTest extends TestCase {

    Video video;

    public void setUp() throws Exception {
        video =new Video("TESTSRC");

    }

    public void testConstructor(){
        assertEquals("TESTSRC",video.getVideoSource());
    }

    public void testToXMLString() throws Exception {
        assertEquals("<video src=\"TESTSRC\" />",video.toXMLString());
    }
}