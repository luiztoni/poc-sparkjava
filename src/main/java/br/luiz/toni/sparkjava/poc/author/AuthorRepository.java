package br.luiz.toni.sparkjava.poc.author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.luiz.toni.sparkjava.poc.config.Repository;


public class AuthorRepository extends Repository<Author> {
	
	private static final Logger LOGGER = Logger.getLogger("br.luiz.toni.sparkjava.demo.author.AuthorRepository");

    public AuthorRepository() {
        super();
    }

    @Override
    public void create(Author author) {
        String insert =  "INSERT INTO AUTHORS(NAME, BIOGRAPHY) VALUES(?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, author.getName());
            statement.setString(2, author.getBiography());
            statement.executeUpdate();
            connection.commit();
        } catch (Exception exception) {
        	LOGGER.log(Level.SEVERE, exception.getLocalizedMessage(), exception);
        }
    }

    @Override
    public Author read(int id) {
        Author author = new Author();
        String select = "SELECT * FROM AUTHORS WHERE ID = ?;";
        try {
        	PreparedStatement statement = connection.prepareStatement(select);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery(select);
            if (result.next()) {
                author.setId(result.getInt("ID"));
                author.setName(result.getString("NAME"));
                author.setBiography(result.getString("BIOGRAPHY"));
            }
        } catch (Exception exception) {
        	LOGGER.log(Level.SEVERE, exception.getLocalizedMessage(), exception);
        }
        return author;
    }

    @Override
    public void update(int id, Author author) {
        String update = "UPDATE AUTHORS SET NAME = ?, BIOGRAPHY = ? WHERE = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, author.getName());
            statement.setString(2, author.getBiography());
            statement.setInt(3, id);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception exception) {
        	LOGGER.log(Level.SEVERE, exception.getLocalizedMessage(), exception);
        }
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM AUTHORS WHERE ID = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception exception) {
        	LOGGER.log(Level.SEVERE, exception.getLocalizedMessage(), exception);
        }
    }

    @Override
    public List<Author> index() {
        Author author = new Author();
        List<Author> authors = new ArrayList<>();
        String select = "SELECT * FROM AUTHORS;";
        try {
            PreparedStatement statement = connection.prepareStatement(select);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                author.setId(result.getInt("ID"));
                author.setName(result.getString("NAME"));
                author.setBiography(result.getString("BIOGRAPHY"));
                authors.add(author);
                author = new Author();
            }
        } catch (Exception exception) {
        	LOGGER.log(Level.SEVERE, exception.getLocalizedMessage(), exception);
        }
        return authors;
    }

}