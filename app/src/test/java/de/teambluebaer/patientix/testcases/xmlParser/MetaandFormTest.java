package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import de.teambluebaer.patientix.xmlParser.Form;
import de.teambluebaer.patientix.xmlParser.MetaData;
import de.teambluebaer.patientix.xmlParser.MetaandForm;

/**
 * Created by Simon on 29.05.2015.
 */
public class MetaandFormTest extends TestCase {

    MetaandForm metaandForm;
    Form form;
    MetaData meta;

    @Override
    protected void setUp() throws Exception {
        metaandForm = new MetaandForm();
        meta = new MetaData();
        form = new Form();
        metaandForm.setForm(form);
        metaandForm.setMeta(meta);
    }

    public void testToXMLString() throws Exception {
        String inputXML = metaandForm.toXMLString();
        String testXML = "<root><meta><pID></pID><pFirstName></pFirstName><pLastName></pLastName><pDate>Unbekannt</pDate><name></name></meta><form></form></root>";
        assertEquals(testXML,inputXML);
    }
}