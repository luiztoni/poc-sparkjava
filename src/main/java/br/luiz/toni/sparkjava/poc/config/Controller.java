package br.luiz.toni.sparkjava.poc.config;

public interface Controller {

    void create(String json);

    void update(int id, String json);

    void delete(int id);

    String read(int id);

    String index();
}