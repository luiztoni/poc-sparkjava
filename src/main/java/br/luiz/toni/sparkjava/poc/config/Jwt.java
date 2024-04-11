package br.luiz.toni.sparkjava.poc.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.*;

import br.luiz.toni.sparkjava.poc.user.User;


public final class Jwt {
    private static final long EXPIRATION_TIME = 60 * 60 * 1000L; // 60 minutes
    private static final String ROLES = "roles";

    private final static String JWT_SECRET_KEY = "foo_bar";

    public static String generate(User user) {
        DefaultClaims claims = new DefaultClaims();
        if (user.getEmail().contains("@admin")) {
            user.setRoles(Arrays.asList(Role.BASIC, Role.ADMIN));
        } else {
            user.setRoles(Collections.singletonList(Role.BASIC));
        }
        claims.put(ROLES, user.getRoles());
        claims.setSubject(user.getEmail());
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEY.getBytes())
                .compact();
    }

    public static boolean isValid(String token) {
        try {
            Date expiration = Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY.getBytes())
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getExpiration();
            if (expiration.after(new Date())) {
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static boolean hasRoles(String token, Role[] roles) {
        try {
            if (roles.length == 0) {
                return true;
            }
            
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY.getBytes())
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();

            @SuppressWarnings("unchecked") 
            List<String> list = (List<String>) claims.get("roles");

            List<Role> authorities = new ArrayList<>();
            for (String s : list) {
                authorities.add(Role.valueOf(s));
            }
            return authorities.stream().anyMatch(Arrays.asList(roles)::contains);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

    }
}