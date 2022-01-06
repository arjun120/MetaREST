package com.arjun.metarest.resource;

import com.arjun.metarest.dao.UserDAO;
import com.arjun.metarest.domain.Entity;
import com.arjun.metarest.domain.EntityMetaData;
import com.arjun.metarest.domain.User;
import com.arjun.metarest.domain.UserDetails;
import com.arjun.metarest.service.EntityService;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.dropwizard.auth.Auth;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/entity")
@Produces(MediaType.APPLICATION_JSON)

public class EntityResource {

    private final EntityService entityService;
    private final UserDAO userdao;

    public EntityResource(EntityService entityService, UserDAO userdao) {
        this.entityService = entityService;
        this.userdao = userdao;
    }

    @POST
    @Path("/signup")
    public UserDetails signUp(@Valid UserDetails userDetails) {
        userdao.insertUser(userDetails);
        return userDetails;
}

    @PermitAll
    @GET
    @Timed
    public Response getEntitys(@Auth User user) {
        List<Entity> entities = entityService.getEntities();
        
        List<EntityMetaData> responselist = new ArrayList<>();
        if(entities.isEmpty())
            return Response.ok(entities).build();
        for (int i = 0; i < entities.size(); i++) {
            EntityMetaData entityMetaData = new EntityMetaData(entities.get(i).getId(), entities.get(i).getName(), entities.get(i).getDataSchema());
            responselist.add(entityMetaData);
        }
        return Response.ok(responselist).build();
    }

    @RolesAllowed({ "USER" })
    @GET
    @Timed
    @Path("{id}")
    public Response getEntity(@PathParam("id") final int id, @Auth User user) {
        return Response.ok(entityService.getEntity(id).getData()).build();
    }

    @RolesAllowed({ "ADMIN" })
    @POST
    @Timed
    public Response createEntity(@NotNull @Valid final InputStream entity, @Auth User user) throws JsonParseException, JsonMappingException, IOException {
        Map<String, Object> response = new ObjectMapper().readValue(entity, HashMap.class); 
        Map<String, Object> dataSchema = (Map<String, Object>) response.get("dataSchema");
        Entity entityCreate = entityService.createEntity(new Entity(response.get("name").toString(),dataSchema.toString(), ""));
        EntityMetaData entityMetaData = new EntityMetaData(entityCreate.getId(), entityCreate.getName(), entityCreate.getDataSchema());
        return Response.ok(entityMetaData).build();
    }

    @RolesAllowed({ "ADMIN" })
    @PUT
    @Timed
    @Path("{id}")
    public Response editEntity(@NotNull @Valid final InputStream data, @PathParam("id") final int id, @Auth User user) throws JsonParseException, JsonMappingException, IOException {
                                    
        Map<String, Object> response = new ObjectMapper().readValue(data, HashMap.class);
        
        Entity entity = new Entity(entityService.getEntity(id).getName(), entityService.getEntity(id).getDataSchema(), entityService.getEntity(id).getData());
        entity.setId(id);
        entity.setData(entity.getData() + "\n" + response.toString());
        System.out.println(entity);
        entityService.editEntity(entity);
        return Response.ok(response).build();
    }

    @RolesAllowed({ "ADMIN" })
    @DELETE
    @Timed
    @Path("{id}")
    public Response deleteEntity(@PathParam("id") final int id, @Auth User user) {
        Map<String, String> response = new HashMap<>();
        response.put("status", entityService.deleteEntity(id));
        return Response.ok(response).build();
    }
}

