package br.luiz.toni.sparkjava.poc.user;

import java.util.List;

import br.luiz.toni.sparkjava.poc.config.Role;

public class User {
    private String email;

    private String password;

    private List<Role> roles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
