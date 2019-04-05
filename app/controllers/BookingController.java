package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.security.Authenticator;
import daos.BookingDao;
import daos.HomeDao;
import models.Booking;
import models.Home;
import models.User;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.awt.print.Book;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookingController extends Controller {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    final HomeDao homeDao;

    private final static Logger.ALogger LOGGER = Logger.of(controllers.BookingController.class);


    final BookingDao bookingDao;

    @Inject
    public BookingController(HomeDao homeDao, BookingDao bookingDao) {
        this.homeDao = homeDao;
        this.bookingDao = bookingDao;
    }


    @Transactional
    @Authenticator
    public Result createBooking(){
        final JsonNode json = request().body().asJson();
        final Booking booking = Json.fromJson(json, Booking.class);

        final Integer homeId = json.get("homeId").asInt();
        final Home home = homeDao.findHomeById(homeId);


        final User user = (User) ctx().args.get("user");
        LOGGER.debug("user while creation is "+user);

        booking.setUser(user);
        //LOGGER.debug("user id" + booking.getUser());
        booking.setHome(home);



        final Booking newBooking = bookingDao.create(booking);


        final JsonNode result = Json.toJson(newBooking);

        return ok(result);

    }

    @Transactional
    public Result getDatesByHomeId(Integer id){
        Collection<Booking> newBookings =null;
//        final User user = (User) ctx().args.get("user");
//        LOGGER.debug("user is "+user);

        if (null == id) {
            return badRequest("Home Id must be provided");
        }
        try{
        newBookings = bookingDao.getBookingsByHomeId(id);
        }
        catch (NoResultException nre){
           throw new IllegalArgumentException("No bookings found for the given home id");
        }

        final JsonNode result = Json.toJson(newBookings);

        return ok(result);
    }

    @Transactional
    public Result getHomeDetailsByUserId(Integer id){
        Collection<Booking> newBookings =null;
        ArrayList list = new ArrayList();
        if (null == id) {
            return badRequest("Home Id must be provided");
        }
        try{
            newBookings = bookingDao.getBookingsByUserId(id);

            for(Booking booking : newBookings){
                ArrayList data = new ArrayList();
                final Home home = homeDao.findHomeById(bookingDao.findHomeidByBookingId(booking.getBookingId()));
                data.add(booking.getBookingId());
                data.add(home.getHomeId());
                data.add(home.getHomeName());
                data.add(DATE_FORMAT.format(booking.getToDate()));
                list.add(data);
            }

            //LOGGER.debug("home id is " + home.getHomeId());

        }
        catch (NoResultException nre){
            throw new IllegalArgumentException("No bookings found for the given home id");
        }

        final JsonNode result = Json.toJson(list);

        return ok(result);
    }

    @Transactional
    public Result bookingDeletionById(Integer id) {

        if (null == id) {
            return badRequest("Booking Id must be provided");
        }
        final Booking existingBooking = bookingDao.findBookingById(id);
        final Booking newBooking = bookingDao.delete(existingBooking);

        final JsonNode result = Json.toJson(newBooking);

        return ok(result);

    }

    @Transactional
    public Result getBookingsCountByHomeId(Integer id){
        Integer newBookings = 0;
//        final User user = (User) ctx().args.get("user");
//        LOGGER.debug("user is "+user);

        if (null == id) {
            return badRequest("Home Id must be provided");
        }
        try{
            newBookings = bookingDao.getBookingsByHome(id);
        }
        catch (NoResultException nre){
            throw new IllegalArgumentException("No bookings found for the given home id");
        }

        final JsonNode result = Json.toJson(newBookings);

        return ok(result);
    }

}
