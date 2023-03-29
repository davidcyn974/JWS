package fr.epita.assistants.presentation.rest;


import fr.epita.assistants.presentation.rest.request.ReverseRequest;
import fr.epita.assistants.presentation.rest.response.HelloResponse;
import fr.epita.assistants.presentation.rest.response.ReverseResponse;
import org.apache.commons.lang3.NotImplementedException;

import javax.ws.rs.*;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import java.util.Objects;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/")
public class Endpoints {

    @GET
    @Path("hello/{content}")
    @Produces(APPLICATION_JSON)
    public Response hello(@PathParam("content") String content) {
        return Response.status(200).entity(new HelloResponse(content)).build();
    }

    @POST
    @Consumes(APPLICATION_JSON) // Or MediaType.APPLICATION_JSON
    @Produces(APPLICATION_JSON) // Or MediaType.APPLICATION_JSON
    @Path("reverse")
    public Response reverse(ReverseRequest request) {
        // THESE IF ARE NOT NECESSARY
        if (request == null) // request is null
            return Response.status(500).build();
        if (request.getContent() == null)
            return Response.status(404).build();
        return Response.status(200).entity(new ReverseResponse(request.getContent())).build();

    }
}
