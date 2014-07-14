/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import facade.SchoolFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import model.School;

/**
 *
 * @author louis
 */
@Named( "loginManager" )
@ManagedBean
@SessionScoped
public class LoginManager implements Serializable {

    @EJB
    private SchoolFacade ejbFacade;

    private String inputName;
    private String inputPasswd;
    private School loggedSchool;
    private boolean logged;
    private boolean admin;
    List<School> listLoggedSchool;

    public String login() {
        School school = null;
        try {
            school = ejbFacade.getByName( inputName );
        } catch ( Exception ex ) {

        }
        return checkLogin( school );
    }

    private String checkLogin( School school ) {
        if ( null == school ) {
            return "loop";
        }
        if ( school.getPassword().equals( inputPasswd ) ) {
            this.loggedSchool = school;
            this.logged = true;
            if ( school.getName().equals( "admin" ) ) {
                this.admin = true;
                return "admin";
            }
        }
        return "loop";
    }

    public List<School> getListLoggedSchool() {
        List<School> school = new ArrayList();
        school.add( loggedSchool );
        return school;
    }

    public String logout() {
        this.loggedSchool = null;
        this.admin = false;
        this.logged = false;
        return "loop";
    }

    public LoginManager() {
    }

    public SchoolFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade( SchoolFacade ejbFacade ) {
        this.ejbFacade = ejbFacade;
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

    public void setListLoggedSchool( List<School> listLoggedSchool ) {
        this.listLoggedSchool = listLoggedSchool;
    }

    public School getLoggedSchool() {
        return loggedSchool;
    }

    public void setLoggedSchool( School loggedSchool ) {
        this.loggedSchool = loggedSchool;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged( boolean logged ) {
        this.logged = logged;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin( boolean admin ) {
        this.admin = admin;
    }

}
