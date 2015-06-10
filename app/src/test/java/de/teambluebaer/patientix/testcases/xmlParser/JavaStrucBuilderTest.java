package de.teambluebaer.patientix.testcases.xmlParser;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import de.teambluebaer.patientix.helper.Constants;
import de.teambluebaer.patientix.xmlParser.Checkbox;
import de.teambluebaer.patientix.xmlParser.Form;
import de.teambluebaer.patientix.xmlParser.Input;
import de.teambluebaer.patientix.xmlParser.JavaStrucBuilder;
import de.teambluebaer.patientix.xmlParser.MetaAndForm;
import de.teambluebaer.patientix.xmlParser.MetaData;
import de.teambluebaer.patientix.xmlParser.Page;
import de.teambluebaer.patientix.xmlParser.Radio;
import de.teambluebaer.patientix.xmlParser.Row;
import de.teambluebaer.patientix.xmlParser.Sound;
import de.teambluebaer.patientix.xmlParser.Text;
import de.teambluebaer.patientix.xmlParser.Video;

/**
 * Created by Simon on 29.05.2015.
 */
public class JavaStrucBuilderTest extends TestCase {

    String xmlTestString;
    String xmlTestString2;
    JavaStrucBuilder testBuilder;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        xmlTestString =
                "<meta>" +
                    "<pID>1234</pID>" +
                    "<pFirstName>Hans</pFirstName>" +
                    "<pLastName>Peter</pLastName>" +
                    "<pDate>12.12.2012</pDate>" +
                    "<name>MRT</name>" +
                "</meta>" +
                "<form>" +
                    "<page>" +
                        "<row>" +
                            "<text text=\"DAS IST EIN TEST\" size=\"20\"/>" +
                            "<radiobutton text=\"DAS IST EIN TEST\" checked=\"1\" highlight=\"false\"/>" +
                            "<checkbox text=\"DAS IST EIN TEST\" checked=\"1\" highlight=\"false\"/>"+

                            "<sound/>"+
                            "<video/>"+
                            "<input text=\"DAS IST EIN TEST\" patientInput=\"INPUT\" highlight=\"false\"/>"+
                        "</row>" +
                    "</page>" +
                "</form>";

        xmlTestString2 = "<meta resign=\"1\"/>";
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
        Radio radio = new Radio("DAS IST EIN TEST","1","false");
        row.addElement(radio);
        Checkbox checkbox = new Checkbox("DAS IST EIN TEST","1","false");
        row.addElement(checkbox);

        Sound sound = new Sound("");
        row.addElement(sound);
        Video video = new Video("");
        row.addElement(video);
        Input input = new Input("INPUT","DAS IST EIN TEST","false");
        row.addElement(input);
        page.addNewRow(row);
        List<Page> pageList = new ArrayList<>();
        pageList.add(page);

        assertEquals(pageList, form.getPageList());
        assertEquals(true, Constants.RESIGN);

        metaAndForm = testBuilder.buildStruc(xmlTestString2);
        assertEquals(false,Constants.RESIGN);

    }
}