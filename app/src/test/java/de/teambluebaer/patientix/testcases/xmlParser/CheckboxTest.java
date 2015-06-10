package de.teambluebaer.patientix.testcases.xmlParser;

import android.widget.LinearLayout;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Checkbox;

/**
 * Created by Simon on 26.05.2015.
 */
public class CheckboxTest extends TestCase {

    Checkbox cbJaUncheckedUnhighlighted;
    Checkbox cbNeinCheckedHighleighted;
    LinearLayout linearLayout;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        cbJaUncheckedUnhighlighted = new Checkbox("Ja","0",null);
        cbNeinCheckedHighleighted = new Checkbox("Nein","1","true");

    }


    public void testConstructor(){
        assertEquals("Ja", cbJaUncheckedUnhighlighted.getCheckboxText());
        assertEquals(false, cbJaUncheckedUnhighlighted.isChecked());
        assertEquals("Nein",cbNeinCheckedHighleighted.getCheckboxText());
        assertEquals(true, cbNeinCheckedHighleighted.isChecked());
    }


    public void testToXMLString (){
        String xml1 = "<checkbox text=\"Ja\" checked=\"0\" highlight=\"false\" />";
        String test1 = cbJaUncheckedUnhighlighted.toXMLString();
        assertEquals(xml1,test1);
        String xml2 = "<checkbox text=\"Nein\" checked=\"1\" highlight=\"true\" />";
        String test2 = cbNeinCheckedHighleighted.toXMLString();
        assertEquals(xml2,test2);


    }

        @Override
        protected void tearDown() throws Exception {
            super.tearDown();
        }
    }
