package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.helper.TextSize;
import de.teambluebaer.patientix.xmlParser.Text;

/**
 * Created by Simon on 29.05.2015.
 */
public class TextTest extends TestCase {

    Text text;

    @Override
    protected void setUp() throws Exception {
        text = new Text("DAS IST NUR EIN TEST","20");
    }

    public void testConstructor(){
        assertEquals(TextSize.TITEL,text.getSize());
        assertEquals("DAS IST NUR EIN TEST",text.getText());
    }

    public void testToXMLString() throws Exception {
        String textXML = text.toXMLString();
        String testXML = "<text text=\"DAS IST NUR EIN TEST\" size=\"20\" />";
        assertEquals(testXML,textXML);
    }
}