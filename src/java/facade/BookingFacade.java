/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBException;
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

    public List<Booking> getUncheckedBookings() {
        try {
        List<Booking> bookings = em.createNamedQuery( "Booking.findNotChecked", Booking.class )
                .getResultList();
        for ( Booking b : bookings ) {
            System.out.println( b.toString() );
        }
        return bookings;
        }catch(Exception ex){
            return new ArrayList<Booking>();
        }
    }

//    @Override
//    public List<Booking> findAll() {
//        List<Booking> bookings = em.createNamedQuery( "Booking.findAll", Booking.class )
//                .setParameter( "dateNow", new Date() )
//                .getResultList();
//        for ( Booking b : bookings ) {
//            System.out.println( b.toString() );
//        }
//        return bookings;
//    }

}
