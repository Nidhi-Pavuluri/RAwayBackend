package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import play.Logger;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Booking {

    private final static Logger.ALogger LOGGER = Logger.of(models.Booking.class);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("bookingId")
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "homeId")
    @JsonIgnore
    private Home home;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @JsonProperty("toDate")
    public String getToDate1() {

        return DATE_FORMAT.format(toDate);
    }

    @JsonProperty("fromDate")
    public String getFromDate1() {
        return DATE_FORMAT.format(fromDate);

    }

    @JsonProperty("toDate")
    public void setToDate1(String dateString) {
        try {
            final Date date = DATE_FORMAT.parse(dateString);
            LOGGER.debug("toDate = {}", date);
            toDate = date;
        } catch (ParseException e) {
            LOGGER.error("{}", e);
        }
    }

    @JsonProperty("fromDate")
    public void setFromDate1(String dateString) {
        try {
            final Date date = DATE_FORMAT.parse(dateString);
            fromDate = date;
        } catch (ParseException e) {
            LOGGER.error("{}", e);
        }
    }


    @Basic
    @JsonIgnore
    private Date fromDate;

    @Basic
    @JsonIgnore
    private Date toDate;


    public Booking() {
    }

    public Booking(Integer bookingId, Home home, User user, Date fromDate, Date toDate, Boolean bookingStatus) {
        this.bookingId = bookingId;
        this.home = home;
        this.user = user;
        this.fromDate = fromDate;
        this.toDate = toDate;

    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

}