package br.luiz.toni.sparkjava.poc;

import static spark.Spark.*;
import static br.luiz.toni.sparkjava.poc.config.Jwt.*;

import br.luiz.toni.sparkjava.poc.author.AuthorController;
import br.luiz.toni.sparkjava.poc.book.BookController;
import br.luiz.toni.sparkjava.poc.config.CorsFilter;
import br.luiz.toni.sparkjava.poc.config.Role;
import br.luiz.toni.sparkjava.poc.user.UserController;

public class App {

    public static void main(String... args) {

        port(8080);

        CorsFilter.apply();

        before((request, response) -> response.type("application/json"));

        before(((request, response) -> {
            String method =  request.requestMethod();
            String path = request.pathInfo();
            if (method.equals("DELETE") && path.contains("books")) {
                String token = request.headers("Authorization");
                if (!isValid(token.replace("Bearer ", ""))) {
                    halt(401, "Unauthorized");
                }
            }
        }));

        get("/" ,((request, response) -> {
             response.redirect("/books");
             return response;
        }));

        path("books", () -> {
            final BookController controller = new BookController();

            get("", (request, response) -> {
               response.status(200);
               response.body(controller.index());
               return response;
            });

            post("", ((request, response) -> {
                if (hasRoles(request.headers("Authorization"), new Role[]{Role.ADMIN})) {
                    controller.create(request.body());
                    response.status(201);
                    response.body("created");
                    return response;
                } else {
                    response.status(401);
                    response.body("Unauthorized");
                }
                return response;
            }));

            put("/:id", ((request, response) -> {
                if (hasRoles(request.headers("Authorization"), new Role[]{Role.ADMIN})) {
                    controller.update(Integer.parseInt(request.params("id")), request.body());
                    response.status(200);
                    response.body("ok");
                } else {
                    response.status(401);
                    response.body("Unauthorized");
                }
                return response;
            }));

            delete("/:id",((request, response) -> {
                if (hasRoles(request.headers("Authorization"), new Role[]{Role.ADMIN})) {
                    controller.delete(Integer.parseInt(request.params("id")));
                    response.status(200);
                    response.body("ok");
                } else {
                    response.status(401);
                    response.body("Unauthorized");
                }
                return response;
            }));

            get("/:id", ((request, response) -> {
                if (hasRoles(request.headers("Authorization"), new Role[]{Role.BASIC})) {
                    response.status(200);
                    response.body(controller.read(Integer.parseInt(request.params("id"))));
                } else {
                    response.status(401);
                    response.body("Unauthorized");
                }
                return response;
            }));
        });

        path("authors", () -> {
            final AuthorController controller = new AuthorController();

            get("", (request, response) -> {
                response.status(200);
                response.body(controller.index());
                return response;
            });

            post("", ((request, response) -> {
                controller.create(request.body());
                response.status(201);
                response.body("created");
                return response;
            }));

            put("/:id", ((request, response) -> {
                controller.update(Integer.parseInt(request.params("id")), request.body());
                response.status(200);
                response.body("ok");
                return response;
            }));

            delete("/:id",((request, response) -> {
                controller.delete(Integer.parseInt(request.params("id")));
                response.status(200);
                response.body("ok");
                return response;
            }));

            get("/:id", ((request, response) -> {
                response.status(200);
                response.body(controller.read(Integer.parseInt(request.params("id"))));
                return response;
            }));
        });

        post("login", (request, response) -> {
            UserController controller = new UserController();
            String token = controller.login(request.body());
            if (token == null) {
                halt(401, "Unauthorized");
            }
            response.status(200);
            response.header("Authorization", "Bearer "+token);
            response.body("ok");
            return response;
        });

        post("register", (request, response) -> {
            UserController controller = new UserController();
            String token = controller.register(request.body());
            if (token == null) {
                halt(401, "Unauthorized");
            }
            response.status(200);
            response.header("Authorization", "Bearer "+token);
            response.body("ok");
            return response;
        });
    }
}