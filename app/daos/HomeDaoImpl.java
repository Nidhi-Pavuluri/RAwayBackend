package daos;

import models.Home;
import play.Logger;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class HomeDaoImpl implements HomeDao {
    private final static Logger.ALogger LOGGER = Logger.of(daos.HomeDaoImpl.class);

    final JPAApi jpaApi;
    public static Home home;
    private ImageDao imageDao;
    private BookingDao bookingDao;

    @Inject
    public HomeDaoImpl(JPAApi jpaApi, ImageDao imageDao, BookingDao bookingDao) {
        this.jpaApi = jpaApi;
        this.imageDao = imageDao;
        this.bookingDao = bookingDao;
    }

    @Override
    public Home create(Home home) {
        if(null == home) {
            throw new IllegalArgumentException("Home must be provided");
        }

        jpaApi.em().persist(home);
        return home;
    }


    public Optional<Home> read(Integer Id) {

        if (null == Id) {
            throw new IllegalArgumentException("Id must be provided");
        }

        final Home home = jpaApi.em().find(Home.class, Id);
        return home != null ? Optional.of(home) : Optional.empty();

    }


    @Override
    public Home deleteadmin(Home existinghome) {

        if(null == existinghome){
            throw new IllegalArgumentException("Invalid home");
        }

        if(Home.HouseStatus.APPROVED!=existinghome.getHouseStatus() || Home.Bool.TRUE != existinghome.getDeleteRequest() || null != bookingDao.getBookingsByHomeId(existinghome.getHomeId())){
            throw new IllegalArgumentException("Home cannot be deleted");
        }
        jpaApi.em().remove(existinghome);

        return existinghome;


    }


    @Override
    public Home deleteReportadmin(Home existinghome) {

        if(null == existinghome){
            throw new IllegalArgumentException("Invalid home");
        }

        if(Home.HouseStatus.APPROVED!=existinghome.getHouseStatus() || existinghome.getReportFlag() <= 3 || ( bookingDao.getBookingsByHome(existinghome.getHomeId())!=0 )){
            throw new IllegalArgumentException("Home cannot be deleted");
        }
        jpaApi.em().remove(existinghome);

        return existinghome;


    }





    @Override
    public Home deleteuser(Home existinghome) {


        if(null == existinghome){
            throw new IllegalArgumentException("Invalid home");
        }

        LOGGER.debug("booking id is " + bookingDao.getBookingsByHomeId(existinghome.getHomeId()));
        if( bookingDao.getBookingsByHome(existinghome.getHomeId())!=0 ){
            throw new IllegalArgumentException("Home cannot be deleted");
        }

        if(Home.Bool.TRUE != existinghome.getDeleteRequest()){
            final Home newhome = deleteRequestUpdate(existinghome);
        }

        jpaApi.em().remove(existinghome);

        return existinghome;

    }

    @Override
    public Home homeReportupdate(Home existinghome) {


        if(null == existinghome){
            throw new IllegalArgumentException("Invalid home");
        }

        existinghome.setReportFlag(existinghome.getReportFlag() + 1);


        jpaApi.em().persist(existinghome);
        return existinghome;

    }

    @Override
    public Home homeStatusupdate(Home existinghome) {

        if(null == existinghome){
            throw new IllegalArgumentException("Invalid home");
        }

        existinghome.setHouseStatus(Home.HouseStatus.APPROVED);


        jpaApi.em().persist(existinghome);
        return existinghome;

    }

    @Override
    public Home deleteRequestUpdate(Home existinghome) {

        if(null == existinghome){
            throw new IllegalArgumentException("Invalid home");
        }

        existinghome.setDeleteRequest(Home.Bool.TRUE);


        jpaApi.em().persist(existinghome);
        return existinghome;

    }

    @Override
    public Collection<Home> pendingListings() {
        TypedQuery<Home> query = jpaApi.em().createQuery("SELECT h FROM Home h WHERE houseStatus= 0 and user.role = 2", Home.class);
        List<Home> homes = query.getResultList();

        return homes;
    }

    @Override
    public Collection<Home> pendingUsers() {
        TypedQuery<Home> query = jpaApi.em().createQuery("SELECT h FROM Home h WHERE user.role =1 and houseStatus= 0", Home.class);
        List<Home> homes = query.getResultList();

        return homes;
    }

    @Override
    public Collection<Home> searchByUsername(String username) {
        if(null == username) {
            throw new IllegalArgumentException("username must be provided");
        }


        TypedQuery<Home> query = jpaApi.em().createQuery("SELECT h FROM Home h where username = '" + username + "'", Home.class);
        List<Home> homes = query.getResultList();
        return homes;
    }

    @Override
    public Collection<Home> approvedlist() {
        TypedQuery<Home> query = jpaApi.em().createQuery("SELECT h FROM Home h WHERE houseStatus = 1", Home.class);
        List<Home> homes = query.getResultList();
        return homes;
    }

    @Override
    public Collection<Home> reportlist() {
        TypedQuery<Home> query = jpaApi.em().createQuery("SELECT h FROM Home h WHERE reportFlag > 3", Home.class);
        List<Home> homes = query.getResultList();
        return homes;

    }

    @Override
    public Collection<Home> filters(Home home) {

        LOGGER.debug("From Date"+home.getFromDate());
        LOGGER.debug("To date" + home.getToDate());
        LOGGER.debug("Entered");
        List<Home> homes=null;


        StringBuilder sb = new StringBuilder();
        sb.append("SELECT h from Home h WHERE houseStatus = 1 and deleteRequest = 1");

    //book status should be false and house status should be approved and delete request should be false

        try {



            if(null != home.getPropertyType()) {

                if (Home.PropertyType.APARTMENT== home.getPropertyType()) {
                    sb.append(" and propertyType = '0'");
                }
                else if (Home.PropertyType.HOUSE == home.getPropertyType()) {
                    sb.append(" and propertyType = '1'");
                }
                else if (Home.PropertyType.CONDO == home.getPropertyType()) {
                    sb.append(" and propertyType = '2'");
                }
                else{
                    sb.append(" and propertyType = '3'");
                }
            }

            if(null != home.getGuestCount()){
                sb.append(" and guestCount >= '"+home.getGuestCount()+"'");
            }
            if(null != home.getLocation()){
                sb.append(" and location = '"+home.getLocation()+"'");
            }
            if(null != home.getPrice()){
                sb.append(" and price >= '"+home.getPrice()+"'");
            }
            if(null != home.getToDate()){

                sb.append(" and toDate >= '"+home.getToDate1()+"'");
            }
            if(null != home.getFromDate()){
                sb.append(" and fromDate <= '"+home.getFromDate1()+"'");
            }




            if (null != home.getAmenities()) {


                if (null != home.getAmenities().getWifi())
                    sb.append(" and amenities.wifi = true");

                if (null != home.getAmenities().getAirConditioner())
                    sb.append(" and amenities.airConditioner = true");

                if (null != home.getAmenities().getTv())
                    sb.append(" and amenities.tv = true");
                if (null != home.getAmenities().getBreakfast())
                    sb.append(" and amenities.breakfast = true");
                if (null != home.getAmenities().getParking())
                    sb.append(" and amenities.parking = true");
                if (null != home.getAmenities().getGym())
                    sb.append(" and amenities.gym = true");
                if (null != home.getAmenities().getWorkspace())
                    sb.append(" and amenities.workspace = true");
                if (null != home.getAmenities().getFirstAidKit())
                    sb.append(" and amenities.firstAidKit = true");
                if (null != home.getAmenities().getFireExtinguisher())
                    sb.append(" and amenities.fireExtinguisher = true");
                if (null != home.getAmenities().getSmokeDetector())
                    sb.append(" and amenities.smokeDetector = true");
                if (null != home.getAmenities().getNoPets())
                    sb.append(" and amenities.noPets = true");

                if (null != home.getAmenities().getPool())
                    sb.append(" and amenities.pool = true");

                if (null != home.getAmenities().getNoDrinking())
                    sb.append(" and amenities.noDrinking= true");

                if (null != home.getAmenities().getNoSmoking())
                    sb.append(" and amenities.noSmoking = true");

            }



            sb.append(" order by Rating desc");

            LOGGER.debug("query is " + sb.toString());

            TypedQuery<Home> query = jpaApi.em().createQuery(sb.toString(), Home.class);
            homes = query.getResultList();

        }catch(NoResultException nre){

        }
        return homes;

    }

    @Override
    public Collection<Home> search(Home home) {
        List<Home> homes=null;

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT h from Home h WHERE houseStatus = 1 and deleteRequest = 1");

        //book status should be false and house status should be approved and delete request should be false


        try {

            if(null != home.getGuestCount()){
                sb.append("and guestCount <= '"+home.getGuestCount()+"'");
            }
            if(null != home.getLocation()){
                sb.append(" and location = '"+home.getLocation()+"'");
            }
            if(null != home.getPrice()){
                sb.append(" and price <= '"+home.getPrice()+"'");
            }
            if(null != home.getToDate()){
                sb.append(" and toDate <= '"+home.getToDate1()+"'");
            }
            if(null != home.getFromDate()){
                sb.append(" and fromDate >= '"+home.getFromDate1()+"'");
            }

        TypedQuery<Home> query = jpaApi.em().createQuery(sb.toString(), Home.class);
        homes = query.getResultList();


    }catch(NoResultException nre){

    }
        return homes;


}

    @Override
    public Home findHomeById(Integer  id) {

        if(null == id){
            throw new IllegalArgumentException("Id must be provided");
        }

        LOGGER.debug("home id is " + id);
        final Home existinghome = jpaApi.em().find(Home.class,id);


        if(null == existinghome){
            throw new IllegalArgumentException("Invalid home");
        }

        LOGGER.debug("homeid is "+existinghome.getHomeId());

        //LOGGER.debug("urls are"+ imageDao.searchByHomeId(36));
        return existinghome;



    }
}


