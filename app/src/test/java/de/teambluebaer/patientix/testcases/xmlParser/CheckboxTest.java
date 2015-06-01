package de.teambluebaer.patientix.testcases.xmlParser;

import android.widget.LinearLayout;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Checkbox;

/**
 * Created by Simon on 26.05.2015.
 */
public class CheckboxTest extends TestCase {

    Checkbox checkbox;
    LinearLayout linearLayout;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        checkbox = new Checkbox("Ja","false", "Was hei�t das?", "nix", "das sie das haben","false");

    }


    public void testConstructor(){
        assertEquals("Ja", checkbox.getCheckboxText());
        assertEquals("Was hei�t das?", checkbox.getPatientCommentar());
        assertEquals("nix", checkbox.getMtraCommentar());
        assertEquals("das sie das haben", checkbox.getDoctorCommentar());
        assertEquals(false, checkbox.isChecked());
    }


    public void testToXMLString (){
        String xml = "<checkbox text=\"Ja\" checked=\"false\" comment\"Was hei�t das?\" mtraComment\"nix\" " +
                "docComment\"das sie das haben\" highlight=\"false\" />";
        assertEquals(xml ,checkbox.toXMLString());
    }

        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
        }
    }
