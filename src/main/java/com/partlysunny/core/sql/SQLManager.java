package com.partlysunny.core.sql;

import com.partlysunny.core.ConsoleLogger;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class SQLManager {

    public Connection connection;
    public DatabaseMetaData metaData;

    public SQLManager(String path) {

        ConsoleLogger.console("Connecting to database " + path);

        try {
            if (SQLManager.class.getResource(path) == null) {
                throw new NullPointerException("Data file was null");
            }
            URL url = SQLManager.class.getResource(path);
            assert url != null;

            String filePath = url.toString();

            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);

            metaData = connection.getMetaData();

        } catch (Exception e) {
            ConsoleLogger.console("Unsuccessful init of a database: Error occurred");
            e.printStackTrace();
        }
    }

    /**
     * This method creates a table with the values specified.
     *
     * @param title   Title of the new table
     * @param columns The columns of the new table in the SQL format. -> [column name] [type] [options]
     */
    public void createTable(String title, String columns) {
        Statement stmt;
        String data = "CREATE TABLE IF NOT EXISTS " + title + "(" + columns + ");";
        try {
            stmt = connection.createStatement();
            stmt.execute(data);
        } catch (SQLException thrownException) {
            System.out.println("An error occurred when creating table with data: " + data);
        }
    }

    public boolean doesTableExist(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeQuery("SELECT * FROM " + tableName);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * This method inserts a row into a table.
     *
     * @param tableName The name of the table being accessed.
     * @throws SQLException This exception is thrown if an error occurs during the gathering of data
     */
    public void insert(String tableName, ArrayList<Object> values) throws SQLException {
        String sql;
        ResultSet set = null;
        PreparedStatement selectStmt = connection.prepareStatement("SELECT * from " + tableName, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        try {
            set = selectStmt.executeQuery();
        } catch (Exception e) {
            System.out.println("An error occurred while getting your table.");
        }

        assert set != null;
        ResultSetMetaData resultSetMetaData = set.getMetaData();

        StringBuilder columnNames = new StringBuilder();
        for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
            columnNames.append(resultSetMetaData.getColumnName(i + 1));
            if (i != resultSetMetaData.getColumnCount() - 1) {
                columnNames.append(", ");
            }
        }

        StringBuilder endQuestionMarks = new StringBuilder();
        for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
            endQuestionMarks.append("?");
            if (i != resultSetMetaData.getColumnCount() - 1) {
                endQuestionMarks.append(", ");
            }
        }

        sql = "INSERT INTO " + tableName + "(" + columnNames + ") VALUES(" + endQuestionMarks + ")";

        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(sql);
            assert pstmt != null;
            for (int i = 0; i < resultSetMetaData.getColumnCount(); i++) {
                if (resultSetMetaData.getColumnTypeName(i + 1).equals("TEXT")) {
                    pstmt.setString(i + 1, (String) values.get(i));
                } else if (resultSetMetaData.getColumnTypeName(i + 1).equals("INTEGER")) {
                    pstmt.setInt(i + 1, (Integer) values.get(i));
                }
            }
            pstmt.executeUpdate();
        } catch (SQLException thrownException) {
            System.out.println("Error occurred when inserting values: " + sql);
            System.out.println(thrownException.getMessage());
        }
    }

    public void deleteValue(String table, String ID) {
        String query = "DELETE FROM " + table + " WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, ID);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public ArrayList<Object> selectRowWithID(String ID, String tableName) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE id = " + ID;

        Statement stmt = connection.createStatement();
        ResultSet dataFull = stmt.executeQuery(query);

        while (dataFull.next()) {
            if (dataFull.getString("id").equals(ID)) {
                ArrayList<Object> returnedValues = new ArrayList<>();
                for (int i = 0; i < dataFull.getMetaData().getColumnCount(); i++) {
                    if (dataFull.getMetaData().getColumnTypeName(i + 1).equals("TEXT")) {
                        returnedValues.add(dataFull.getString(i + 1));
                    } else if (dataFull.getMetaData().getColumnTypeName(i + 1).equals("INTEGER")) {
                        returnedValues.add(dataFull.getInt(i + 1));
                    } else if (dataFull.getMetaData().getColumnTypeName(i + 1).equals("REAL")) {
                        returnedValues.add(dataFull.getDouble(i + 1));
                    }
                }
                return returnedValues;
            }
        }

        return null;
    }


}
