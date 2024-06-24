package org.acme;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import java.util.List;
import org.acme.entity.User;
import org.acme.service.UserService;

@Path("/users")
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Produces(APPLICATION_JSON)
    public List<User> getAllUsers() {
        return User.listAll();
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public User getUserById(@PathParam("id") long id) {
        return User.findById(id);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Transactional
    public User createUser(User user) {
        User.persist(user);
        return user;
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public User updateUser(User user, @PathParam("id") long id) {
        return userService.updateUser(id, user);
    }

    @DELETE
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public Long deleteUser(@PathParam("id") long id) {
        return userService.deleteUser(id);
    }

}
