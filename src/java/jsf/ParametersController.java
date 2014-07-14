package jsf;

import model.Parameters;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import facade.ParametersFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.event.SlideEndEvent;

@Named( "parametersController" )
@SessionScoped
public class ParametersController implements Serializable {

    @EJB
    private facade.ParametersFacade ejbFacade;
    private List<Parameters> items = null;
    private Parameters selected;
    private List<Integer> hours;

    public ParametersController() {
    }

    @PostConstruct
    private void init() {
        hours = new ArrayList();
        hours.add( 0 );
        hours.add( 1 );
        hours.add( 2 );
        hours.add( 3 );
        hours.add( 4 );
        hours.add( 5 );
        hours.add( 6 );
        hours.add( 7 );
        hours.add( 8 );
        hours.add( 9 );
        hours.add( 10 );
        hours.add( 11 );
        hours.add( 12 );
        hours.add( 13 );
        hours.add( 14 );
        hours.add( 15 );
        hours.add( 16 );
        hours.add( 17 );
        hours.add( 18 );
        hours.add( 19 );
        hours.add( 20 );
        hours.add( 21 );
        hours.add( 22 );
        hours.add( 23 );
    }

    public List<Integer> getHours() {
        return hours;
    }

    public void setHours( List<Integer> hours ) {
        this.hours = hours;
    }

    public void onMinHourSlideEnd( SlideEndEvent event ) {
        this.selected.setBookingMinHour( event.getValue() );
    }

    public void onMaxHourSlideEnd( SlideEndEvent event ) {
        this.selected.setBookingMaxHour( event.getValue() );
    }

    public void onMinDurationSlideEnd( SlideEndEvent event ) {
        this.selected.setBookingMinDuration( event.getValue() );
    }

    public void onMaxDurationSlideEnd( SlideEndEvent event ) {
        this.selected.setBookingMaxDuration( event.getValue() );
    }

    public Parameters getSelected() {
        if ( null == selected ) {
            selected = ejbFacade.getCurrent();
        }
        return selected;
    }

    public void setSelected( Parameters selected ) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ParametersFacade getFacade() {
        return ejbFacade;
    }

    public Parameters prepareCreate() {
        selected = new Parameters();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist( PersistAction.CREATE, ResourceBundle.getBundle( "/BundleParam" ).getString( "ParametersCreated" ) );
        if ( !JsfUtil.isValidationFailed() ) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist( PersistAction.UPDATE, ResourceBundle.getBundle( "/BundleParam" ).getString( "ParametersUpdated" ) );
    }

    public void destroy() {
        persist( PersistAction.DELETE, ResourceBundle.getBundle( "/BundleParam" ).getString( "ParametersDeleted" ) );
        if ( !JsfUtil.isValidationFailed() ) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Parameters> getItems() {
        List<Parameters> params = new ArrayList();
        params.add( ejbFacade.getCurrent() );

        return params;
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
                    JsfUtil.addErrorMessage( ex, ResourceBundle.getBundle( "/BundleParam" ).getString( "PersistenceErrorOccured" ) );
                }
            } catch ( Exception ex ) {
                Logger.getLogger( this.getClass().getName() ).log( Level.SEVERE, null, ex );
                JsfUtil.addErrorMessage( ex, ResourceBundle.getBundle( "/BundleParam" ).getString( "PersistenceErrorOccured" ) );
            }
        }
    }

    public Parameters getParameters( java.lang.Integer id ) {
        return getFacade().find( id );
    }

    public List<Parameters> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Parameters> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter( forClass = Parameters.class )
    public static class ParametersControllerConverter implements Converter {

        @Override
        public Object getAsObject( FacesContext facesContext, UIComponent component, String value ) {
            if ( value == null || value.length() == 0 ) {
                return null;
            }
            ParametersController controller = ( ParametersController ) facesContext.getApplication().getELResolver().
                    getValue( facesContext.getELContext(), null, "parametersController" );
            return controller.getParameters( getKey( value ) );
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
            if ( object instanceof Parameters ) {
                Parameters o = ( Parameters ) object;
                return getStringKey( o.getId() );
            } else {
                Logger.getLogger( this.getClass().getName() ).log( Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[] { object, object.getClass().getName(), Parameters.class.getName() } );
                return null;
            }
        }

    }

}
