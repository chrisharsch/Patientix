package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Form;
import de.teambluebaer.patientix.xmlParser.MetaData;
import de.teambluebaer.patientix.xmlParser.MetaAndForm;

/**
 * Created by Simon on 29.05.2015.
 */
public class MetaAndFormTest extends TestCase {

    MetaAndForm metaAndForm;
    Form form;
    MetaData meta;

    @Override
    protected void setUp() throws Exception {
        metaAndForm = new MetaAndForm();
        meta = new MetaData();
        form = new Form();
        metaAndForm.setForm(form);
        metaAndForm.setMeta(meta);
    }


    public void testToXMLString() throws Exception {
        String inputXML = metaAndForm.toXMLString();
        String testXML = "<root><meta><pID></pID><pFirstName></pFirstName><pLastName></pLastName><pDate>Unbekannt</pDate>" +
                "<name></name><pExamID></pExamID></meta><form></form><sign image=\"\" /></root>";
        assertEquals(testXML,inputXML);
    }
}
