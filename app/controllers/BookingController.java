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

public class BookingController extends Controller {
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

        booking.setUser(user);
        //LOGGER.debug("user id" + booking.getUser());
        booking.setHome(home);



        final Booking newBooking = bookingDao.create(booking);


        final JsonNode result = Json.toJson(newBooking);

        return ok(result);

    }

//    @Transactional
//    @Authenticator
//    public Result getDatesByHomeId(){
//
//    }
}
