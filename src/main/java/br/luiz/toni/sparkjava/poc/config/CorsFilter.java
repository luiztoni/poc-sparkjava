package br.luiz.toni.sparkjava.poc.config;

import spark.Filter;
import spark.Spark;

import java.util.HashMap;

public final class CorsFilter {
	
    private CorsFilter() {}

	private static final HashMap<String, String> headers = new HashMap<>();

    static {
        headers.put("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        headers.put("Access-Control-Allow-Origin", "*");
        headers.put("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, Content-Length, Accept, Origin,");
        headers.put("Access-Control-Allow-Credentials", "true");
    }

    public static void apply() {
        Filter filter = (request, response) -> headers.forEach(response::header);
        Spark.after(filter);
    }
}