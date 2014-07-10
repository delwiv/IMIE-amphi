/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Booking;

/**
 *
 * @author louis
 */
@Stateless
public class BookingFacade extends AbstractFacade<Booking> {
    @PersistenceContext( unitName = "EasyBookingPU" )
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookingFacade() {
        super( Booking.class );
    }
    
}
