package de.teambluebaer.patientix.xmlParser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Created by Simon on 06.05.2015.
 */
public final class JavaStrucBuilder {

    public static MetaandForm buildStruc() {

        FileInputStream fileimput;
        try{
            //TODO INSERT RIGHT PATH
            fileimput = new FileInputStream("/sdcard");
        }catch (Exception e){
            System.out.println(e.getMessage()+"File loading Error");
            return null;
        }
        String xmlString = fileimput.toString();
        Document xml = buildDOMTreeformXMLString(xmlString);
        NodeList pagelist =  xml.getElementsByTagName("page");
        NodeList metadata = xml.getElementsByTagName("metaData");
        MetaData meta = metaParser(metadata.item(0).getChildNodes());
        Form form = formParser(pagelist);
        MetaandForm metaAndForm = MetaandForm.getInstance();
        metaAndForm.setMeta(meta);
        metaAndForm.setForm(form);
        return metaAndForm;
    }

    static Document buildDOMTreeformXMLString(String filename) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try{
             db = dbf.newDocumentBuilder();
            return db.parse(new File(filename));
        }catch (Exception e){

            System.out.println(e.getMessage() + "Parsing Error");
            return null;
        }


    }

    static MetaData metaParser(NodeList meta){

        MetaData.getInstance().setMetaData(
                meta.item(0).getNodeValue(),
                meta.item(1).getNodeValue(),
                meta.item(2).getNodeValue(),
                meta.item(3).getNodeValue(),
                meta.item(4).getNodeValue()
        );

        return MetaData.getInstance();

    }

    static Form formParser(NodeList pageList){
        Form form = Form.getInstance();

        for(int pageIterator = 0; pageIterator < pageList.getLength(); pageIterator++){
            Node currendPageNode = pageList.item(pageIterator);
            Page currendPage = new Page();
            for(int rowIterator = 0; rowIterator < currendPageNode.getChildNodes().getLength(); rowIterator++){
                Node currendRowNode = currendPageNode.getChildNodes().item(rowIterator);
                Row currendRow = new Row();
                for(int elementIterator = 0; elementIterator < currendRowNode.getChildNodes().getLength(); elementIterator++){
                    Node currendElementNode = currendRowNode.getChildNodes().item(elementIterator);
                    Element elementToAdd;
                    if(currendElementNode.getNodeName().equals("text")){
                        elementToAdd = new Text(currendElementNode.getAttributes().getNamedItem("text").getTextContent());
                    }else if(currendElementNode.getNodeName().equals("checkbox")){
                        elementToAdd = new Checkbox(currendElementNode.getAttributes().getNamedItem("text").getTextContent());
                    }else if(currendElementNode.getNodeName().equals("radiobutton")){
                        elementToAdd = new Radio(currendElementNode.getAttributes().getNamedItem("text").getTextContent());
                    }else if(currendElementNode.getNodeName().equals("image")){
                        elementToAdd = new Image(currendElementNode.getAttributes().getNamedItem("picture").getTextContent());
                    }else if(currendElementNode.getNodeName().equals("sound")){
                        elementToAdd = new Sound(currendElementNode.getAttributes().getNamedItem("sound").getTextContent());
                    }else if(currendElementNode.getNodeName().equals("video")){
                        elementToAdd = new Video(currendElementNode.getAttributes().getNamedItem("video").getTextContent());
                    }else if(currendElementNode.getNodeName().equals("input")){
                        elementToAdd = new Input();
                    }else{
                        elementToAdd = new WhiteSpace();
                    }
                    currendRow.addElement(elementToAdd);
                }
                currendPage.addNewRow(currendRow);
            }
            form.addPage(currendPage);
        }

        return null;
    }


}
