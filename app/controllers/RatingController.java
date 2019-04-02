package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.security.Authenticator;
import daos.HomeDao;
import daos.RatingDao;
import models.Booking;
import models.Home;
import models.Rating;
import models.User;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.Collection;

public class RatingController extends Controller {
    final HomeDao homeDao;
    final RatingDao ratingDao;
    private final static Logger.ALogger LOGGER = Logger.of(controllers.BookingController.class);

    @Inject
    public RatingController(HomeDao homeDao, RatingDao ratingDao) {
        this.homeDao = homeDao;
        this.ratingDao = ratingDao;
    }

    @Transactional
    @Authenticator
    public Result createRating(){
        final JsonNode json = request().body().asJson();
        final Rating rating = Json.fromJson(json, Rating.class);

        final Integer homeId = json.get("homeId").asInt();
        final Home home = homeDao.findHomeById(homeId);


        final User user = (User) ctx().args.get("user");

        rating.setUser(user);
        //LOGGER.debug("user id" + booking.getUser());
        rating.setHome(home);



        final Rating newRating = ratingDao.create(rating);


        final JsonNode result = Json.toJson(newRating);

        return ok(result);

    }

    @Transactional
    public Result getAvgRatingsByHome(Integer id){
        Integer avgRating  = 0;

        if (null == id) {
            return badRequest("Home Id must be provided");
        }
        try{
            avgRating = ratingDao.getRatingsByHomeId(id);
        }
        catch (NoResultException nre){
            throw new IllegalArgumentException("No ratings found for the given home id");
        }

        final JsonNode result = Json.toJson(avgRating);

        return ok(result);
    }


}
