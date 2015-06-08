package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import de.teambluebaer.patientix.xmlParser.Form;
import de.teambluebaer.patientix.xmlParser.JavaStrucBuilder;
import de.teambluebaer.patientix.xmlParser.MetaData;
import de.teambluebaer.patientix.xmlParser.MetaAndForm;
import de.teambluebaer.patientix.xmlParser.Page;
import de.teambluebaer.patientix.xmlParser.Row;
import de.teambluebaer.patientix.xmlParser.Text;

/**
 * Created by Simon on 29.05.2015.
 */
public class JavaStrucBuilderTest extends TestCase {

    String xmlTestString;
    JavaStrucBuilder testBuilder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        xmlTestString = "<meta><pID>1234</pID><pFirstName>Hans</pFirstName><pLastName>Peter</pLastName><pDate>12.12.2012</pDate><name>MRT</name></meta><form><page><row><text text=\"DAS IST EIN TEST\" size=\"20\"/></row></page></form>";
        testBuilder = new JavaStrucBuilder();


    }

    public void testBuildStruc() throws Exception {
        MetaAndForm metaAndForm = testBuilder.buildStruc(xmlTestString);
        Form form = metaAndForm.getForm();
        MetaData meta = metaAndForm.getMeta();
        //Meta
        assertEquals("MRT",meta.getExameName());
        assertEquals("12.12.2012",meta.getPatientBithDate());
        assertEquals("Hans",meta.getPatientFirstName());
        assertEquals("Peter",meta.getPatientLastName());
        assertEquals("1234",meta.getPatientID());
        //Form
        Page page = new Page();
        Row row = new Row("","","");
        Text text = new Text("DAS IST EIN TEST","20");
        row.addElement(text);
        page.addNewRow(row);
        List<Page> pageList= new ArrayList<Page>();
        pageList.add(page);
        assertEquals(pageList, form.getPageList());

    }
}