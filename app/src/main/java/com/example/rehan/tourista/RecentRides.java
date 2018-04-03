package com.example.rehan.tourista;

/**
 * Created by Aimann on 3/22/2018.
 */

public class RecentRides {
    public String trip_id_PK;
    public String tourist_name;
    public String Pick_up;
    public String Drop_off;
    public String tourist_id_FK;



    public void setTourist_id_FK(String tourist_id_FK) {
        this.tourist_id_FK = tourist_id_FK;
    }

    public void setTrip_id_PK(String trip_id_PK) {
        this.trip_id_PK = trip_id_PK;
    }

    public void setTourist_name(String tourist_name) {
        this.tourist_name = tourist_name;
    }

    public void setPick_up(String pick_up) {
        Pick_up = pick_up;
    }

    public void setDrop_off(String drop_off) {
        Drop_off = drop_off;
    }
    public String getTourist_id_FK() {
        return tourist_id_FK;
    }

    public String getTrip_id_PK() {
        return trip_id_PK;
    }

    public String getPick_up() {
        return Pick_up;
    }

    public String getDrop_off() {
        return Drop_off;
    }

    public String getTourist_name() {
        return tourist_name;
    }

}
