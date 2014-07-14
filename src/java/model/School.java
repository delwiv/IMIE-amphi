/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author louis
 */
@Entity
@Table( name = "SCHOOL" )
@XmlRootElement
@NamedQueries( {
    @NamedQuery( name = "School.findAll", query = "SELECT s FROM School s" ),
    @NamedQuery( name = "School.findById", query = "SELECT s FROM School s WHERE s.id = :id" ),
    @NamedQuery( name = "School.findByName", query = "SELECT s FROM School s WHERE s.name = :name" ), /*@NamedQuery( name = "School.findByPassword", query = "SELECT s FROM School s WHERE s.password = :password" )*/ } )
public class School implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Basic( optional = false )
    @Column( name = "ID" )
    private Integer id;
    @Size( max = 120 )
    @Column( name = "NAME" )
    private String name;
    @Size( max = 512 )
    @Column( name = "PASSWORD" )
    private String password;
    @OneToMany( mappedBy = "idSchool" )
    private List<Checking> checkingList;
    @OneToMany( mappedBy = "idSchool" )
    private List<Booking> bookingList;

    public School() {
    }

    public School( Integer id ) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    @XmlTransient
    public List<Checking> getCheckingList() {
        return checkingList;
    }

    public void setCheckingList( List<Checking> checkingList ) {
        this.checkingList = checkingList;
    }

    @XmlTransient
    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList( List<Booking> bookingList ) {
        this.bookingList = bookingList;
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
        if ( !( object instanceof School ) ) {
            return false;
        }
        School other = ( School ) object;
        if ( ( this.id == null && other.id != null ) || ( this.id != null && !this.id.equals( other.id ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "School{" + "id=" + id + ", name=" + name + ", password=" + password + ", checkingList=" + checkingList + ", bookingList=" + bookingList + '}';
    }

}
