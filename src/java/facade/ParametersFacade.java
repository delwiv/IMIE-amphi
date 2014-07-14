/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Parameters;

/**
 *
 * @author louis
 */
@Stateless
public class ParametersFacade extends AbstractFacade<Parameters> {
    @PersistenceContext( unitName = "EasyBookingPU" )
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParametersFacade() {
        super( Parameters.class );
    }
    
    // the namedQuery orders by timestamp desc so the first row are current settings
    public Parameters getCurrent() {
        Parameters param = em.createNamedQuery( "Parameters.findAll", Parameters.class )
                .getResultList()
                .get( 0 );
        System.out.println( param.toString() );
        return param;
    }
    
}
