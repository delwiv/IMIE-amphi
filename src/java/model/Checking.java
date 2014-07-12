/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author louis
 */
@Entity
@Table( name = "CHECKING" )
@XmlRootElement
@NamedQueries( {
    @NamedQuery( name = "Checking.findAll", query = "SELECT c FROM Checking c" ),
    @NamedQuery( name = "Checking.findById", query = "SELECT c FROM Checking c WHERE c.id = :id" ),
    @NamedQuery( name = "Checking.findByUserComment", query = "SELECT c FROM Checking c WHERE c.userComment = :userComment" ) } )
public class Checking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic( optional = false )
    @Column( name = "ID" )
    private Integer id;
    @Size( max = 5000 )
    @Column( name = "USER_COMMENT" )
    private String userComment;
    @JoinColumn( name = "ID_BOOKING", referencedColumnName = "ID" )
    @ManyToOne
    private Booking idBooking;
    @JoinColumn( name = "ID_SCHOOL", referencedColumnName = "ID" )
    @ManyToOne
    private School idSchool;

    public Checking() {
    }

    public Checking( Integer id ) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment( String userComment ) {
        this.userComment = userComment;
    }

    public Booking getIdBooking() {
        return idBooking;
    }

    public void setIdBooking( Booking idBooking ) {
        this.idBooking = idBooking;
    }

    public School getIdSchool() {
        return idSchool;
    }

    public void setIdSchool( School idSchool ) {
        this.idSchool = idSchool;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += ( id != null ? id.hashCode() : 0 );
        return hash;
    }

    @Override
    public boolean equals( Object object ) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if ( !( object instanceof Checking ) ) {
            return false;
        }
        Checking other = ( Checking ) object;
        if ( ( this.id == null && other.id != null ) || ( this.id != null && !this.id.equals( other.id ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Checking[ id=" + id + " ]";
    }

}
