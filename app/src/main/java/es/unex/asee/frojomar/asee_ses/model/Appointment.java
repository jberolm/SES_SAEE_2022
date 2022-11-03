package es.unex.asee.frojomar.asee_ses.model;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "date",
        "description",
        "id",
        "person_id",
        "telephone",
        "time"
})
public class Appointment implements Parcelable {

    public static final String DATE="date";
    public static final String DESCRIPTION="description";
    public static final String ID="id";
    public static final String PERSON_ID="person_id";
    public static final String TELEPHONE="telephone";
    public static final String TIME="time";

    @JsonProperty("date")
    @Expose
    @SerializedName("date")
    private String date;

    @JsonProperty("description")
    @Expose
    @SerializedName("description")
    private String description;

    @JsonProperty("id")
    @Expose
    @SerializedName("id")
    private Integer id;

    @JsonProperty("person_id")
    @Expose
    @SerializedName("person_id")
    private Integer personId;

    @JsonProperty("telephone")
    @Expose
    @SerializedName("telephone")
    private String telephone;

    @JsonProperty("time")
    @Expose
    @SerializedName("time")
    private String time;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    public Appointment withDate(String date) {
        this.date = date;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Appointment withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    public Appointment withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("person_id")
    public Integer getPersonId() {
        return personId;
    }

    @JsonProperty("person_id")
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Appointment withPersonId(Integer personId) {
        this.personId = personId;
        return this;
    }

    @JsonProperty("telephone")
    public String getTelephone() {
        return telephone;
    }

    @JsonProperty("telephone")
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Appointment withTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    @JsonProperty("time")
    public String getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(String time) {
        this.time = time;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public Appointment withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public Appointment(){}

    public Appointment(Parcel in){
        readFromParcel(in);
    }

    public Appointment(Intent intent){
        date= intent.getStringExtra(Appointment.DATE);
        description= intent.getStringExtra(Appointment.DESCRIPTION);
        id= intent.getIntExtra(Appointment.ID, 0);
        personId=intent.getIntExtra(Appointment.PERSON_ID, 0);
        telephone= intent.getStringExtra(Appointment.TELEPHONE);
        time= intent.getStringExtra(Appointment.TIME);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(description);
        dest.writeInt(id);
        dest.writeInt(personId);
        dest.writeString(telephone);
        dest.writeString(time);
    }

    private void readFromParcel(Parcel in){
        date= in.readString();
        description= in.readString();
        id= in.readInt();
        personId=in.readInt();
        telephone= in.readString();
        time= in.readString();
    }

    public static void packageIntent(Intent intent, Integer personId, Integer id,
                                     String date, String description, String telephone,
                                     String time) {

        intent.putExtra(Appointment.PERSON_ID, personId);
        intent.putExtra(Appointment.ID, id);
        intent.putExtra(Appointment.DATE, date);
        intent.putExtra(Appointment.DESCRIPTION, description);
        intent.putExtra(Appointment.TELEPHONE, telephone);
        intent.putExtra(Appointment.TIME, time);

    }

    @Override
    public String toString() {
        return "Appointment{" +
                "date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", person_id=" + personId +
                ", telephone='" + telephone + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}