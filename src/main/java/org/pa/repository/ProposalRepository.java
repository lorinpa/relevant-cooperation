/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.pa.data.Proposal;
import org.pa.data.ProposalPartner;
import org.pa.data.UserProfile;
import org.pa.dto.PartnerProposalDTO;
import org.pa.dto.ProposalDTO;
import org.pa.jpa.JPAUtil;

/**
 *
 * @author mwave
 */
public class ProposalRepository extends CommonRepository {
    
    private static ProposalRepository instance;
    private final SimpleDateFormat date_format = new SimpleDateFormat("MM-dd-yy");
    
    public static ProposalRepository getInstance() {
        if (instance == null) {
            instance = new ProposalRepository();
        }
        return instance;
    }
    

    private ProposalRepository() {}
 
   
    public ProposalDTO addProposal(String subjectName, ProposalDTO proposalDTO) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        Proposal proposal = new Proposal();
        proposal.setCreatedAt(new Date(System.currentTimeMillis()));
        proposal.setTitle(proposalDTO.getTitle());
        proposal.setMessage(proposalDTO.getMessage());
        proposal.setIs_private(proposalDTO.isIs_private());
        Query q = em.createQuery("select a from UserProfile a where a.user.email = ?1");
        q.setParameter(1, subjectName);
        UserProfile owner = (UserProfile) q.getSingleResult();
        proposal.setUserProfile(owner);
        em.persist(proposal);
        ProposalPartner partner;
        UserProfile userProfile;
        for (Long id: proposalDTO.getPartners()) {
            partner = new ProposalPartner();
            try {
                userProfile = em.find(UserProfile.class, id);
                partner.setUserProfile(userProfile);
                partner.setProposal(proposal);
                em.persist(partner);
            } catch(Exception e) {
                
            }         
        }
        try {
            em.getTransaction().commit();
            proposalDTO.setId(proposal.getId());
            proposalDTO.setCreatedAt(date_format.format(proposal.getCreatedAt()));
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        
        return proposalDTO;
        
    }
    
    public List<ProposalDTO> getMyProposals(String subjectName) {
          EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
          Query q = em.createQuery("select new org.pa.dto.ProposalDTO(a.id, a.title, a.message, a.createdAt, a.is_private) from Proposal a where a.userProfile.user.email = ?1");
          q.setParameter(1, subjectName);
          em.getTransaction().begin();
          List<ProposalDTO> list = q.getResultList();
          em.getTransaction().commit();
          em.close();
          return list;  
    }
    
    public List<PartnerProposalDTO> getPartnerProposals(String subjectName) {
          EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
          Query q = em.createQuery("select new org.pa.dto.PartnerProposalDTO(a.proposal.id, a.proposal.userProfile.name, a.proposal.userProfile.user.email, a.proposal.title, a.proposal.message, a.proposal.createdAt) from ProposalPartner a where a.userProfile.user.email = ?1");
          q.setParameter(1, subjectName);
          em.getTransaction().begin();
          List<PartnerProposalDTO> list = q.getResultList();
          em.getTransaction().commit();
          em.close();
          return list;  
    }
    
     public List<PartnerProposalDTO> getPublicProposals(String subjectName) {
          EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
          Query q = em.createQuery("select new org.pa.dto.PartnerProposalDTO(a.id, a.userProfile.name, a.userProfile.user.email, a.title, a.message, a.createdAt) from Proposal a where a.is_private = false and a.userProfile.user.email != ?1 order by a.createdAt");
          q.setParameter(1, subjectName);
          em.getTransaction().begin();
          List<PartnerProposalDTO> list = q.getResultList();
          em.getTransaction().commit();
          em.close();
          return list;  
    }
    
    public ProposalDTO updateProposal(String subjectName, ProposalDTO proposalDTO) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        
        em.getTransaction().begin();
        try {
            Query q = em.createQuery("select a from Proposal a where a.userProfile.user.email = ?1 and a.id= ?2");
            q.setParameter(1, subjectName);
            q.setParameter(2, proposalDTO.getId());
            Proposal p = (Proposal) q.getSingleResult(); 
            p.setTitle((proposalDTO.getTitle()));
            p.setMessage(proposalDTO.getMessage());
            p.setIs_private(proposalDTO.isIs_private());
            em.merge(p);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    
        return proposalDTO;
    }
    
     public Long  deleteProposal(String subjectName, Long id) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        
        em.getTransaction().begin();
        try {
            Query q = em.createQuery("select a from Proposal a where a.userProfile.user.email = ?1 and a.id= ?2");
            q.setParameter(1, subjectName);
            q.setParameter(2, id);
            Proposal p = (Proposal) q.getSingleResult(); 
            em.remove(p);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return id;
    }
     
    /* the client has the proposal number. we need to delete the related ProposalPartner record, NOT the proposal itself */
    public Long dismissPartnerProposal( String subjectName, Long id) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        
        em.getTransaction().begin();
        try {
            Query q = em.createQuery("select a from ProposalPartner a where a.userProfile.user.email = ?1 and a.proposal.id = ?2");
            q.setParameter(1, subjectName);
            q.setParameter(2, id);
            ProposalPartner p = (ProposalPartner) q.getSingleResult(); 
            em.remove(p);
            em.getTransaction().commit();
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return id;
    }
}
