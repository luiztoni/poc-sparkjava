package br.luiz.toni.sparkjava.poc.book;

import com.google.gson.Gson;

import br.luiz.toni.sparkjava.poc.config.Controller;

import java.util.List;

public class BookController implements Controller {

    private final BookRepository repository;
    private final Gson gson;

    public BookController() {
        repository = new BookRepository();
        gson = new Gson();
    }

    public String index() {
        List<Book> books  = repository.index();
        return gson.toJson(books);
    }

    public void create(String json) {
        Book book = gson.fromJson(json, Book.class);
        repository.create(book);
    }

    public String read(int id) {
        Book book = repository.read(id);
        return gson.toJson(book);
    }

    public void update(int id, String json) {
        Book book = gson.fromJson(json, Book.class);
        repository.update(id, book);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}