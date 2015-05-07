package de.teambluebaer.patientix.xmlParser;

import org.w3c.dom.Document;
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
        NodeList pagelist =  xml.getElementsByTagName("form");
        NodeList metadata = xml.getElementsByTagName("metaData");
        MetaData meta = metaPaser(metadata.item(0).getChildNodes());
        Form form = formPaser(pagelist);
        MetaandForm generatedForm = new MetaandForm(meta, form);
        return generatedForm;
    }

    static Document buildDOMTreeformXMLString(String filename) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(filename));
        return doc;

    }

    static MetaData metaPaser(NodeList meta){

        MetaData.getInstance().setMetaData(
            meta.item(0).getNodeValue(),
            meta.item(1).getNodeValue(),
            meta.item(2).getNodeValue(),
            meta.item(3).getNodeValue(),
            meta.item(4).getNodeValue()
        );

        return MetaData.getInstance();

    }

    static Form formPaser(NodeList pageList){

    }


}
