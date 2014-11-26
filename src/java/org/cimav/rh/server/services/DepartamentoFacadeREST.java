/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cimav.rh.server.services;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.cimav.rh.shared.entities.EDepartamento;

/**
 *
 * @author juan.calderon
 */
@Stateless
@Path("departamento")
@Consumes("application/json")
@Produces("application/json")
public class DepartamentoFacadeREST extends AbstractFacade<EDepartamento> {
    
    @PersistenceContext(unitName = "SigreRHEEPU")
    private EntityManager em;

    public DepartamentoFacadeREST() {
        super(EDepartamento.class);
    }

    @POST
    @Override
    public void create(EDepartamento entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    public void edit(@PathParam("id") Integer id, EDepartamento entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    public EDepartamento find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    public List<EDepartamento> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    public List<EDepartamento> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
