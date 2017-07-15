package org.pa.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;

import org.pa.data.User;
import static org.pa.definitions.RequestDefinitions.JWT_ATTR_KEY;
import static org.pa.definitions.RequestDefinitions.NOT_UNIQUE_EXCEPTION;
import static org.pa.definitions.RequestDefinitions.UNEXPECTED_EXCEPTION;
import org.pa.dto.RegistrationDTO;
import org.pa.dto.UserDTO;
import org.pa.exception.UniqueException;
import org.pa.repository.UserRepository;
import org.pa.rest.message.SimpleErrorMessage;
import org.pa.rest.message.SimpleSuccessMessage;
import org.pa.utils.MailRegistration;

/**
 *
 * @author mwave
 */
@Provider
@Path("/user")
public class UserService {

    //private static final Set<User> users = Collections.synchronizedSet(new HashSet<User>());

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateUser(@Context javax.servlet.http.HttpServletRequest request, User user) {
        String subj = (String) request.getAttribute("subj");
        boolean operationStatus = UserRepository.getInstance().updatePassword(subj, user.getPassword());
        if (operationStatus == true) {
            SimpleSuccessMessage successMessage = new SimpleSuccessMessage("xx");
            return Response.ok(successMessage, MediaType.APPLICATION_JSON).build();
        } else {
           SimpleErrorMessage errorMessage = new SimpleErrorMessage(UNEXPECTED_EXCEPTION);
           return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
        }
    }

 
    @PUT
    @Path("/email")
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateSubjectName(@Context javax.servlet.http.HttpServletRequest request, User user) {
        String jwt_key = (String) request.getServletContext().getAttribute(JWT_ATTR_KEY);
        String subj = (String) request.getAttribute("subj");
        String email = user.getEmail();
        try {
            String result = UserRepository.getInstance().updateSubject(subj, email);
            SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;
		
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwt_key);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, sigAlg.getJcaName());
		//System.out.println(request.getUserPrincipal().getName());
		JwtBuilder builder = Jwts.builder()
				.setSubject(result)
				.signWith(sigAlg, signingKey);
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(result);
                userDTO.setToken(builder.compact());
                return Response.ok(userDTO, MediaType.APPLICATION_JSON).build();
                
        } catch (UniqueException ex) {
           // Logger.getLogger(UserProfileService.class.getName()).log(Level.SEVERE, null, ex);
           SimpleErrorMessage errorMessage = new SimpleErrorMessage(NOT_UNIQUE_EXCEPTION);
           return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
        }  
    }

    
    @POST
    @Path("/register")
    @Produces("application/json")
    @Consumes("application/json")
    public Response registerAccount( User user) {
        
        String email = user.getEmail();
        try {
                RegistrationDTO registation = UserRepository.getInstance().createAccount(email);
                MailRegistration.getInstance().sendRegistration(registation);
                return Response.ok(registation, MediaType.APPLICATION_JSON).build();
                
        } catch (Exception ex) {
           // Logger.getLogger(UserProfileService.class.getName()).log(Level.SEVERE, null, ex);
           SimpleErrorMessage errorMessage = new SimpleErrorMessage(NOT_UNIQUE_EXCEPTION);
           return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
        }
    }

}
