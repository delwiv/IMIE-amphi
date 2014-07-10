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
    
}
