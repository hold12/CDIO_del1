package dal;

/**
 * Created by AndersWOlsen on 09-02-2017.
 */

import java.sql.*;

public class DBConnection {
    private Connection connection;
    private Statement statement;

    private String host, database, user, passwd;
    private int port;

    public DBConnection(String host, int port, String database, String user, String passwd) {
        this.host     = host;
        this.port     = port;
        this.database = database;
        this.user     = user;
        this.passwd   = passwd;
    }

    private boolean checkJDBCDriverExists() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("[DBConnection::checkJDBCDriverExists]: Missing JDBC driver in the java project.");
            e.printStackTrace();
            return false;
        }
        // MySQL JDBC driver registered
        return true;
    }

    private boolean canConnectToServer() {
        String connectionURL = "jdbc:mysql://" + host + ":" + port + "/" + database;
        try {
            connection = DriverManager.getConnection(connectionURL, user, passwd);
        } catch (SQLException e) {
            System.err.println("[DBConnection::canConnectToServer]: Connection failed! Check out in the console!");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void open() {
        if (!checkJDBCDriverExists()) return;
        if (!canConnectToServer()) return;

        if (connection != null)
            System.out.println("[DBConnection::open]: Connection established.");
        else
            System.err.println("[DBConnection::open]: Failed to make a connection to the database server.");
    }

    public void close() {
        try {
            if (connection != null) this.connection.close();
            if (statement != null)  this.statement.close();
        } catch (SQLException e) {
            System.err.println("[DBConnection::close]: Failed to close either database connection or statement.");
            e.printStackTrace();
            return;
        }
    }

    public ResultSet query(String querySQL) {
        open();
        if (connection == null) return null;

        statement = createStatement();

        ResultSet result;
        try {
            result = statement.executeQuery(querySQL);
        } catch (SQLException e) {
            System.err.println("[DBConnection::query]: Failed to execute SQL.");
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public void update(String updateSQL) {
        open();
        if (connection == null) return;

        statement = createStatement();

        try {
            statement.executeUpdate(updateSQL);
        } catch(SQLException e) {
            System.err.println("[DBConnection::update]: Failed to insert/update record.");
            e.printStackTrace();
            return;
        }
    }

    private Statement createStatement() {
        Statement stmt;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            System.err.println("[DBConnection::createStatement]: Failed to create statement.");
            e.printStackTrace();
            return null;
        }
        return stmt;
    }
}
