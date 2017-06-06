/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.service;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.pa.dto.PartnerProposalDTO;
import org.pa.dto.ProposalDTO;
import org.pa.repository.ProposalRepository;

import org.pa.repository.ProvidedServiceRepository;
import org.pa.rest.message.SimpleErrorMessage;

/**
 *
 * @author mwave
 */
@Provider
@Path("/proposal")
public class ProposalService extends CommonService {
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    public Response getMyProposals(@Context javax.servlet.http.HttpServletRequest request, ProposalDTO proposalDTO) {
        String subj = (String) request.getAttribute("subj");
        List<ProposalDTO> list;
        try {   
            list = ProposalRepository.getInstance().getMyProposals(subj);
        } catch (Exception ex) {
           // Logger.getLogger(UserProfileService.class.getName()).log(Level.SEVERE, null, ex);
           SimpleErrorMessage errorMessage = new SimpleErrorMessage(ex.getMessage());
           return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
        }
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }
    
    @GET
    @Path("/partner")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getPartnerProposals(@Context javax.servlet.http.HttpServletRequest request, ProposalDTO proposalDTO) {
        String subj = (String) request.getAttribute("subj");
        List<PartnerProposalDTO> list;
        try {   
            list = ProposalRepository.getInstance().getPartnerProposals(subj);
        } catch (Exception ex) {
           SimpleErrorMessage errorMessage = new SimpleErrorMessage(ex.getMessage());
           return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
        }
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }
    
     
    @GET
    @Path("/public")
    @Produces("application/json")
    @Consumes("application/json")
    public Response getPublicProposals(@Context javax.servlet.http.HttpServletRequest request, ProposalDTO proposalDTO) {
        String subj = (String) request.getAttribute("subj");
        List<PartnerProposalDTO> list;
        try {   
            list = ProposalRepository.getInstance().getPublicProposals(subj);
        } catch (Exception ex) {
           SimpleErrorMessage errorMessage = new SimpleErrorMessage(ex.getMessage());
           return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
        }
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }
    
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addNewProposal(@Context javax.servlet.http.HttpServletRequest request, ProposalDTO proposalDTO) {
        String subj = (String) request.getAttribute("subj");
        ProposalDTO result;
        try {
            //String message = proposalDTO.getMessage();
           // List<Long> partners = proposalDTO.getPartners();
            result = ProposalRepository.getInstance().addProposal(subj, proposalDTO);
        } catch (Exception ex) {
           SimpleErrorMessage errorMessage = new SimpleErrorMessage(ex.getMessage());
           return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
        }
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }
    
    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response editProposal(@Context javax.servlet.http.HttpServletRequest request, ProposalDTO proposalDTO) {
        String subj = (String) request.getAttribute("subj");
        ProposalDTO result;
        try {
            result = ProposalRepository.getInstance().updateProposal(subj, proposalDTO);
        } catch (Exception ex) {
           SimpleErrorMessage errorMessage = new SimpleErrorMessage(ex.getMessage());
           return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
        }
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }
    
    @DELETE
    @Path("{id}")
    @Produces("application/json")
    //@Consumes("application/json")
    public Response deleteProposal(@Context javax.servlet.http.HttpServletRequest request, @PathParam("id") Long id) {
        String subj = (String) request.getAttribute("subj");
        Long result;
        try {
            result = ProposalRepository.getInstance().deleteProposal(subj, id);
        } catch (Exception ex) {
           SimpleErrorMessage errorMessage = new SimpleErrorMessage(ex.getMessage());
           return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
        }
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }
    
    @DELETE
    @Path("/partner/{id}")
    @Produces("application/json")
    //@Consumes("application/json")
    public Response dismissPartnerProposal(@Context javax.servlet.http.HttpServletRequest request, @PathParam("id") Long id) {
        String subj = (String) request.getAttribute("subj");
        Long result;
        try {
            result = ProposalRepository.getInstance().dismissPartnerProposal(subj, id);
        } catch (Exception ex) {
           SimpleErrorMessage errorMessage = new SimpleErrorMessage(ex.getMessage());
           return Response.status(Response.Status.NOT_ACCEPTABLE).entity(errorMessage).build();
        }
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }
}
