package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.io.BaseEncoding;
import controllers.security.Authenticator;
import daos.Userdao;
import models.Home;
import models.User;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.RandomStringGenerator;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class UserController extends Controller {



    private final static int HASH_ITERATIONS = 100;

    private Userdao userDao;

    @Inject
    public UserController(Userdao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public Result registerUser() {

        final JsonNode json = request().body().asJson();
        final User user = Json.fromJson(json, User.class);

        if (null == user.getUsername() ||
                null == user.getEmail()) {
            return badRequest("Missing mandatory parameters");
        }

        final String password = json.get("passwordHash").asText();
        if (null == password) {
            return badRequest("Missing mandatory parameters");
        }

        if (null != userDao.findUserByName(user.getUsername())) {
            return badRequest("User taken");
        }

        final String salt = generateSalt();

        final String hash = generateHash(salt, password, HASH_ITERATIONS);

        user.setHashIterations(HASH_ITERATIONS);
        user.setSalt(salt);
        user.setPasswordHash(hash);
        user.setState(User.State.VERIFIED);
        user.setRole(User.Role.USER);



        userDao.create(user);

        final JsonNode result = Json.toJson(user);

        return ok(result);
    }

    @Transactional
    private String generateSalt() {

        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);


        return generatedString;
    }

    @Transactional
    private String generateHash(String salt, String password, int iterations) {
        try {

            final String contat = salt + ":" + password;
            String Hash = null;

            // TODO Run in a loop x iterations
            // TODO User a better hash function


            final MessageDigest sha = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = sha.digest(contat.getBytes());
            //Hash = BaseEncoding.base16().lowerCase().encode(messageDigest);
            for (int i = 0; i < iterations; i++) {
                // Concatenate the hash bytes with the clearPassword bytes and rehash
                messageDigest = sha.digest(ArrayUtils.addAll(messageDigest, contat.getBytes()));
            }

            final String passwordHash = BaseEncoding.base16().lowerCase().encode(messageDigest);




            return passwordHash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
          }

    }

    @Transactional
    public Result signInUser() {

        final JsonNode json = request().body().asJson();


        final String username = json.get("username").asText();
        final String password = json.get("passwordHash").asText();
        if (null == password || null == username) {
            return badRequest("Missing mandatory parameters");
        }
        //LOGGER.debug("entered signin");
        final User existingUser = userDao.findUserByName(username);
        //LOGGER.debug("user details taken");

        if (null == existingUser) {
            return unauthorized("Wrong username");
        }

        final String salt = existingUser.getSalt();
        final int iterations = existingUser.getHashIterations();
        final String hash = generateHash(salt, password, iterations);

        if (!existingUser.getPasswordHash().equals(hash)) {
            return unauthorized("Wrong password");
        }

        if (existingUser.getState() != User.State.VERIFIED) {
            return unauthorized("Account not verified");
        }

        existingUser.setAccessToken(generateAccessToken());

        userDao.updateaccessToken(existingUser);

        final JsonNode result = Json.toJson(existingUser);

        return ok(result);
    }


    @Transactional
    public Result updateUser() {

        final JsonNode json = request().body().asJson();
        final User user = Json.fromJson(json, User.class);



        if (null == user.getName() || null == user.getUsername()|| null == user.getGender() || null == user.getDob() || null == user.getMobilenbr() || null == user.getAadharcard() || null == user.getAddress()) {
            return badRequest("Missing mandatory parameters");
        }

        final User existingUser = userDao.findUserByName(user.getUsername());

        if (null == existingUser) {
            return unauthorized("Wrong username");
        }

        existingUser.setName(user.getName());
        existingUser.setGender(user.getGender());
        existingUser.setDob(user.getDob());
        existingUser.setMobilenbr(user.getMobilenbr());
        existingUser.setAadharcard(user.getAadharcard());
        existingUser.setAddress(user.getAddress());


        userDao.update(existingUser);

        final JsonNode result = Json.toJson(existingUser);

        return ok(result);
    }


    @Transactional
    private String generateAccessToken() {

        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = true;

        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z').build();
        String randomLetters = generator.generate(20);


        return randomLetters;

    }





    @Transactional
    @Authenticator
    public Result signOutUser() {

        final User user = (User) ctx().args.get("user");
        user.setAccessToken(null);
        userDao.updateaccessToken(user);

        return ok();
    }

    @Transactional
    @Authenticator
    //@IsAdmin
    public Result getCurrentUser() {

        final User user = (User) ctx().args.get("user");

        final JsonNode result = Json.toJson(user);

        return ok(result);
    }

    @Transactional
    public Result changePendingStatus(Integer id) {

        if (null == id) {
            return badRequest("User Id must be provided");
        }

        final User existingUser = userDao.findUserById(id);
        final User newUser = userDao.userRoleUpdate(existingUser);

        if (newUser != null) {
            final JsonNode result = Json.toJson(newUser);
            return ok(result);
        } else {
            return notFound();
        }
    }

//    @Transactional
//    public Result getPendingListofUsers() {
//
//        Collection<User> users = userDao.pendingUsers();
//        final JsonNode result = Json.toJson(users);
//        return ok(result);
//
//    }



}
