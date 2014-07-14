package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-07-14T22:25:01")
@StaticMetamodel(Parameters.class)
public class Parameters_ { 

    public static volatile SingularAttribute<Parameters, Integer> bookingEnableChecking;
    public static volatile SingularAttribute<Parameters, Integer> bookingMaxHour;
    public static volatile SingularAttribute<Parameters, Integer> id;
    public static volatile SingularAttribute<Parameters, Integer> bookingMinHour;
    public static volatile SingularAttribute<Parameters, Integer> bookingMaxDuration;
    public static volatile SingularAttribute<Parameters, Integer> bookingBreakDuration;
    public static volatile SingularAttribute<Parameters, Integer> bookingMinDuration;
    public static volatile SingularAttribute<Parameters, Date> timestamp;

}