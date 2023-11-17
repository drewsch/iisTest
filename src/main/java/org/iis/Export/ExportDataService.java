package org.iis.Export;

import org.apache.log4j.Logger;
import org.iis.DTO.EmployerValueObject;
import org.iis.Repositories.EmployerInfoRepository;
import org.w3c.dom.Document;

import java.sql.SQLException;
import java.util.List;

public class ExportDataService {

    final static Logger logger = Logger.getLogger(ExportDataService.class.getName());
    final static EmployerInfoRepository employerInfoRepository = EmployerInfoRepository.getInstance();

    /**
     * Method for exporting table employer_info data to XML file
     *
     * @param outputFileName name of destination file
     */
    public static void exportData(String outputFileName) {
        List<EmployerValueObject> result;
        try {
            assert employerInfoRepository != null;
            result = employerInfoRepository.fetchAll();

            Document dataDocument = XmlBuilder.toXML(result);

            XmlSaver.save(dataDocument, outputFileName);

        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
