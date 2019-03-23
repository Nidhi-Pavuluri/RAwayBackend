package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import controllers.security.Authenticator;
import controllers.security.IsAdmin;
import daos.ImageDao;
import daos.HomeDao;
import models.Home;
import models.Image;
import models.User;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.*;

public class RHomeController extends Controller {

    private final static Logger.ALogger LOGGER = Logger.of(controllers.HomeController.class);

    final HomeDao homeDao;
    private ImageDao imageDao;

    @Inject
    public RHomeController(HomeDao homeDao, ImageDao imageDao) {
        this.homeDao = homeDao;
        this.imageDao = imageDao;
    }


    @Transactional
    @Authenticator
    public Result createHome() {

        final JsonNode json = request().body().asJson();
        final Home home = Json.fromJson(json, Home.class);



        final User user = (User) ctx().args.get("user");
        user.setRole(User.Role.HOST);


        home.setUser(user);


        if(null == home.getHomeName() || null == home.getPropertyType() || null == home.getGuestCount() || null == home.getLocation() || null==home.getPrice() || null==home.getToDate() || null == home.getFromDate())
            return badRequest("Missing mandatory parameters");

        home.setHouseStatus(Home.HouseStatus.PENDING);
        home.setReportFlag(0);
        home.setBook(Home.Bool.FALSE);
        home.setDeleteRequest(Home.Bool.FALSE);

        LOGGER.debug("todate is"+home.getToDate());

        final Home newHome = homeDao.create(home);

        for (String url : home.getImageUrls()) {
            final Image image = new Image(url);
            image.setImageUrl(url);
            image.setHome(newHome);
            imageDao.create(image);
        }



        final JsonNode result = Json.toJson(newHome);

        return ok(result);

    }

    @Transactional
    public Result filters()
    {

        final JsonNode json = request().body().asJson();

        final Home home = Json.fromJson(json, Home.class);
        LOGGER.debug("Location"+home.getLocation());

        final Collection<Home> newHome = homeDao.filters(home);
        for(Home home_new: newHome ){
            String[] image_strings = imageDao.searchByHomeId(home_new.getHomeId());
            LOGGER.debug("img collection is "+ image_strings);
            home_new.setImageUrls(image_strings);
        }

        final JsonNode result = Json.toJson(newHome);
        return ok(result);
    }


    @Transactional
    public Result homesearch()
    {


        final JsonNode json = request().body().asJson();

        final Home home = Json.fromJson(json, Home.class);

        if(null == home.getLocation() || null == home.getToDate() || null == home.getFromDate() || null == home.getPrice() || null == home.getGuestCount()){
            return badRequest("Missing mandatory parameters");
        }

        final Collection<Home> newHome = homeDao.search(home);
        for(Home home_new: newHome ){
            String[] image_strings = imageDao.searchByHomeId(home_new.getHomeId());
            LOGGER.debug("img collection is "+ image_strings);
            home_new.setImageUrls(image_strings);
        }

        final JsonNode result = Json.toJson(newHome);
        return ok(result);
    }

    @Transactional
    public Result getHomeByHomeId(Integer id) {

        if (null == id) {
            return badRequest("Home Id must be provided");
        }

        final Home home = homeDao.findHomeById(id);
        String[] image_strings = imageDao.searchByHomeId(home.getHomeId());
        home.setImageUrls(image_strings);


        if (home != null) {
            final JsonNode result = Json.toJson(home);
            return ok(result);
        }

        else {
            return notFound();
        }
    }

    @Transactional
    public Result updateHomeBooking(Integer id) {

        if (null == id) {
            return badRequest("Home Id must be provided");
        }

        final Home existinghome = homeDao.findHomeById(id);
        String[] image_strings = imageDao.searchByHomeId(existinghome.getHomeId());
        existinghome.setImageUrls(image_strings);
        final Home newhome = homeDao.Bookupdate(existinghome);

        if (newhome != null) {
            final JsonNode result = Json.toJson(newhome);
            return ok(result);
        }

        else {
            return notFound();
        }

    }


    @Transactional
    public Result requestedHomeDeletionByAdmin(Integer id) {

        if (null == id) {
            return badRequest("Home Id must be provided");
        }
        final Home existinghome = homeDao.findHomeById(id);
        String[] image_strings = imageDao.searchByHomeId(existinghome.getHomeId());
        existinghome.setImageUrls(image_strings);
        for (String url : existinghome.getImageUrls()) {
            imageDao.delete(url);
        }
        final Home newhome = homeDao.deleteadmin(existinghome);


            for (String url : newhome.getImageUrls()) {
                imageDao.delete(url);
            }
            final JsonNode result = Json.toJson(newhome);

            return ok(result);

    }

    @Transactional
    public Result reportedHomeDeletionByAdmin(Integer id) {

        if (null == id) {
            return badRequest("Home Id must be provided");
        }
        final Home existinghome = homeDao.findHomeById(id);
        String[] image_strings = imageDao.searchByHomeId(existinghome.getHomeId());
        existinghome.setImageUrls(image_strings);
        for (String url : existinghome.getImageUrls()) {
            imageDao.delete(url);
        }
        final Home newhome = homeDao.deleteReportadmin(existinghome);

        final JsonNode result = Json.toJson(newhome);
        return ok(result);

    }


    @Transactional
    public Result homeDeletionByUser(Integer id) {

        if (null == id) {
            return badRequest("Home Id must be provided");
        }

        final Home existinghome = homeDao.findHomeById(id);
        String[] image_strings = imageDao.searchByHomeId(existinghome.getHomeId());
        existinghome.setImageUrls(image_strings);
        for (String url : existinghome.getImageUrls()) {
            imageDao.delete(url);
        }
        final Home newhome = homeDao.deleteuser(existinghome);
        final JsonNode result = Json.toJson(newhome);
        return ok(result);

    }

    @Transactional
    public Result getPendingListings() {

        Collection<Home> homes = homeDao.pendingListings();
        for(Home home_new: homes ){
            String[] image_strings = imageDao.searchByHomeId(home_new.getHomeId());
            LOGGER.debug("img collection is "+ image_strings);
            home_new.setImageUrls(image_strings);
        }


        final JsonNode result = Json.toJson(homes);
        return ok(result);

    }

    @Transactional
    public Result getPendingListofUsers() {

        Collection<Home> homes = homeDao.pendingUsers();
        for(Home home_new: homes ){
            String[] image_strings = imageDao.searchByHomeId(home_new.getHomeId());
            LOGGER.debug("img collection is "+ image_strings);
            home_new.setImageUrls(image_strings);
        }


        final JsonNode result = Json.toJson(homes);
        return ok(result);

    }


    @Transactional
    public Result getApprovedListofUsers() {

        Collection<Home> homes = homeDao.approvedlist();
        for(Home home_new: homes ){
            String[] image_strings = imageDao.searchByHomeId(home_new.getHomeId());
            LOGGER.debug("img collection is "+ image_strings);
            home_new.setImageUrls(image_strings);
        }

        final JsonNode result = Json.toJson(homes);
        return ok(result);

    }


    @Transactional
    public Result getreportedListofUsers() {

        Collection<Home> homes = homeDao.reportlist();
        for(Home home_new: homes ){
            String[] image_strings = imageDao.searchByHomeId(home_new.getHomeId());
            LOGGER.debug("img collection is "+ image_strings);
            home_new.setImageUrls(image_strings);
        }

        final JsonNode result = Json.toJson(homes);
        return ok(result);

    }

    @Transactional
    public Result changePendingStatus(Integer id) {

        if (null == id) {
            return badRequest("Home Id must be provided");
        }

        final Home existinghome = homeDao.findHomeById(id);
        String[] image_strings = imageDao.searchByHomeId(existinghome.getHomeId());
        existinghome.setImageUrls(image_strings);
        final Home newhome = homeDao.homeStatusupdate(existinghome);

        if (newhome != null) {
            final JsonNode result = Json.toJson(newhome);
            return ok(result);
        }

        else {
            return notFound();
        }



        }



    @Transactional
    public Result reportHome(Integer id) {

        if (null == id) {
            return badRequest("Home Id must be provided");
        }


        final Home existinghome = homeDao.findHomeById(id);
        String[] image_strings = imageDao.searchByHomeId(existinghome.getHomeId());
        existinghome.setImageUrls(image_strings);
        final Home newhome = homeDao.homeReportupdate(existinghome);

        if (newhome != null) {
            final JsonNode result = Json.toJson(newhome);
            return ok(result);
        }

        else {
            return notFound();
        }



    }

    @Transactional
    public Result changeDeleteRequestStatus(Integer id) {

        if (null == id) {
            return badRequest("Home Id must be provided");
        }

        final Home existinghome = homeDao.findHomeById(id);
        String[] image_strings = imageDao.searchByHomeId(existinghome.getHomeId());
        existinghome.setImageUrls(image_strings);
        final Home newhome = homeDao.deleteRequestUpdate(existinghome);

        if (newhome != null) {
            final JsonNode result = Json.toJson(newhome);
            return ok(result);
        }

        else {
            return notFound();
        }



    }


    @Transactional
    public Result getHomeByUsername(){

        final JsonNode json = request().body().asJson();
        final Home home = Json.fromJson(json,Home.class);
        final String username = json.get("username").asText();
        Collection<Home> homes = homeDao.searchByUsername(username);
        for(Home home_new: homes ){
            String[] image_strings = imageDao.searchByHomeId(home_new.getHomeId());
            LOGGER.debug("img collection is "+ image_strings);
            home_new.setImageUrls(image_strings);
        }

        final JsonNode result = Json.toJson(homes);
        return ok(result);

    }





}
