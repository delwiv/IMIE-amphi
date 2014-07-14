/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Checking;

/**
 *
 * @author louis
 */
@Stateless
public class CheckingFacade extends AbstractFacade<Checking> {

    @PersistenceContext( unitName = "EasyBookingPU" )
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CheckingFacade() {
        super( Checking.class );
    }

    public Checking getByName( String name ) {
        Checking school = ( Checking ) em.createNamedQuery( "Checking.findByName" )
                .setParameter( "name", name )
                .setMaxResults( 1 )
                .getSingleResult();
        return school;
    }

    public Checking getByBookingId( int idBooking ) {
        try {
        Checking checking = em.createNamedQuery( "Checking.findByBooking", Checking.class )
                .setParameter( "idBooking", idBooking )
                .getResultList()
                .get( 0 );
        return checking;
        } catch(Exception ex){
            return null;
        }
    }

}
