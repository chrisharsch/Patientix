package de.teambluebaer.patientix.xmlParser;


import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


/**
 * Created by Simon on 06.05.2015.
 *
 * holds functions to pars an XMLString into an defined Java Structure
 */
public class JavaStrucBuilder extends DefaultHandler{

    Form form;
    MetaData meta;
    MetaandForm metaandForm;
    Page currendPage;
    Row currendRow;
    boolean isPID = false;
    boolean isPFN = false;
    boolean isPLN = false;
    boolean ispDate = false;
    boolean isName = false;

    public static MetaandForm buildStruc(){



        return MetaandForm.getInstance();
    }
    @Override
    public void StartDocument() throws Exception{
        Form.getInstance().refresh();
        MetaData.getInstance().refresh();
        metaandForm = MetaandForm.getInstance();

    }


    public void EndDocument() throws Exception{
        System.out.println("Dokument geladen");
    }

    public void StartElement(String uri, String localName, String qName,
                             Attributes attributes) throws Exception{
        if(qName.equals("page")){
            currendPage = new Page();
        }else if (qName.equals("row")){
            currendRow = new Row();
        }else if(qName.equals("text")){
            currendRow.addElement(new Text(attributes.getValue("text"),attributes.getValue("size")));
        }else if(qName.equals("checkbox")){
            currendRow.addElement(new Checkbox(attributes.getValue("text")));
        }else if(qName.equals("radiobutton")){
            currendRow.addElement(new Radio(attributes.getValue("text")));
        }else if(qName.equals("image")){
            currendRow.addElement(new Image(attributes.getValue("src")));
        }else if(qName.equals("sound")){
            currendRow.addElement(new Sound(attributes.getValue("src")));
        }else if(qName.equals("video")){
            currendRow.addElement(new Video(attributes.getValue("src")));
        }else if(qName.equals("input")) {
            currendRow.addElement(new Input());
        }else if(qName.equals("pID")){
            isPID = true;
        }else if(qName.equals("pFirstName")){
            isPFN = true;
        }else if(qName.equals("pLastName")){
            isPLN = true;
        }else if(qName.equals("pDate")){
            ispDate = true;
        }else if(qName.equals("name")){
            isName = true;
        }else{
        }


    }

    public void EndElement(String uri, String localName, String qName,
                           Attributes attributes) throws Exception{
        if(qName.equals("page")){
            Form.getInstance().addPage(currendPage);
        }else if (qName.equals("row")){
            currendPage.addNewRow(currendRow);
        }else{
            isPID = false;
            isPFN = false;
            isPLN = false;
            ispDate = false;
            isName = false;
        }

    }

    public void character(char[] ch, int start, int length){
        if(isPID){
            MetaData.getInstance().setPatientID(new String(ch,start,length));
        }else if(isPFN){
            MetaData.getInstance().setPatientFirstName(new String(ch, start, length));
        }else if(isPLN){
            MetaData.getInstance().setPatientLastName(new String(ch, start, length));
        }else if(ispDate){
            MetaData.getInstance().setPatientBithDate(new String(ch, start, length));
        }else if (isName){
            MetaData.getInstance().setexameName(new String(ch, start, length));
        }
    }
















}
