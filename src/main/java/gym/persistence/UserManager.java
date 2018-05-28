package gym.persistence;

import gym.model.User;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User management class
 */
public abstract class UserManager {

    final static Logger logger = Logger.getLogger(UserManager.class);

    public static final User getUser(String logname, String password) {
        PreparedStatement st = null;
        Connection connection = null;
        try {
            connection = DatabaseManager.getConnection();
            st = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            st.setString(1, logname);

            try {
                st.setString(2, hashPassword(password));
            } catch (NoSuchAlgorithmException e) {
                logger.error("Failed computing password hash", e);
            }
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                return new User(id, name, logname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.cleanup(st, connection);
        }
        return null;
    }

    /**
     * Method for encoding user's password
     */
    private static final String hashPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();

        StringBuilder sb = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}