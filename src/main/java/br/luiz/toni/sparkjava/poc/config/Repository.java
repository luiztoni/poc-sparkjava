package br.luiz.toni.sparkjava.poc.config;

import java.sql.Connection;
import java.util.List;

public abstract class Repository<T> {
	
	protected Connection connection;
	
    public Repository() {
    	connection = DbConfig.getConnection();
	}

	public abstract void create(T model);

    public abstract T read(int id);

    public abstract void update(int id, T model);

    public abstract void delete(int id);

    public abstract List<T> index();
}