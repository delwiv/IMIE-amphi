package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Checking;
import model.School;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-07-14T02:06:48")
@StaticMetamodel(Booking.class)
public class Booking_ { 

    public static volatile SingularAttribute<Booking, Integer> duration;
    public static volatile SingularAttribute<Booking, String> userComment;
    public static volatile ListAttribute<Booking, Checking> checkingList;
    public static volatile SingularAttribute<Booking, Integer> id;
    public static volatile SingularAttribute<Booking, Date> startDate;
    public static volatile SingularAttribute<Booking, School> idSchool;

}