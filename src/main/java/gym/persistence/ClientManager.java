package gym.persistence;

import gym.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Client management class - contains database data manipulation methods: add, update, remove, selectAll
 */
public abstract class ClientManager {
    /**
     * Method for adding new client to database
     */
    public static final void add(Client client) {
        PreparedStatement ps = null;
        Connection connection = null;
        try {
            connection = DatabaseManager.getConnection();
            ps = connection.prepareStatement("INSERT INTO clients (name, gender, phone_no, validation_expire_date, pass) " +
                    "values (?, ?, ?, ?, ?)");
            ps.setString(1, client.getName());
            ps.setString(2, client.getGender().name());
            ps.setString(3, client.getPhoneNumber());
            ps.setDate(4, (java.sql.Date) client.getValidationExpireDate());
            ps.setString(5, client.getPass().name());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.cleanup(ps, connection);
        }
    }

    /**
     * Method for updating client in database
     */
    public static final void update(Client client) {
        PreparedStatement st = null;
        Connection connection = null;
        try {
            connection = DatabaseManager.getConnection();
            st = connection.prepareStatement("UPDATE clients SET name = ?, phone_no = ?, validation_expire_date = ?, pass = ?, WHERE id = ?");
            st.setString(1, client.getName());
            st.setString(2, client.getPhoneNumber());
            st.setDate(3, (java.sql.Date) client.getValidationExpireDate());
            st.setString(4, client.getPass().name());
            st.setLong(5, client.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.cleanup(st, connection);
        }
    }

    /**
     * Method for sorting and searching clients
     */
    public static final List<Client> selectAll(String sortBy, String order, String search) {

        List<Client> clients = new ArrayList<Client>();

        PreparedStatement st = null;
        Connection connection = null;
        try {
            connection = DatabaseManager.getConnection();
            StringBuffer sb = new StringBuffer("SELECT id, name, gender, phone_no, validation_expire_date, pass, registered_on FROM clients");

            boolean searchFilter = search != null && search.trim().length() > 0;
            if (searchFilter) {
                sb.append(" WHERE name LIKE ?");
            }

            if (sortBy != null) {
                sb.append(" ORDER BY ").append(sortBy).append(" ");
                sb.append(order != null ? order : DatabaseManager.ORDER.asc);
            }

            st = connection.prepareStatement(sb.toString());

            if (searchFilter) {
                st.setString(1, search);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String phoneNo = rs.getString("phone_no");
                Date validationExpireDate = rs.getDate("validation_expire_date");
                String pass = rs.getString("pass");
                Date registeredOn = rs.getDate("registered_on");
                clients.add(new Client(id, name, Client.Gender.valueOf(gender), phoneNo, validationExpireDate, Client.SubscriptionType.valueOf(pass), registeredOn));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.cleanup(st, connection);
        }

        return clients;
    }

    /**
     * Method for deleting client from database
     */
    public static final void remove(Long clientId) {
        PreparedStatement st = null;
        Connection connection = null;
        try {
            connection = DatabaseManager.getConnection();
            st = connection.prepareStatement("DELETE FROM clients WHERE id = ?");
            st.setLong(1, clientId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.cleanup(st, connection);
        }
    }

    /**
     * Method returning client from database based on client id
     */
    public static final Client getClient(Long clientId) {
        PreparedStatement st = null;
        Connection connection = null;
        try {
            connection = DatabaseManager.getConnection();
            st = connection.prepareStatement("SELECT * FROM clients WHERE id = ?");
            st.setLong(1, clientId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String phoneNo = rs.getString("phone_no");
                Date validationExpireDate = rs.getDate("validation_expire_date");
                String pass = rs.getString("pass");
                Date registeredOn = rs.getDate("registered_on");
                return new Client(id, name, Client.Gender.valueOf(gender), phoneNo, validationExpireDate, Client.SubscriptionType.valueOf(pass), registeredOn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseManager.cleanup(st, connection);
        }
        return null;
    }
}