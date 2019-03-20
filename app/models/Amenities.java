package models;

import com.fasterxml.jackson.annotation.JsonProperty;


import javax.persistence.*;


@Entity
public class Amenities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("amenityId")
    private Integer amenityId;

    @Basic
    @JsonProperty("wifi")
    private Boolean wifi;

    @Basic
    @JsonProperty("airConditioner")
    private Boolean airConditioner;

    @Basic
    @JsonProperty("tv")
    private Boolean tv;

    @Basic
    @JsonProperty("breakfast")
    private Boolean breakfast;

    @Basic
    @JsonProperty("pool")
    private Boolean pool;

    @Basic
    @JsonProperty("parking")
    private Boolean parking;

    @Basic
    @JsonProperty("gym")
    private Boolean gym;

    @Basic
    @JsonProperty("workspace")
    private Boolean workspace;

    @Basic
    @JsonProperty("firstAidKit")
    private Boolean firstAidKit;

    @Basic
    @JsonProperty("fireExtinguisher")
    private Boolean fireExtinguisher;

    @Basic
    @JsonProperty("smokeDetector")
    private Boolean smokeDetector;

    @Basic
    @JsonProperty("noPets")
    private Boolean noPets;

    @Basic
    @JsonProperty("noDrinking")
    private Boolean noDrinking;

    @Basic
    @JsonProperty("noSmoking")
    private Boolean noSmoking;


    public Amenities(){

    }

    public Amenities(Integer amenityId, Boolean wifi, Boolean airConditioner, Boolean tv, Boolean breakfast, Boolean pool, Boolean parking, Boolean gym, Boolean workspace, Boolean firstAidKit, Boolean fireExtinguisher, Boolean smokeDetector, Boolean noPets, Boolean noDrinking, Boolean noSmoking) {
        this.amenityId = amenityId;
        this.wifi = wifi;
        this.airConditioner = airConditioner;
        this.tv = tv;
        this.breakfast = breakfast;
        this.pool = pool;
        this.parking = parking;
        this.gym = gym;
        this.workspace = workspace;
        this.firstAidKit = firstAidKit;
        this.fireExtinguisher = fireExtinguisher;
        this.smokeDetector = smokeDetector;
        this.noPets = noPets;
        this.noDrinking = noDrinking;
        this.noSmoking = noSmoking;
    }

    public Integer getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(Integer amenityId) {
        this.amenityId = amenityId;
    }

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getAirConditioner() {
        return airConditioner;
    }

    public void setAirConditioner(Boolean airConditioner) {
        this.airConditioner = airConditioner;
    }

    public Boolean getTv() {
        return tv;
    }

    public void setTv(Boolean tv) {
        this.tv = tv;
    }

    public Boolean getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Boolean breakfast) {
        this.breakfast = breakfast;
    }

    public Boolean getPool() {
        return pool;
    }

    public void setPool(Boolean pool) {
        this.pool = pool;
    }

    public Boolean getParking() {
        return parking;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean getGym() {
        return gym;
    }

    public void setGym(Boolean gym) {
        this.gym = gym;
    }

    public Boolean getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Boolean workspace) {
        this.workspace = workspace;
    }

    public Boolean getFirstAidKit() {
        return firstAidKit;
    }

    public void setFirstAidKit(Boolean firstAidKit) {
        this.firstAidKit = firstAidKit;
    }

    public Boolean getFireExtinguisher() {
        return fireExtinguisher;
    }

    public void setFireExtinguisher(Boolean fireExtinguisher) {
        this.fireExtinguisher = fireExtinguisher;
    }

    public Boolean getSmokeDetector() {
        return smokeDetector;
    }

    public void setSmokeDetector(Boolean smokeDetector) {
        this.smokeDetector = smokeDetector;
    }

    public Boolean getNoPets() {
        return noPets;
    }

    public void setNoPets(Boolean noPets) {
        this.noPets = noPets;
    }

    public Boolean getNoDrinking() {
        return noDrinking;
    }

    public void setNoDrinking(Boolean noDrinking) {
        this.noDrinking = noDrinking;
    }

    public Boolean getNoSmoking() {
        return noSmoking;
    }

    public void setNoSmoking(Boolean noSmoking) {
        this.noSmoking = noSmoking;
    }
}
