package org.iis;

import org.iis.Export.ExportDataService;
import org.iis.Import.ImportDataService;


public class Main {
    public static void main(String[] args) {
        if (args.length == 0) throw new RuntimeException("No command nor file provided");

        String command = args[0];

        if (args.length == 1) {
            throw new RuntimeException("No filename provided");
        } else if (args.length == 2) {
            String fileName = args[1];

            switch (command) {
                case "sync":
                    ImportDataService.importData(fileName);
                    break;
                case "export":
                    ExportDataService.exportData(fileName);
                    break;
                default:
                    throw new RuntimeException("Unknown command name");
            }
        }
    }
}