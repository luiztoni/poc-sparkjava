package br.luiz.toni.sparkjava.poc.book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.luiz.toni.sparkjava.poc.config.Repository;

public class BookRepository extends Repository<Book> {
	
	private static final Logger LOGGER = Logger.getLogger("br.luiz.toni.sparkjava.demo.book.BookRepository");
	
    public BookRepository() {
        super();
    }

    @Override
    public void create(Book book) {
        String insert = "INSERT INTO BOOKS(NAME, DESCRIPTION, AUTHOR_ID) VALUES(?, ?, ?);";
        try {
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, book.getName());
            statement.setString(2, book.getDescription());
            statement.setInt(3, book.getAuthorId());
            statement.executeUpdate();
            connection.commit();
        } catch (Exception exception) {
        	LOGGER.log(Level.SEVERE, exception.getLocalizedMessage(), exception);
        }
    }

    @Override
    public Book read(int id) {
        Book book = new Book();
        String select = "SELECT * FROM BOOKS WHERE ID = ?;";
        try {
        	PreparedStatement statement = connection.prepareStatement(select);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery(select);
            if (result.next()) {
                book.setName(result.getString("NAME"));
                book.setDescription(result.getString("DESCRIPTION"));
                book.setAuthorId(result.getInt("AUTHOR_ID"));
                book.setId(result.getInt("ID"));
            }
        } catch (Exception exception) {
        	LOGGER.log(Level.SEVERE, exception.getLocalizedMessage(), exception);
        }
        return book;
    }

    @Override
    public void update(int id, Book book) {
        String update = "UPDATE BOOKS SET NAME = ?, DESCRIPTION = ?, AUTHOR_ID = ? WHERE ID = ?;";
        try {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, book.getName());
            statement.setString(2, book.getDescription());
            statement.setInt(3, book.getAuthorId());
            statement.setInt(4, id);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception exception) {
        	LOGGER.log(Level.SEVERE, exception.getLocalizedMessage(), exception);
        }
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM BOOKS WHERE ID = ?;";
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
    public List<Book> index() {
        Book book = new Book();
        List<Book> books = new ArrayList<>();
        String select = "SELECT * FROM BOOKS;";
        try {
            PreparedStatement statement = connection.prepareStatement(select);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                book.setName(result.getString("NAME"));
                book.setDescription(result.getString("DESCRIPTION"));
                book.setAuthorId(result.getInt("AUTHOR_ID"));
                book.setId(result.getInt("ID"));
                books.add(book);
                book = new Book();
            }
        } catch (Exception exception) {
        	LOGGER.log(Level.SEVERE, exception.getLocalizedMessage(), exception);
        }
        return books;
    }
}