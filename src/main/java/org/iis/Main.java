package org.iis;

import org.apache.log4j.Logger;
import org.iis.Export.ExportDataService;
import org.iis.Import.ImportDataService;

import java.sql.SQLException;

public class Main {
    final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws SQLException {
        ExportDataService.exportData("output.xml");
        ImportDataService.importData("output2.xml");
    }
}