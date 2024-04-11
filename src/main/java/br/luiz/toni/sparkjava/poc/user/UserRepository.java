package br.luiz.toni.sparkjava.poc.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.luiz.toni.sparkjava.poc.config.DbConfig;


public class UserRepository {

    private final Connection connection;
    
    private static final Logger LOGGER = Logger.getLogger("br.luiz.toni.alfa.config.auth.user.UserRepository");

    public UserRepository() {
        connection = DbConfig.getConnection();
    }

    public void create(User user) {
        String insert = "INSERT INTO USERS(EMAIL, PASSWORD) VALUES(?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            connection.commit();
        } catch (Exception exception) {
        	LOGGER.log(Level.SEVERE, "Error: "+exception.getLocalizedMessage());
        }
    }

    public User read(String email) {
        User user = new User();
        String select = "SELECT * FROM USERS WHERE EMAIL = '?' LIMIT 1;";
        try {
        	PreparedStatement statement = connection.prepareStatement(select);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery(select);
            if (result.next()) {
                user.setEmail(result.getString("EMAIL"));
                user.setPassword(result.getString("PASSWORD"));
            }
        } catch (Exception exception) {
        	LOGGER.log(Level.SEVERE, "Error: "+exception.getLocalizedMessage());
        }
        return user;
    }
}