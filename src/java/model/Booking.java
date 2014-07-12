/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author louis
 */
@Entity
@Table( name = "BOOKING" )
@XmlRootElement
@NamedQueries( {
    @NamedQuery( name = "Booking.findAll", query = "SELECT b FROM Booking b WHERE b.startDate > :dateNow" ),
    @NamedQuery( name = "Booking.findById", query = "SELECT b FROM Booking b WHERE b.id = :id" ),
    @NamedQuery( name = "Booking.findByStartDate", query = "SELECT b FROM Booking b WHERE b.startDate = :startDate" ),
    @NamedQuery( name = "Booking.findByDuration", query = "SELECT b FROM Booking b WHERE b.duration = :duration" ),
    @NamedQuery( name = "Booking.findByUserComment", query = "SELECT b FROM Booking b WHERE b.userComment = :userComment" ),
    @NamedQuery( name = "Booking.findNotChecked", query = "SELECT b FROM Booking b JOIN Checking c ON b.id <> c.id_booking" )
//    @NamedQuery( name = "Booking.findNotChecked", query = "SELECT b FROM Booking b JOIN Checking c ON b.id <> c.id_booking" ),
} )
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic( optional = false )
    @Column( name = "ID" )
    private Integer id;
    @Column( name = "START_DATE" )
    @Temporal( TemporalType.TIMESTAMP )
    private Date startDate;
    @Column( name = "DURATION" )
    private Integer duration;
    @Size( max = 5000 )
    @Column( name = "USER_COMMENT" )
    private String userComment;
    @OneToMany( mappedBy = "idBooking" )
    private List<Checking> checkingList;
    @JoinColumn( name = "ID_SCHOOL", referencedColumnName = "ID" )
    @ManyToOne
    private School idSchool;

    public Booking() {
    }

    public Booking( Integer id ) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate( Date startDate ) {
        this.startDate = startDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration( Integer duration ) {
        this.duration = duration;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment( String userComment ) {
        this.userComment = userComment;
    }

    @XmlTransient
    public List<Checking> getCheckingList() {
        return checkingList;
    }

    public void setCheckingList( List<Checking> checkingList ) {
        this.checkingList = checkingList;
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
        if ( !( object instanceof Booking ) ) {
            return false;
        }
        Booking other = ( Booking ) object;
        if ( ( this.id == null && other.id != null ) || ( this.id != null && !this.id.equals( other.id ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Booking[ id=" + id + " ]";
    }

    public String getStrStartDate() {
        SimpleDateFormat sdf = new SimpleDateFormat( "dd MMM HH:mm" );
        try {
            return sdf.format( startDate );
        } catch ( NullPointerException ex ) {
            return sdf.format( new Date() );
        }

    }

    public String getStrDuration() {
        try {
            int hours = 0, minutes = 0;
        if ( duration >= 60 ) {
            hours = duration / 60;
            minutes = duration % 60;
        } else {
            minutes = duration;
        }

        String response = "";

        if ( hours > 0 ) {
            response += String.valueOf( hours ) + "h";
        }
        if ( minutes > 0 ) {
            response += String.valueOf( minutes );
        }
        return response;
        } catch ( Exception e ) {
            return "";
        }
        
    }

}
