package dal;

/**
 * Created by AndersWOlsen on 09-02-2017.
 */

import java.sql.*;

public class DBConnection {
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private boolean connectionIsOpen;

    private String host, database, user, passwd;
    private int port;

    public DBConnection(String host, int port, String database, String user, String passwd) {
        this.host     = host;
        this.port     = port;
        this.database = database;
        this.user     = user;
        this.passwd   = passwd;

        this.connectionIsOpen = false;
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

    private void open() throws ConnectionNeverClosedException {
        if (connectionIsOpen) throw new ConnectionNeverClosedException("[DBConnection::open]: Database connection was never closed properly.");
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
            if (preparedStatement != null) this.preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("[DBConnection::close]: Failed to close either database connection or statement.");
            e.printStackTrace();
        }
    }

    public ResultSet query(String querySQL) {
        try {
            open();
        } catch (ConnectionNeverClosedException e) {
            System.err.print("[DBConnection::query]: " + e.getMessage());
            e.printStackTrace();
            return null;
        }

        if (connection == null) return null;

        statement = createStatement();

        ResultSet result;
        try {
            result = statement.executeQuery(querySQL);
        } catch (SQLException e) {
            System.err.print("[DBConnection::query]: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public void setPreparedInt(int index, int value) {
        if (preparedStatement == null) return;
        try {
            preparedStatement.setInt(index, value);
        } catch (SQLException e) {
            System.err.println("[DBConnection::setPreparedInt]: Unable to set int.");
            e.printStackTrace();
            return;
        }
    }

    public void setPreparedString(int index, String value) {
        if (preparedStatement == null) return;
        try {
            preparedStatement.setString(index, value);
        } catch (SQLException e) {
            System.err.println("[DBConnection::setPreparedInt]: Unable to set int.");
            e.printStackTrace();
            return;
        }
    }

    public void prepareQuery(String querySQL) {
        try {
            open();
        } catch (ConnectionNeverClosedException e) {
            System.out.println("[DBConnection::prepareQuery]: The database connection was never closed before this query was executed.");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println(preparedStatement);return;}

        try {
            preparedStatement = connection.prepareStatement(querySQL);
        } catch (SQLException e) {
            System.err.println("[DBConnection::prepareQuery]: Unable to prepare the prepared statement.");
            e.printStackTrace();
            return;
        }
    }

    public ResultSet executePreparedQuery() {
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.err.println("[DBConnection::executePreparedUpdate]: Unable to execute prepared query.");
            e.printStackTrace();
            return null;
        }
    }

    public void executePreparedUpdate() {
        try {
            if (preparedStatement == null) {
                System.err.println("[DBConnection::executePreparedUpdate]: Prepared Statement is null!");
                return;
            }
            preparedStatement.executeUpdate();
//            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.err.println("[DBConnection::executePreparedUpdate]: Unable to execute prepared query.");
            e.printStackTrace();
            return;
        }
    }

    public void update(String updateSQL) {
        try {
            open();
        } catch (ConnectionNeverClosedException e) {
            System.err.print("[DBConnection::query]: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        if (connection == null) return;

        statement = createStatement();

        ResultSet result;
        try {
            statement.executeUpdate(updateSQL);
        } catch (SQLException e) {
            System.err.print("[DBConnection::query]: " + e.getMessage());
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

    class ConnectionNeverClosedException extends Exception {
        public ConnectionNeverClosedException() {}
        public ConnectionNeverClosedException(String message) { super(message); }
    }
}
