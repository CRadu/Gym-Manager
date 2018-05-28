package gym.persistence;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for managing database connection and cleaning up.
 */
public abstract class DatabaseManager {

    final static Logger logger = Logger.getLogger(DatabaseManager.class);

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creating database connection method
     */
    protected static final Connection getConnection() throws SQLException {
        return DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/gym",
                        "postgres", "postgres");
    }

    /**
     * Cleaning up - closing statement and connection
     */
    protected static final void cleanup(Statement st, Connection connection) {
        try {
            if (st != null && !st.isClosed()) {
                st.close();
            }
        } catch (SQLException e) {
            logger.error("Unable to close statement", e);
        }

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Unable to close connection", e);
        }
    }

    public enum ORDER {
        asc, desc
    }
}
