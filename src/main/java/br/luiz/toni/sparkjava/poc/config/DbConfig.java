package br.luiz.toni.sparkjava.poc.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public final class DbConfig {
	
	private DbConfig() {}
	
    public static Connection getConnection() {
        Properties properties = new Properties();
        InputStream input;

        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            input = classLoader.getResourceAsStream("META-INF/connection.properties");
            properties.load(input);
            String user = properties.getProperty("user");
            String password =  properties.getProperty("password");

            String url = properties.getProperty("url");
            url = "jdbc:postgresql://"+url;

            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            assert input != null;
            input.close();
            return connection;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}