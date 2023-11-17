package org.iis.Import;

import org.apache.log4j.Logger;
import org.iis.DTO.EmployerValueObject;
import org.iis.Repositories.EmployerInfoRepository;

import java.sql.SQLException;
import java.util.List;

public class ImportDataService {
    final static Logger logger = Logger.getLogger(ImportDataService.class.getName());

    final static EmployerInfoRepository employerInfoRepository = EmployerInfoRepository.getInstance();

    /**
     * Method for exporting XML file data to employer_info table
     *
     * @param inputFileName name of incoming data file
     */
    public static void importData(String inputFileName) throws SQLException {
        try {
            assert employerInfoRepository != null;

            List<EmployerValueObject> currentItems = employerInfoRepository.fetchAll();
            List<EmployerValueObject> parsedData = XmlParser.fromXML(inputFileName);

            List<EmployerValueObject> itemsToUpdate = DataDiff.getDiff(currentItems, parsedData, true);
            List<EmployerValueObject> itemsToDelete = getItemsToDelete(currentItems, parsedData, itemsToUpdate);
            List<EmployerValueObject> itemsToCreate = getItemsToCreate(currentItems, parsedData, itemsToUpdate);

            System.out.println(itemsToUpdate.size());
            System.out.println(itemsToCreate.size());
            System.out.println(itemsToDelete.size());


            if (!itemsToUpdate.isEmpty()) employerInfoRepository.multiRowUpdate(itemsToUpdate);
            if (!itemsToDelete.isEmpty()) employerInfoRepository.multiRowDelete(itemsToDelete);
            if (!itemsToCreate.isEmpty()) employerInfoRepository.multiRowInsert(itemsToCreate);
        } catch (Throwable e) {
            throw e;
//            logger.error(e);
        }
    }

    private static List<EmployerValueObject> getItemsToDelete(
            List<EmployerValueObject> currentItems,
            List<EmployerValueObject> parsedItems,
            List<EmployerValueObject> itemsToUpdate
    ) {
        List<EmployerValueObject> preRes = DataDiff.getDiff(currentItems, parsedItems, false);

        return DataDiff.getDiff(
                preRes,
                itemsToUpdate,
                false
        );
    }

    private static List<EmployerValueObject> getItemsToCreate(
            List<EmployerValueObject> currentItems,
            List<EmployerValueObject> parsedItems,
            List<EmployerValueObject> itemsToUpdate
    ) {
        List<EmployerValueObject> preRes = DataDiff.getDiff(parsedItems, currentItems, false);

        return DataDiff.getDiff(
                preRes,
                itemsToUpdate,
                false
        );
    }
}
