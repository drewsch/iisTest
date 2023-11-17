package org.iis.Import;

import org.apache.log4j.Logger;
import org.iis.DTO.EmployerValueObject;
import org.iis.Repositories.EmployerInfoRepository;

import java.util.*;

public class ImportDataService {
    final static Logger logger = Logger.getLogger(ImportDataService.class.getName());

    final static EmployerInfoRepository employerInfoRepository = EmployerInfoRepository.getInstance();

    /**
     * Method for exporting XML file data to employer_info table
     *
     * @param inputFileName name of incoming data file
     */
    public static void importData(String inputFileName) {
        try {
            assert employerInfoRepository != null;

            List<EmployerValueObject> currentItems = employerInfoRepository.fetchAll();
            List<EmployerValueObject> parsedData = XmlParser.fromXML(inputFileName);

            HashMap<Integer, EmployerValueObject> currentItemsMap = new HashMap<>();
            HashMap<Integer, EmployerValueObject> parsedDataMap = new HashMap<>();

            List<Integer> toDelete = new ArrayList<>();
            List<EmployerValueObject> toInsert = new ArrayList<>();
            List<EmployerValueObject> toUpdate = new ArrayList<>();

            for (EmployerValueObject item : currentItems) {
                currentItemsMap.put(item.hashCode(), item);
            }

            for (EmployerValueObject item : parsedData) {

                if (parsedDataMap.get(item.hashCode()) != null) {
                    logger.warn("Duplicate data: " + item.getDepCode() + " : " + item.getDepJob());
                    continue;
                }

                parsedDataMap.put(item.hashCode(), item);
            }

            for (Integer item : currentItemsMap.keySet()) {
                if (parsedDataMap.get(item) == null) {
                    EmployerValueObject dep = currentItemsMap.get(item);
                    toDelete.add(dep.getId());
                } else {
                    EmployerValueObject parsedDataItem = parsedDataMap.get(item);
                    EmployerValueObject currentItemsItem = currentItemsMap.get(item);

                    if (!parsedDataItem.getDescription().equals(currentItemsItem.getDescription())) {
                        parsedDataItem.setId(currentItemsItem.getId());
                        toUpdate.add(parsedDataItem);
                    }
                }
            }

            for (Integer item : parsedDataMap.keySet()) {
                if (currentItemsMap.get(item) == null) {
                    toInsert.add(parsedDataMap.get(item));
                }
            }

            if (!toUpdate.isEmpty()) employerInfoRepository.multiRowUpdate(toUpdate);
            if (!toDelete.isEmpty()) employerInfoRepository.multiRowDelete(toDelete);
            if (!toInsert.isEmpty()) employerInfoRepository.multiRowInsert(toInsert);
        } catch (Throwable e) {
            logger.error(e);
        }
    }
}
