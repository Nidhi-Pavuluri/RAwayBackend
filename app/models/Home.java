package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import play.Logger;

import javax.persistence.*;
import javax.validation.Constraint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;


@Entity
public class Home {


    private final static Logger.ALogger LOGGER = Logger.of(models.Home.class);
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public enum HouseStatus {
        PENDING,
        APPROVED
    }

    public enum PropertyType {
        APARTMENT,
        HOUSE,
        CONDO,
        SERVICEDAPARTMENTS
    }

    public enum Bool{
        TRUE,
        FALSE
    }




    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("homeId")
    private Integer homeId;

    @Basic
    @JsonProperty("propertyType")
    private PropertyType propertyType;

    @Basic
    @JsonProperty("homeName")
    private String homeName;

    @Basic
    @JsonProperty("guestCount")
    private Integer guestCount;

    @Basic
    @JsonProperty("location")
    private String location;

    @Basic
    @JsonIgnore
    private Date fromDate;

    @Basic
    @JsonIgnore
    private Date toDate;

    @Basic
    @JsonProperty("price")
    private Integer price;

    @Basic
    @JsonProperty("houseStatus")
    private HouseStatus houseStatus;


    @OneToOne(cascade = {CascadeType.ALL})
    @JsonProperty("amenities")
    private Amenities amenities;


    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @Basic
    @JsonProperty("reportFlag")
    private Integer reportFlag;



    @Basic
    @JsonProperty("deleteRequest")
    private Bool deleteRequest;

    @Transient
    @JsonProperty("imageUrls")
    private String[] imageUrls;

//    public  String toString(){
//        return "Fromdate" + fromDate ;
//    }


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
    @JsonProperty("address")
    private String address;

    @Basic
    @JsonProperty("pincode")
    private String pincode;

    @Basic
    @JsonProperty("description")
    private String description;

    @Basic
    @JsonProperty("latitude")
    private String latitude;

    @Basic
    @JsonProperty("longitude")
    private String longitude;

    @Transient
    @JsonProperty("bookingIds")
    private Integer[] bookingIds;


    @Basic
    @JsonProperty("Rating")
    private Integer Rating;



    public Home(Integer homeId, PropertyType propertyType, String homeName, Integer guestCount, String location, Date fromDate, Date toDate, Integer price, HouseStatus houseStatus, Amenities amenities, User user, Integer reportFlag, Bool deleteRequest, String[] imageUrls, String address, String pincode, String description, String latitude, String longitude, Integer[] bookingIds, Integer rating) {
        this.homeId = homeId;
        this.propertyType = propertyType;
        this.homeName = homeName;
        this.guestCount = guestCount;
        this.location = location;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.price = price;
        this.houseStatus = houseStatus;
        this.amenities = amenities;
        this.user = user;
        this.reportFlag = reportFlag;
        this.deleteRequest = deleteRequest;
        this.imageUrls = imageUrls;
        this.address = address;
        this.pincode = pincode;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bookingIds = bookingIds;
        Rating = rating;
    }

    public Home() {
    }

    public static DateFormat getDateFormat() {
        return DATE_FORMAT;
    }

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public Integer getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public HouseStatus getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(HouseStatus houseStatus) {
        this.houseStatus = houseStatus;
    }

    public Amenities getAmenities() {
        return amenities;
    }

    public void setAmenities(Amenities amenities) {
        this.amenities = amenities;
    }





    public Integer getReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(Integer reportFlag) {
        this.reportFlag = reportFlag;
    }




    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }


    public Bool getDeleteRequest() {
        return deleteRequest;
    }

    public void setDeleteRequest(Bool deleteRequest) {
        this.deleteRequest = deleteRequest;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }




    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer[] getBookingIds() {
        return bookingIds;
    }

    public void setBookingIds(Integer[] bookingIds) {
        this.bookingIds = bookingIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRating() {
        return Rating;
    }

    public void setRating(Integer rating) {
        Rating = rating;
    }
}

