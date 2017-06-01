/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.service;

import java.util.Collection;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import org.pa.data.KeywordValue;
import org.pa.dto.SearchResultDTO;
import org.pa.repository.BusinessConceptRepository;
import org.pa.repository.KeywordRepository;
import org.pa.repository.PartnerServiceRepository;
import org.pa.repository.ProvidedServiceRepository;

/**
 *
 * @author mwave
 */
@Provider
@Path("/keyword")
public class KeywordValueService extends CommonService {
    
    @GET
    @Produces({"application/json"})
    public Collection<KeywordValue> getAllKeywordValues() {
        List keywordList = KeywordRepository.getInstance().findAll();
        return keywordList;
    }

    /**
     * Gets a list keywords (user's provided services) for the currently
     * logged in user
     * @return 
     */
    @GET
    @Path("/provided")
    @Produces({"application/json"})
    public Collection<KeywordValue> getProvidedServices(@Context javax.servlet.http.HttpServletRequest request) {
        String subjectName = getSubjectName(request);
        List keywordList = ProvidedServiceRepository.getInstance().getUserProvidedServices(subjectName);
        return keywordList;
    }
    
    @POST
    @Path("/provided/new")
    @Produces("application/json")
    @Consumes("application/json")
    public KeywordValue addProvidedService(@Context javax.servlet.http.HttpServletRequest request,String value) {
        String subjectName = getSubjectName(request);
        return ProvidedServiceRepository.getInstance().addUserProvided(subjectName, value);
    }
    
    @POST
    @Path("/provided")
    @Produces("application/json")
    @Consumes("application/json")
    public KeywordValue addProvidedServiceRelationship(@Context javax.servlet.http.HttpServletRequest request, Long id) {
        String subjectName = getSubjectName(request);
        return ProvidedServiceRepository.getInstance().addUserProvided(subjectName,id);
    }
    
    @DELETE
    @Path("/provided/{id}")
    @Produces("application/json")
    public Long deleteProvidedService(@Context javax.servlet.http.HttpServletRequest request, @PathParam("id") Long id) {
        String subjectName = getSubjectName(request);
        return ProvidedServiceRepository.getInstance().deleteUserProvided(subjectName,id);
    } 
    
    @GET
    @Path("/bus")
    @Produces({"application/json"})
    public Collection<KeywordValue> getUserBusConcepts(@Context javax.servlet.http.HttpServletRequest request) {
        String subjectName = getSubjectName(request);
        List keywordList = BusinessConceptRepository.getInstance().getUserBusConcepts(subjectName);
        return keywordList;
    }
    
    @POST
    @Path("/bus/new")
    @Produces("application/json")
    @Consumes("application/json")
    public KeywordValue addBusConcept(@Context javax.servlet.http.HttpServletRequest request, String value) {
        String subjectName = getSubjectName(request);
        return BusinessConceptRepository.getInstance().addUserBusConcept(subjectName,value);
    }
    
    @POST
    @Path("/bus")
    @Produces("application/json")
    @Consumes("application/json")
    public KeywordValue addBusConceptRelationship(@Context javax.servlet.http.HttpServletRequest request, Long id) {
        String subjectName = getSubjectName(request);
        return BusinessConceptRepository.getInstance().addUserBusConcept(subjectName,id);
    }
    
    @DELETE
    @Path("/bus/{id}")
    @Produces("application/json")
    public Long deleteBusConcept(@Context javax.servlet.http.HttpServletRequest request, @PathParam("id") Long id) {
        String subjectName = getSubjectName(request);
        return BusinessConceptRepository.getInstance().deleteUserBusConcept(subjectName,id);
    }
    
    /* partner services **************/
    @GET
    @Path("/partner")
    @Produces({"application/json"})
    public Collection<KeywordValue> getPartnerServices(@Context javax.servlet.http.HttpServletRequest request) {
        String subjectName = getSubjectName(request);
        List keywordList = PartnerServiceRepository.getInstance().getPartnerServices(subjectName);
        return keywordList;
    }
    
    @POST
    @Path("/partner/new")
    @Produces("application/json")
    @Consumes("application/json")
    public KeywordValue addPartnerService(@Context javax.servlet.http.HttpServletRequest request,String value) {
        String subjectName = getSubjectName(request);
        return PartnerServiceRepository.getInstance().addPartnerService(subjectName, value);
    }
    
    @POST
    @Path("/partner")
    @Produces("application/json")
    @Consumes("application/json")
    public KeywordValue addPartnerService(@Context javax.servlet.http.HttpServletRequest request, Long id) {
        String subjectName = getSubjectName(request);
        return PartnerServiceRepository.getInstance().addPartnerService(subjectName,id);
    }
    
    @DELETE
    @Path("/partner/{id}")
    @Produces("application/json")
    public Long deletePartnerService(@Context javax.servlet.http.HttpServletRequest request, @PathParam("id") Long id) {
        String subjectName = getSubjectName(request);
        return PartnerServiceRepository.getInstance().deletePartnerService(subjectName,id);
    }
    
    @GET
    @Path("/search")
    @Produces("application/json")
    @Consumes("application/json")
    public List<SearchResultDTO> search(@Context javax.servlet.http.HttpServletRequest request) {
        String subjectName = getSubjectName(request);
        return KeywordRepository.getInstance().search(subjectName);
    }
    
    @GET
    @Path("/search/bus")
    @Produces("application/json")
    @Consumes("application/json")
    public List<SearchResultDTO> searchBusConcepts(@Context javax.servlet.http.HttpServletRequest request) {
        String subjectName = getSubjectName(request);
        return KeywordRepository.getInstance().searchBusConcepts(subjectName);
    }
    
    
}
