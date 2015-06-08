package de.teambluebaer.patientix.xmlParser;


import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import de.teambluebaer.patientix.helper.Constants;


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
    boolean ispExamID = false;

    public MetaandForm buildStruc(String xmlString) throws IOException, SAXException, ParserConfigurationException {

        SAXParserFactory spf = SAXParserFactory.newInstance();

        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(new InputSource(new java.io.StringReader("<doc>" + xmlString + "</doc>")), this);
        return metaandForm;
    }

    @Override
    public void startDocument() throws SAXException {
        form = new Form();
        meta = new MetaData();
        metaandForm = new MetaandForm();
        metaandForm.setForm(form);
        metaandForm.setMeta(meta);
    }

    @Override
    public void endDocument() throws SAXException {
        if(Constants.resign){
            Page page = new Page();
            Row row = new Row("","","");
            Image image = new Image("");
            row.addElement(image);
            page.addNewRow(row);
            form.addPage(page);
        }

    }


    @Override
    public void startElement(String uri, String localName, String qName,
                              Attributes attributes) {
        if(qName.equals("page")){
            currendPage = new Page();
        }else if (qName.equals("row")){
            currendRow = new Row(attributes.getValue("comment"),attributes.getValue("mtraComment"),
                    attributes.getValue("docComment"));
        }else if(qName.equals("text")){
            currendRow.addElement(new Text(attributes.getValue("text"),attributes.getValue("size")));
        }else if(qName.equals("checkbox")){
            currendRow.addElement(new Checkbox(attributes.getValue("text"), attributes.getValue("checked")
                    ,attributes.getValue("highlight")));
        }else if(qName.equals("radiobutton")){
            currendRow.addElement(new Radio(attributes.getValue("text"), attributes.getValue("checked"),
                     attributes.getValue("highlight")));

        }else if(qName.equals("picture")){
            currendRow.addElement(new Image(attributes.getValue("src")));
        }else if(qName.equals("sound")){
            currendRow.addElement(new Sound(attributes.getValue("src")));
        }else if(qName.equals("video")){
            currendRow.addElement(new Video(attributes.getValue("src")));
        }else if(qName.equals("input")) {
            currendRow.addElement(new Input(attributes.getValue("patientInput"), attributes.getValue("text"),
                    attributes.getValue("highlight")));
        }else if(qName.equals("meta")){
            if(attributes.getValue("resign") != null){
                Constants.resign = false;
            }else{
                Constants.resign = true;
            }

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
        }else if(qName.equals("pExamID")){
            ispExamID = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(qName.equals("page")){
            form.addPage(currendPage);
        }else if (qName.equals("row")) {
            currendPage.addNewRow(currendRow);

        }else {
            isPID = false;
            isPFN = false;
            isPLN = false;
            ispDate = false;
            isName = false;
            ispExamID = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length){
        if(isPID){
            meta.setPatientID(new String(ch, start, length));
        }else if(isPFN){
            meta.setPatientFirstName(new String(ch, start, length));
        }else if(isPLN){
            meta.setPatientLastName(new String(ch, start, length));
        }else if(ispDate){
            meta.setPatientBithDate(new String(ch, start, length));
        }else if (isName){
            meta.setexameName(new String(ch, start, length));
        }else if (ispExamID){
            meta.setpExamID(new String(ch, start, length));
        }
    }


    public Form getForm() {
        return form;
    }

    public MetaData getMeta() {
        return meta;
    }

    public MetaandForm getMetaandForm() {
        return metaandForm;
    }

    public Page getCurrendPage() {
        return currendPage;
    }

    public Row getCurrendRow() {
        return currendRow;
    }

    public boolean isPID() {
        return isPID;
    }

    public boolean isPFN() {
        return isPFN;
    }

    public boolean isPLN() {
        return isPLN;
    }

    public boolean isIspDate() {
        return ispDate;
    }

    public boolean isName() {
        return isName;
    }
}