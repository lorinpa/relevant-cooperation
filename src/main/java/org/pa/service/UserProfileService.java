/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import static org.pa.definitions.RequestDefinitions.NOT_UNIQUE_EXCEPTION;

import org.pa.dto.UserProfileDTO;
import org.pa.exception.UniqueException;
import org.pa.repository.UserProfileRepository;
import org.pa.rest.message.SimpleErrorMessage;


/**
 *
 * @author mwave
 */
@Provider
@Path("/profile")
public class UserProfileService {
    
    @GET
    @Produces({"application/json"})
    public UserProfileDTO getProfileData(@Context javax.servlet.http.HttpServletRequest request) {
        String subj = null;
        UserProfileDTO up = null;
        try {
             subj = (String) request.getAttribute("subj");
             up =  UserProfileRepository.getInstance().getProfileData(subj);
        } catch (Exception e) {
            
        }
        return up;
    }
    
    @PUT
    @Path("/location")
    @Produces("application/json")
    @Consumes("application/json")
    public UserProfileDTO updateUserLocation(@Context javax.servlet.http.HttpServletRequest request, UserProfileDTO up) {
        String subj = (String) request.getAttribute("subj");
        String location = up.getLocation();
        String result = UserProfileRepository.getInstance().updateLocation(subj,location);
        return up;
    }
    
    @PUT
    @Path("/name")
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateUserName(@Context javax.servlet.http.HttpServletRequest request, UserProfileDTO up) {
        String subj = (String) request.getAttribute("subj");
        String name = up.getName();
        try {
            String result = UserProfileRepository.getInstance().updateName(subj,name);
        } catch (UniqueException ex) {
           // Logger.getLogger(UserProfileService.class.getName()).log(Level.SEVERE, null, ex);
            SimpleErrorMessage errorMessage = new SimpleErrorMessage(NOT_UNIQUE_EXCEPTION);
           return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
        }
        return Response.ok(up, MediaType.APPLICATION_JSON).build();
    }
    
}
