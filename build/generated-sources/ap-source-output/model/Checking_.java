package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Booking;
import model.School;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-07-14T17:33:17")
@StaticMetamodel(Checking.class)
public class Checking_ { 

    public static volatile SingularAttribute<Checking, String> userComment;
    public static volatile SingularAttribute<Checking, Booking> idBooking;
    public static volatile SingularAttribute<Checking, Integer> id;
    public static volatile SingularAttribute<Checking, School> idSchool;

}