/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.School;

/**
 *
 * @author louis
 */
@Stateless
public class SchoolFacade extends AbstractFacade<School> {

    @PersistenceContext( unitName = "EasyBookingPU" )
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SchoolFacade() {
        super( School.class );
    }

    public School getByName( String name ) {
        School school = ( School ) em.createNamedQuery( "School.findByName" )
                .setParameter( "name", name )
                .setMaxResults( 1 )
                .getSingleResult();
        return school;
    }

    @Override
    public List<School> findAll() {
        List<School> schools = super.findAll();
        School admin = null;
        for ( School s : schools ) {
            if ( s.getName().equals( "admin" ) ) {
                admin = s;
            }
        }
        schools.remove( admin );
        return schools;

    }
    
    public School getAdmin(){
        School admin = em.createNamedQuery( "School.findByName", School.class)
                .setParameter( "name", "admin")
                .getSingleResult();
        return admin;
    }

}
