package org.iis.Export;

import org.apache.log4j.Logger;
import org.iis.DTO.EmployerValueObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

public class XmlBuilder {
    final static Logger logger = Logger.getLogger(XmlBuilder.class.getName());

    /**
     * @param data list of fetched items from database
     * @return complete XML document
     */
    public static Document toXML(List<EmployerValueObject> data) {
        Document doc = null;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = db.newDocument();
            Element root = doc.createElement("EmployerInfo");
            doc.appendChild(root);
            for (EmployerValueObject item: data) {
                Element node = doc.createElement("Item");
                root.appendChild(node);

                Element depCodeElement = doc.createElement("DepCode");
                Text depCode = doc.createTextNode(item.getDepCode());
                depCodeElement.appendChild(depCode);

                Element depJobElement = doc.createElement("DepJob");
                Text depJob = doc.createTextNode(item.getDepJob());
                depJobElement.appendChild(depJob);

                Element descriptionElement = doc.createElement("Description");
                Text description = doc.createTextNode(item.getDescription());
                descriptionElement.appendChild(description);

                node.appendChild(depCodeElement);
                node.appendChild(depJobElement);
                node.appendChild(descriptionElement);
            }
        } catch (ParserConfigurationException e) {
            logger.error("XML creation error : " + e.getMessage());
        }

        return doc;
    }
}
