package de.teambluebaer.patientix.xmlParser;

import android.os.Environment;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Created by Simon on 06.05.2015.
 *
 * holds functions to pars an XMLString into an defined Java Structure
 */
public final class JavaStrucBuilder {

    /**
    * creates an <code>MetaandForm</code> Object from an File
    * @return file that has been parsed into a <code>MetaandForm</code> Object
    */
    public static MetaandForm buildStruc() {

        FileInputStream fileimput;
        try{
            //TODO INSERT RIGHT PATH
            fileimput = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "MRTAufklärung.txt");
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

    /**
     * build a DOM-Tree out of an XML-String
     * @param filename represents an XML-String
     * @return the generated DOM-Tree
     */
    protected static Document buildDOMTreeformXMLString(String filename) {

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

    /**
     * parses DOM-Tree Object with Meta Data into a <code>MetaData</code> Object
     * @param meta represents the Meta Data as a NodeList
     * @return initialised <code>MetaData</code> Objekt
     * @see MetaData
     */
    protected static MetaData metaParser(NodeList meta){

        MetaData.getInstance().setMetaData(
                meta.item(0).getNodeValue(),
                meta.item(1).getNodeValue(),
                meta.item(2).getNodeValue(),
                meta.item(3).getNodeValue(),
                meta.item(4).getNodeValue()
        );

        return MetaData.getInstance();

    }

    /**
     * parses DOM-Tree Object with Form Data into an <code>Form</code> Object
     * @param pageList List of all <code>Pages</code> that are hold in the <code>Form</code>
     * @return initialised <code>Form</code> Objekt
     * @see Form
     */
    protected static Form formParser(NodeList pageList){
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
