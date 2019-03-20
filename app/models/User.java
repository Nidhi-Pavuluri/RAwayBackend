package models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import play.Logger;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.*;


@Entity
public class User {


    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public enum Role {
        ADMIN,
        USER,
        HOST
    }


    public enum State {
        PENDING,
        VERIFIED,
        LOCKED
    }

    public enum Gender{
        MALE,
        FEMALE
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("userId")
    private Integer userId;

    @Basic
    @JsonProperty("username")
    private String username;

    @Basic
    @JsonProperty("email")
    private String email;

    @Basic
    @JsonIgnore
    @JsonProperty("passwordHash")
    private String passwordHash;


    @Basic
    @JsonIgnore
    @JsonProperty("salt")
    private String salt;

    @Basic
    @JsonIgnore
    @JsonProperty("hashIterations")
    private Integer hashIterations;

    @Basic
    @JsonProperty("role")
    private Role role;


    @Basic
    @JsonProperty("state")
    private State state;


    @Basic
    @JsonProperty("accessToken")
    private String accessToken;

    @Basic
    @JsonProperty("name")
    private String name;


    @Basic
    @JsonProperty("gender")
    private Gender gender;

    @Basic
    @JsonProperty("mobilenbr")
    private String mobilenbr;

    @Basic
    @JsonProperty("aadharcard")
    private String aadharcard;

    @Basic
    @JsonProperty("address")
    private String address;


    @Basic
    @JsonIgnore
    private Date dob;

    @JsonProperty("dob")
    public String getDOB1() {

        return DATE_FORMAT.format(dob);
    }

    @JsonProperty("dob")
    public void setDOB1(String dateString) {
        try {
            final Date date = DATE_FORMAT.parse(dateString);

            dob = date;
        } catch (ParseException e) {

        }
    }







    public User() {
    }

    public User(Integer userId, String username, String email, String passwordHash, String salt, Integer hashIterations, Role role, State state, String accessToken, String name, Gender gender, String mobilenbr, String aadharcard, String address, Date dob) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.hashIterations = hashIterations;
        this.role = role;
        this.state = state;
        this.accessToken = accessToken;
        this.name = name;
        this.gender = gender;
        this.mobilenbr = mobilenbr;
        this.aadharcard = aadharcard;
        this.address = address;
        this.dob = dob;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(Integer hashIterations) {
        this.hashIterations = hashIterations;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMobilenbr() {
        return mobilenbr;
    }

    public void setMobilenbr(String mobilenbr) {
        this.mobilenbr = mobilenbr;
    }

    public String getAadharcard() {
        return aadharcard;
    }

    public void setAadharcard(String aadharcard) {
        this.aadharcard = aadharcard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

}


