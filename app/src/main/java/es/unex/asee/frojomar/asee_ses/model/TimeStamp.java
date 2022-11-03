package es.unex.asee.frojomar.asee_ses.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import es.unex.asee.frojomar.asee_ses.utils.DateTypeConverter;

@Entity(tableName = "TimeStamp")
public class TimeStamp {

    @PrimaryKey
    private Integer id;
    @TypeConverters(DateTypeConverter.class)
    private Date timestampCenters;
    @TypeConverters(DateTypeConverter.class)
    private Date timestampCities;
    @TypeConverters(DateTypeConverter.class)
    private Date timestampDoctors;


    public TimeStamp() {
    }

    @Ignore
    public TimeStamp(Date timestampCenters, Date timestampCities, Date timestampDoctors) {
        this.id=0;
        this.timestampCenters = timestampCenters;
        this.timestampCities = timestampCities;
        this.timestampDoctors = timestampDoctors;
    }

    public Date getTimestampCenters() {
        return timestampCenters;
    }

    public void setTimestampCenters(Date timestampCenters) {
        this.timestampCenters = timestampCenters;
    }

    public Date getTimestampCities() {
        return timestampCities;
    }

    public void setTimestampCities(Date timestampCities) {
        this.timestampCities = timestampCities;
    }

    public Date getTimestampDoctors() {
        return timestampDoctors;
    }

    public void setTimestampDoctors(Date timestampDoctors) {
        this.timestampDoctors = timestampDoctors;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
