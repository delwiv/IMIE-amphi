package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Booking;
import model.Checking;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-07-14T02:06:48")
@StaticMetamodel(School.class)
public class School_ { 

    public static volatile SingularAttribute<School, String> password;
    public static volatile ListAttribute<School, Checking> checkingList;
    public static volatile ListAttribute<School, Booking> bookingList;
    public static volatile SingularAttribute<School, String> name;
    public static volatile SingularAttribute<School, Integer> id;

}