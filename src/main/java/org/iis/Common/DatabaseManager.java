package org.iis.Common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    /**
     * @return database connection
     * @throws SQLException throws in case of unreachable database
     * @throws IOException throws in case of unreachable properties file
     */
    public static Connection get() throws SQLException, IOException {
        Properties appProps = new Properties();

        appProps.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));

        return DriverManager.getConnection((String) appProps.get("database_url"));
    }
}
