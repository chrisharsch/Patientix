package de.teambluebaer.patientix.xmlParser;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

/**
 * Created by Simon on 26.05.2015.
 */
public class CheckboxTest extends TestCase {

    Checkbox checkbox;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        checkbox = new Checkbox("Ja", "Was heißt das?", "nix", "das sie das haben");
    }

    @SmallTest
    public void testConstructor(){
        assertEquals("Ja", checkbox.getCheckboxText());
        assertEquals("Was heißt das?", checkbox.getPatientCommentar());
        assertEquals("nix", checkbox.getMtraCommentar());
        assertEquals("das sie das haben", checkbox.getDoctorCommentar());
    }

    @SmallTest
    public void testToXMLString (){
        String xml = "<checkbox/ text=\"Ja\" checked=\"true\" comment\"Was heißt das?\" mtraComment\"nix\" " +
                "docComment\"dassie das haben\" >";
        assertEquals(xml ,checkbox.toXMLString());
    }





    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
