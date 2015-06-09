package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.helper.TextSize;
import de.teambluebaer.patientix.xmlParser.Text;

/**
 * Created by Simon on 29.05.2015.
 */
public class TextTest extends TestCase {

    Text text,text2,text3;

    @Override
    protected void setUp() throws Exception {
        text = new Text("DAS IST NUR EIN TEST","20");
        text2 = new Text("DAS IST NUR EIN TEST","15");
        text3 = new Text("DAS IST NUR EIN TEST","14");
    }

    public void testConstructor(){
        assertEquals(TextSize.TITEL,text.getSize());
        assertEquals(TextSize.SUBTITEL,text2.getSize());
        assertEquals(TextSize.TEXT,text3.getSize());
        assertEquals("DAS IST NUR EIN TEST",text.getText());
    }

    public void testToXMLString() throws Exception {
        String textXML = text.toXMLString();
        String testXML = "<text text=\"DAS IST NUR EIN TEST\" size=\"20\" />";
        assertEquals(testXML,textXML);

        textXML = text2.toXMLString();
        testXML = "<text text=\"DAS IST NUR EIN TEST\" size=\"15\" />";
        assertEquals(testXML,textXML);

        textXML = text3.toXMLString();
        testXML = "<text text=\"DAS IST NUR EIN TEST\" size=\"14\" />";
        assertEquals(testXML,textXML);
    }
}