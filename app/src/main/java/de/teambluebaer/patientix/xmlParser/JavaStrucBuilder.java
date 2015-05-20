package de.teambluebaer.patientix.xmlParser;


import android.os.Environment;
import android.util.Log;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

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

    public MetaandForm buildStruc(String xmlString) throws IOException, SAXException, ParserConfigurationException {

        SAXParserFactory spf = SAXParserFactory.newInstance();

        SAXParser saxParser = spf.newSAXParser();
        saxParser.parse(new InputSource(new java.io.StringReader(xmlString)), this);
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

    }


    @Override
    public void startElement(String uri, String localName, String qName,
                              Attributes attributes) {
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
            Log.d("SCHREIBT","NIX REIN");
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
        }
    }
















}
