package org.iis.Repositories;

import org.apache.log4j.Logger;
import org.iis.Common.DatabaseManager;
import org.iis.DTO.EmployerValueObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployerInfoRepository {

    final static Logger logger = Logger.getLogger(EmployerInfoRepository.class.getName());

    Connection conn;

    /**
     * @throws SQLException throws in case of unreachable database
     * @throws IOException  throws in case of unreachable properties file
     */
    private EmployerInfoRepository() throws SQLException, IOException {
        conn = DatabaseManager.get();
    }

    public static EmployerInfoRepository getInstance() {
        try {
            return new EmployerInfoRepository();
        } catch (Throwable e) {
            logger.error("Unable to get instance of repository: " + e.getMessage());
        }

        return null;
    }

    /**
     * @return list of items from database table
     * @throws SQLException throws in case of unreachable database
     */
    public List<EmployerValueObject> fetchAll() throws SQLException {
        Statement statement = conn.createStatement();

        ResultSet rs = statement.executeQuery("SELECT * FROM employer_info");

        List<EmployerValueObject> collection = new ArrayList<>();

        while (rs.next()) {
            EmployerValueObject vo = new EmployerValueObject();

            vo.setId(rs.getInt(vo.ID_FIELD));
            vo.setDepCode(rs.getString(vo.DEP_CODE_FIELD));
            vo.setDepJob(rs.getString(vo.DEP_JOB_FIELD));
            vo.setDescription(rs.getString(vo.DESCRIPTION_FIELD));

            collection.add(vo);
        }

        rs.close();
        statement.close();

        return collection;
    }

    /**
     * @param data list of parsed items
     * @throws SQLException throws in case of unreachable database
     */
    public void multiRowInsert(List<EmployerValueObject> data) throws SQLException {
        Statement statement = conn.createStatement();

        statement.execute("INSERT INTO employer_info (\"DepCode\", \"DepJob\", \"Description\") VALUES " + prepareMultiRowInsertQuery(data));

        statement.close();
    }

    /**
     * @param data list of parsed items
     * @throws SQLException throws in case of unreachable database
     */
    public void multiRowUpdate(List<EmployerValueObject> data) throws SQLException {
        for (EmployerValueObject item: data) {
            try ( Statement statement = conn.createStatement()){
                statement.execute("UPDATE employer_info SET \"Description\" = '" + item.getDescription() + "' WHERE \"id\" = " + item.getId());
            }
        }
    }

    /**
     * @param data list of items ids selected for deletion
     * @throws SQLException throws in case of unreachable database
     */
    public void multiRowDelete(List<Integer> data) throws SQLException {
        try (Statement statement = conn.createStatement()){
            statement.execute("DELETE FROM employer_info WHERE \"id\" IN (" + prepareMultiRowDeleteQuery(data) + ")");
        }
    }

    /**
     * @param data list of parsed items
     * @return completed multirow values string for SQL query
     */
    private String prepareMultiRowInsertQuery(List<EmployerValueObject> data) {
        StringBuilder result = new StringBuilder();

        for (EmployerValueObject item : data) {
            result.append("('")
                    .append(item.getDepCode()).append("', '")
                    .append(item.getDepJob()).append("', '")
                    .append(item.getDescription()).append("'),");
        }

        return (result.toString().isEmpty()) ? null : result.toString().replaceAll(".$", "");
    }

    /**
     * @param data list of parsed items ids
     * @return completed multirow values string for SQL query
     */
    private String prepareMultiRowDeleteQuery(List<Integer> data) {
        StringBuilder result = new StringBuilder();

        for (Integer item : data) {
            result.append(item).append(",");
        }

        return (result.toString().isEmpty()) ? null : result.toString().replaceAll(".$", "");
    }
}
