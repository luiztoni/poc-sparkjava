package br.luiz.toni.sparkjava.poc.author;

import com.google.gson.Gson;

import br.luiz.toni.sparkjava.poc.config.Controller;

import java.util.List;

public class AuthorController implements Controller {

    private final AuthorRepository repository;
    private final Gson gson;

    public AuthorController() {
        repository = new AuthorRepository();
        gson = new Gson();
    }

    public String index() {
        List<Author> authors = repository.index();
        return gson.toJson(authors);
    }

    public String read(int id) {
        Author author = repository.read(id);
        return gson.toJson(author);
    }

    public void create(String json) {
        Author author = gson.fromJson(json, Author.class);
        repository.create(author);
    }

    public void update(int id, String json) {
        Author author = gson.fromJson(json, Author.class);
        repository.update(id, author);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
