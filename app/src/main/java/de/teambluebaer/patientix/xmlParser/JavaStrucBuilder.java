package de.teambluebaer.patientix.xmlParser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Created by Simon on 06.05.2015.
 */
public final class JavaStrucBuilder {
    static MetaandForm builStruc(String filename) throws Exception {
        Document xml = buildDOMTreeformXMLString(filename);
        NodeList pagelist =  xml.getElementsByTagName("page");
        NodeList metadata = xml.getElementsByTagName("metaData");
        MetaData meta = metaParser(metadata.item(0).getChildNodes());
        Form form = formParser(pagelist);
        return new MetaandForm(meta, form);
    }

    static Document buildDOMTreeformXMLString(String filename) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(new File(filename));

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
