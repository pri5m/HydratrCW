package com.nsa.hydratr.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * A model representing a log of a user's water consumption
 * Created by c1712480 on 03/05/2018.
 */

@Entity
public class WaterLog implements Parcelable {

    /**
     * Set up of Parcelable in the model
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public WaterLog createFromParcel(Parcel in){
            return new WaterLog(in);
        }
        public WaterLog[] newArray(int size){
            return new WaterLog[size];
        }
    };

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "log_date")
    private String date;

    @ColumnInfo(name = "log_ltr")
    private String ltr;

    @ColumnInfo(name = "log_negative")
    private Boolean negative;

    @ColumnInfo(name = "log_neutral")
    private Boolean neutral;

    @ColumnInfo(name = "log_positive")
    private Boolean positive;

    /**
     * Model constructor
     * @param date
     * @param ltr
     * @param negative
     * @param neutral
     * @param positive
     */
    public WaterLog(String date, String ltr, Boolean negative, Boolean neutral, Boolean positive) {
        this.date = date;
        this.ltr = ltr;
        this.negative = negative;
        this.neutral = neutral;
        this.positive = positive;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDate() {
        return date;
    }

    public String getLtr() {
        return ltr;
    }

    public Boolean getNegative() {
        return negative;
    }

    public Boolean getNeutral() {
        return neutral;
    }

    public Boolean getPositive() {
        return positive;
    }

    /**
     * Parcelable constructor
     * @param in
     */
    public WaterLog(Parcel in){
        date = in.readString();
        ltr = in.readString();
        negative = in.readByte() != 0;
        neutral = in.readByte() != 0;
        positive = in.readByte() != 0;
    }

    /**
     * Parcel implementation
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcel implementation
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(ltr);
        dest.writeByte((byte) (negative ? 1 : 0)); //Adapted from: https://stackoverflow.com/a/7089687
        dest.writeByte((byte) (neutral ? 1 : 0));
        dest.writeByte((byte) (positive ? 1 : 0));
    }
}
