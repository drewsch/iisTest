package org.iis.Export;

import org.apache.log4j.Logger;
import org.iis.Main;
import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlSaver {

    final static Logger logger = Logger.getLogger(XmlSaver.class.getName());

    /**
     * @param document document for save
     * @param filename name of destination file
     */
    public static void save(Document document, String filename)
    {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(filename));
            Source input = new DOMSource(document);

            transformer.transform(input, output);
        } catch (TransformerException e) {
            logger.error("Error saving XML file: " + e.getMessage());
        }
    }
}
