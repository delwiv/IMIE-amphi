package jsf;

import model.School;
import jsf.util.JsfUtil;
import jsf.util.JsfUtil.PersistAction;
import facade.SchoolFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named( "schoolController" )
@SessionScoped
public class SchoolController implements Serializable {

    @EJB
    private facade.SchoolFacade ejbFacade;
    private List<School> items = null;
    private School selected;
    private boolean logged;
    private String inputName;
    private String inputPasswd;

    public SchoolController() {
    }

    public String login() {
        try {
            School school = ejbFacade.getByName( inputName );
        } catch ( Exception ex ) {
            System.err.println( ex.getMessage() );
        }

        return "loop";
    }

    public String logout() {

        return "loop";
    }

    public School getSelected() {
        return selected;
    }

    public void setSelected( School selected ) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SchoolFacade getFacade() {
        return ejbFacade;
    }

    public School prepareCreate() {
        selected = new School();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist( PersistAction.CREATE, ResourceBundle.getBundle( "/Bundle" ).getString( "SchoolCreated" ) );
        if ( !JsfUtil.isValidationFailed() ) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist( PersistAction.UPDATE, ResourceBundle.getBundle( "/Bundle" ).getString( "SchoolUpdated" ) );
    }

    public void destroy() {
        persist( PersistAction.DELETE, ResourceBundle.getBundle( "/Bundle" ).getString( "SchoolDeleted" ) );
        if ( !JsfUtil.isValidationFailed() ) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<School> getItems() {
        if ( items == null ) {
            items = getFacade().findAll();
        }
        return items;
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

    public School getSchool( java.lang.Integer id ) {
        return getFacade().find( id );
    }

    public List<School> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<School> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public SchoolFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade( SchoolFacade ejbFacade ) {
        this.ejbFacade = ejbFacade;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged( boolean logged ) {
        this.logged = logged;
    }

    @FacesConverter( forClass = School.class )
    public static class SchoolControllerConverter implements Converter {

        @Override
        public Object getAsObject( FacesContext facesContext, UIComponent component, String value ) {
            if ( value == null || value.length() == 0 ) {
                return null;
            }
            SchoolController controller = ( SchoolController ) facesContext.getApplication().getELResolver().
                    getValue( facesContext.getELContext(), null, "schoolController" );
            return controller.getSchool( getKey( value ) );
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
            if ( object instanceof School ) {
                School o = ( School ) object;
                return getStringKey( o.getId() );
            } else {
                Logger.getLogger( this.getClass().getName() ).log( Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[] { object, object.getClass().getName(), School.class.getName() } );
                return null;
            }
        }

    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName( String inputName ) {
        this.inputName = inputName;
    }

    public String getInputPasswd() {
        return inputPasswd;
    }

    public void setInputPasswd( String inputPasswd ) {
        this.inputPasswd = inputPasswd;
    }

}
