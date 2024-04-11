package br.luiz.toni.sparkjava.poc.user;

import com.google.gson.Gson;

import br.luiz.toni.sparkjava.poc.config.Jwt;
import br.luiz.toni.sparkjava.poc.config.Password;

public class UserController {

    private final UserRepository repository;
    private final Gson gson;

    public UserController() {
        repository = new UserRepository();
        gson = new Gson();
    }

    public String login(String json) {
        User credentials = gson.fromJson(json, User.class);
        User user = repository.read(credentials.getEmail());
        String token = null;
        if (user.getEmail() != null && Password.equals(credentials.getPassword(), user.getPassword(), "salt")) {
            token = Jwt.generate(credentials);
        }
        return token;
    }

    public String register(String json) {
    	User credentials = gson.fromJson(json, User.class);
        User user = repository.read(credentials.getEmail());
        if (user.getEmail() != null) {
            return null;
        }
        credentials.setPassword(Password.encode(credentials.getPassword(), "salt"));
        repository.create(credentials);
        return Jwt.generate(credentials);
    }
}