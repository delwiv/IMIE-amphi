/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author louis
 */
@Entity
@Table( name = "PARAMETERS" )
@XmlRootElement
@NamedQueries( {
    @NamedQuery( name = "Parameters.findAll", query = "SELECT p FROM Parameters p order by timestamp desc" ),
    @NamedQuery( name = "Parameters.findById", query = "SELECT p FROM Parameters p WHERE p.id = :id" ),
    @NamedQuery( name = "Parameters.findByBookingMinHour", query = "SELECT p FROM Parameters p WHERE p.bookingMinHour = :bookingMinHour" ),
    @NamedQuery( name = "Parameters.findByBookingMaxHour", query = "SELECT p FROM Parameters p WHERE p.bookingMaxHour = :bookingMaxHour" ),
    @NamedQuery( name = "Parameters.findByBookingMaxDuration", query = "SELECT p FROM Parameters p WHERE p.bookingMaxDuration = :bookingMaxDuration" ),
    @NamedQuery( name = "Parameters.findByBookingEnableChecking", query = "SELECT p FROM Parameters p WHERE p.bookingEnableChecking = :bookingEnableChecking" ),
    @NamedQuery( name = "Parameters.findByBookingBreakDuration", query = "SELECT p FROM Parameters p WHERE p.bookingBreakDuration = :bookingBreakDuration" ) } )
public class Parameters implements Serializable {

    @Column( name = "BOOKING_MIN_DURATION" )
    private Integer bookingMinDuration;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic( optional = false )
    @Column( name = "ID" )
    private Integer id;
    @Column( name = "BOOKING_MIN_HOUR" )
    private Integer bookingMinHour;
    @Column( name = "BOOKING_MAX_HOUR" )
    private Integer bookingMaxHour;
    @Column( name = "BOOKING_MAX_DURATION" )
    private Integer bookingMaxDuration;
    @Column( name = "BOOKING_ENABLE_CHECKING" )
    private Integer bookingEnableChecking;
    @Column( name = "BOOKING_BREAK_DURATION" )
    private Integer bookingBreakDuration;
    @Column( name = "TIMESTAMP" )
    @Temporal( TemporalType.TIMESTAMP )
    private Date timestamp;

    public Parameters() {
    }

    public Parameters( Integer id ) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public Integer getBookingMinHour() {
        return bookingMinHour;
    }

    public void setBookingMinHour( Integer bookingMinHour ) {
        this.bookingMinHour = bookingMinHour;
    }

    public Integer getBookingMaxHour() {
        return bookingMaxHour;
    }

    public void setBookingMaxHour( Integer bookingMaxHour ) {
        this.bookingMaxHour = bookingMaxHour;
    }

    public Integer getBookingMaxDuration() {
        return bookingMaxDuration;
    }

    public void setBookingMaxDuration( Integer bookingMaxDuration ) {
        this.bookingMaxDuration = bookingMaxDuration;
    }

    public Integer getBookingEnableChecking() {
        return bookingEnableChecking;
    }

    public void setBookingEnableChecking( Integer bookingEnableChecking ) {
        this.bookingEnableChecking = bookingEnableChecking;
    }

    public Integer getBookingBreakDuration() {
        return bookingBreakDuration;
    }

    public void setBookingBreakDuration( Integer bookingBreakDuration ) {
        this.bookingBreakDuration = bookingBreakDuration;
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
        if ( !( object instanceof Parameters ) ) {
            return false;
        }
        Parameters other = ( Parameters ) object;
        if ( ( this.id == null && other.id != null ) || ( this.id != null && !this.id.equals( other.id ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Parameters[ id=" + id + " ]";
    }

    public Integer getBookingMinDuration() {
        return bookingMinDuration;
    }

    public void setBookingMinDuration( Integer bookingMinDuration ) {
        this.bookingMinDuration = bookingMinDuration;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( Date timestamp ) {
        this.timestamp = timestamp;
    }

}