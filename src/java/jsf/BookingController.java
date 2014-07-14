package jsf;

import facade.BookingFacade;
import facade.ParametersFacade;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import model.Booking;
import model.Checking;
import model.Parameters;
import model.School;
import org.apache.commons.lang.time.DateUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.SlideEndEvent;

@Named( "bookingController" )
@SessionScoped
public class BookingController implements Serializable {

    @EJB
    private facade.BookingFacade bookingFacade;
    @EJB
    private facade.ParametersFacade parametersFacade;
    @EJB
    private facade.CheckingFacade checkingFacade;

    private Parameters parameters;
    private List<Booking> items = null;
    private List<Booking> uncheckedItems = null;
    private Booking selected;
    private Date previousSelectedDate = null;

    public BookingController() {
    }

    public boolean isMatchingPlanning( Booking otherBooking ) {
        // start & end dates of comparing booking (we check the break between two bookings)
        Date bookedStartDate = otherBooking.getStartDate();
        Date bookedEndDate = DateUtils.addMinutes( bookedStartDate, otherBooking.getDuration() );

        // same for the one we try to create
        Date startDate = selected.getStartDate();
        Date endDate = startDate;

        if ( selected.getDuration() != null ) {
            endDate = DateUtils.addMinutes( selected.getStartDate(), selected.getDuration() );
        }
        // If startDate is during another booking
        if ( startDate.after( bookedStartDate ) && startDate.before( DateUtils.addMinutes( bookedEndDate, getParameters().getBookingBreakDuration() ) ) ) {
            return false;
        }

        // If endDate is after the beginning of another booking (it's too long)
        if ( startDate.before( bookedStartDate ) && endDate.after( bookedStartDate ) ) {
            return false;
        }
        if ( startDate.equals( bookedStartDate ) ) {
            return false;
        }
        // else everything's good concerning other bookings ;)
        return true;
    }

    public boolean isChecked( int idBooking ) {
        if ( null != checkingFacade.getByBookingId( idBooking ) ) {
            return true;
        }
        return false;
    }

    public void unCheckBooking() {

    }

    public void checkBooking( School school ) {
        Checking checking = new Checking( selected, school );
        checkingFacade.create( checking );
    }

    private void displayExistingBookings( Date selectedDate ) {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd MMM" );
        List<Booking> sameDayBookings = bookingFacade.findByDay( selectedDate );
        String message = "<h4>Existing bookings for " + sdf.format( selectedDate ) + "</h4><br />";
        for ( Booking b : sameDayBookings ) {
            message += b.getStrHours() + "<br />";
        }
        if ( sameDayBookings.isEmpty() ) {
            message = "<h4>No booking planned on " + sdf.format( selectedDate ) + "</h4>";
        }
        JsfUtil.addSuccessMessage( message );
    }

    public void onCreateSlideEnd( SlideEndEvent event ) {
        this.selected.setDuration( event.getValue() );

    }

    public void handleDateSelect( SelectEvent event ) {

        Date selectedDate = ( Date ) event.getObject();

        if ( !( null == this.previousSelectedDate ) ) {
            if ( !DateUtils.isSameDay( selectedDate, this.previousSelectedDate ) ) {
                previousSelectedDate = selectedDate;
                displayExistingBookings( selectedDate );
            }
        } else {
            this.previousSelectedDate = selectedDate;
            displayExistingBookings( selectedDate );
        }
        // We check if chosen dates are OK with time restriction and other bookings
        String message = "";
        for ( Booking b : bookingFacade.findByDay( selected.getStartDate() ) ) {
            if ( !isMatchingPlanning( b ) ) {
                message += "Conflict with " + b.getStrHours();
            }
        }
        if ( !message.isEmpty() ) {
            JsfUtil.addErrorMessage( "The booking you're trying to add doesn't respect planning (did you think about the break?) <br />" + message );
        } else {
            JsfUtil.addSuccessMessage( "Available booking ;)" );
        }

    }

    public BookingFacade getEjbFacade() {
        return bookingFacade;
    }

    public void setEjbFacade( BookingFacade bookingFacade ) {
        this.bookingFacade = bookingFacade;
    }

    public ParametersFacade getParametersFacade() {
        return parametersFacade;
    }

    public void setParametersFacade( ParametersFacade parametersFacade ) {
        this.parametersFacade = parametersFacade;
    }

    public Date getPreviousSelectedDate() {
        return previousSelectedDate;
    }

    public void setPreviousSelectedDate( Date previousSelectedDate ) {
        this.previousSelectedDate = previousSelectedDate;
    }

    public void handleSchoolSelect( javax.faces.event.AjaxBehaviorEvent event ) {
//        School school = ( School ) event.
        System.out.println( "SCHOOL : " + this.selected.getIdSchool().toString() );
//        selected.setIdSchool( school );
    }

    public Booking getSelected() {
        return selected;
    }

    public void setSelected( Booking selected ) {
        this.selected = selected;
    }

    public Parameters getParameters() {
        if ( null == parameters ) {
            parameters = parametersFacade.getCurrent();
        }
        return parameters;
    }

    public void setParameters( Parameters parameters ) {
        this.parameters = parameters;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BookingFacade getFacade() {
        return bookingFacade;
    }

    public Booking prepareCreate() {
        selected = new Booking();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        System.out.println( "Booking to be created : " + selected.toString() );
        persist( PersistAction.CREATE, ResourceBundle.getBundle( "/Bundle" ).getString( "BookingCreated" ) );
        if ( !JsfUtil.isValidationFailed() ) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist( PersistAction.UPDATE, ResourceBundle.getBundle( "/Bundle" ).getString( "BookingUpdated" ) );
    }

    public void destroy() {
        persist( PersistAction.DELETE, ResourceBundle.getBundle( "/Bundle" ).getString( "BookingDeleted" ) );
        if ( !JsfUtil.isValidationFailed() ) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Booking> getItems() {
        if ( items == null ) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<Booking> getUncheckedItems() {
        if ( uncheckedItems == null ) {
            uncheckedItems = getFacade().getUncheckedBookings();
        }
        return uncheckedItems;
    }

    private void persist( PersistAction persistAction, String successMessage ) {
        if ( selected != null ) {
            setEmbeddableKeys();
            try {
                if ( persistAction != PersistAction.DELETE ) {
                    getFacade().edit( selected );
                } else {
                    getFacade().remove( selected );
                }
                JsfUtil.addSuccessMessage( successMessage );
            } catch ( EJBException ex ) {
                String msg = "";
                Throwable cause = ex.getCause();
                if ( cause != null ) {
                    msg = cause.getLocalizedMessage();
                }
                if ( msg.length() > 0 ) {
                    JsfUtil.addErrorMessage( msg );
                } else {
                    JsfUtil.addErrorMessage( ex, ResourceBundle.getBundle( "/Bundle" ).getString( "PersistenceErrorOccured" ) );
                }
            } catch ( Exception ex ) {
                Logger.getLogger( this.getClass().getName() ).log( Level.SEVERE, null, ex );
                JsfUtil.addErrorMessage( ex, ResourceBundle.getBundle( "/Bundle" ).getString( "PersistenceErrorOccured" ) );
            }
        }
    }

    public Booking getBooking( java.lang.Integer id ) {
        return getFacade().find( id );
    }

    public List<Booking> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Booking> getItemsAvailableSelectOne() {
        return getFacade().findAll();

    }

    @FacesConverter( forClass = Booking.class )
    public static class BookingControllerConverter implements Converter {

        @Override
        public Object getAsObject( FacesContext facesContext, UIComponent component, String value ) {
            if ( value == null || value.length() == 0 ) {
                return null;
            }
            BookingController controller = ( BookingController ) facesContext.getApplication().getELResolver().
                    getValue( facesContext.getELContext(), null, "bookingController" );
            return controller.getBooking( getKey( value ) );
        }

        java.lang.Integer getKey( String value ) {
            java.lang.Integer key;
            key = Integer.valueOf( value );
            return key;
        }

        String getStringKey( java.lang.Integer value ) {
            StringBuilder sb = new StringBuilder();
            sb.append( value );
            return sb.toString();
        }

        @Override
        public String getAsString( FacesContext facesContext, UIComponent component, Object object ) {
            if ( object == null ) {
                return null;
            }
            if ( object instanceof Booking ) {
                Booking o = ( Booking ) object;
                return getStringKey( o.getId() );
            } else {
                Logger.getLogger( this.getClass().getName() ).log( Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[] { object, object.getClass().getName(), Booking.class.getName() } );
                return null;
            }
        }

    }
}
