package org.iis.Common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DatabaseManager {
    /**
     * @return database connection
     * @throws SQLException throws in case of unreachable database
     * @throws IOException throws in case of unreachable properties file
     */
    public static Connection get() throws SQLException, IOException {
        String rootPath = Objects.requireNonNull(
                Thread.currentThread().getContextClassLoader().getResource("")
        ).getPath();
        String appConfigPath = rootPath + "app.properties";

        Properties appProps = new Properties();

        appProps.load(new FileInputStream(appConfigPath));

        return DriverManager.getConnection((String) appProps.get("database_url"));
    }
}
