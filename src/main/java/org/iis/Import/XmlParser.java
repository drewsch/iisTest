package org.iis.Import;

import org.apache.log4j.Logger;
import org.iis.DTO.EmployerValueObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    final static Logger logger = Logger.getLogger(XmlParser.class.getName());

    /**
     * @param inputFile name of incoming data file
     * @return parsed collection of items
     */
    public static List<EmployerValueObject> fromXML(String inputFile) {
        Document doc = null;
        List<EmployerValueObject> result = new ArrayList<>();
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = db.parse(new File(inputFile));

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Item");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    EmployerValueObject item = new EmployerValueObject();

                    item.setDepCode(eElement.getElementsByTagName("DepCode").item(0).getTextContent());
                    item.setDepJob(eElement.getElementsByTagName("DepJob").item(0).getTextContent());
                    item.setDescription(eElement.getElementsByTagName("Description").item(0).getTextContent());

                    result.add(item);
                }
            }
        } catch (Exception e) {
            logger.error("XML parse error : " + e.getMessage());
        }

        return result;
    }
}
