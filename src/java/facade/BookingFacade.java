/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Booking;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 *
 * @author louis
 */
@Stateless
public class BookingFacade extends AbstractFacade<Booking> {

    @PersistenceContext( unitName = "EasyBookingPersistUnit" )
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookingFacade() {
        super( Booking.class );
    }

    public List<Booking> getUncheckedBookings() {
        List<Booking> bookings = null;
        try {
            bookings = em.createNamedQuery( "Booking.findNotChecked", Booking.class )
                    .getResultList();
        } catch ( Exception ex ) {
            bookings = new ArrayList();
        }
        return bookings;
    }

    public List<Booking> findByDay( Date date ) {
        List<Booking> bookings = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
        for ( Booking b : findAll() ) {
            if ( sdf.format( date ).equals( sdf.format( b.getStartDate() ) ) ) {
                bookings.add( b );
            }
        }
        bookings.sort( new Comparator<Booking>() {

            @Override
            public int compare( Booking b1, Booking b2 ) {
                if ( b1.getStartDate().after( b2.getStartDate() ) ) {
                    return 1;
                } else if ( b1.getStartDate().before( b2.getStartDate() ) ) {
                    return -1;
                }
                return 0;

            }
        } );

        return bookings;
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = em.createNamedQuery( "Booking.findAll", Booking.class )
                .setParameter( "dateNow", new Date() )
                .getResultList();
        for ( Booking b : bookings ) {
            System.out.println( b.toString() );
        }
        return bookings;
    }
}
