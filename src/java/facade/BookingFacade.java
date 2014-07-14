/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
            return bookings;
        } catch ( Exception ex ) {
            return new ArrayList<Booking>();
        }
    }

    public List<Booking> findByDay( Date date ) {
        List<Booking> bookings = null;
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        bookings = em.createQuery( "SELECT b FROM Booking b WHERE FUNC('DATE', b.startDate) = FUNC('DATE', :date) ORDER BY b.startDate ASC" )
                .setParameter( "date", sdf.format( date ) )
                .getResultList();
        return bookings;
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = em.createNamedQuery( "Booking.findAll", Booking.class )
                .setParameter( "dateNow", new Date() )
                .getResultList();
        return bookings;

    }

    public List<Booking> findBySchool( int idSchool ) {
        List<Booking> bookings = em.createNamedQuery( "Booking.findByIdSchool", Booking.class )
                .setParameter( "idSchool", idSchool )
                .getResultList();

        return bookings;

    }
}
