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
    MetaAndForm metaAndForm;
    Page currentPage;
    Row currentRow;
    boolean isPID = false;
    boolean isPFN = false;
    boolean isPLN = false;
    boolean ispDate = false;
    boolean isName = false;
    boolean ispExamID = false;

    public MetaAndForm buildStruc(String xmlString) throws IOException, SAXException, ParserConfigurationException {

        SAXParserFactory spf = SAXParserFactory.newInstance();

        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(new InputSource(new java.io.StringReader( xmlString)), this);
        return metaAndForm;
    }

    @Override
    public void startDocument() throws SAXException {
        form = new Form();
        meta = new MetaData();
        metaAndForm = new MetaAndForm();
        metaAndForm.setForm(form);
        metaAndForm.setMeta(meta);
    }

    @Override
    public void endDocument() throws SAXException {
        /*if(Constants.RESIGN){
            Page page = new Page();
            Row row = new Row("","","");
            Image image = new Image("");
            row.addElement(image);
            page.addNewRow(row);
            form.addPage(page);
        }*/

    }


    @Override
    public void startElement(String uri, String localName, String qName,
                              Attributes attributes) {
        if(qName.equals("page")){
            currentPage = new Page();
        }else if (qName.equals("row")){
            currentRow = new Row(attributes.getValue("comment"),attributes.getValue("mtraComment"),
                    attributes.getValue("docComment"));
        }else if(qName.equals("text")){
            currentRow.addElement(new Text(attributes.getValue("text"), attributes.getValue("size")));
        }else if(qName.equals("checkbox")){
            currentRow.addElement(new Checkbox(attributes.getValue("text"), attributes.getValue("checked")
                    , attributes.getValue("highlight")));
        }else if(qName.equals("radiobutton")){
            currentRow.addElement(new Radio(attributes.getValue("text"), attributes.getValue("checked"),
                    attributes.getValue("highlight")));

        }else if(qName.equals("picture")){
            currentRow.addElement(new Image(attributes.getValue("src")));
        }else if(qName.equals("audio")){
            currentRow.addElement(new Sound(attributes.getValue("src")));
        }else if(qName.equals("video")){
            currentRow.addElement(new Video(attributes.getValue("src")));
        }else if(qName.equals("input")) {
            currentRow.addElement(new Input(attributes.getValue("patientInput"), attributes.getValue("text"),
                    attributes.getValue("highlight")));
        }else if(qName.equals("meta")){
            if(attributes.getValue("resign") != null){
                Constants.RESIGN = false;
            }else{
                Constants.RESIGN = true;
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
            form.addPage(currentPage);
        }else if (qName.equals("row")) {
            currentPage.addNewRow(currentRow);

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

    public MetaAndForm getMetaAndForm() {
        return metaAndForm;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public Row getCurrentRow() {
        return currentRow;
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