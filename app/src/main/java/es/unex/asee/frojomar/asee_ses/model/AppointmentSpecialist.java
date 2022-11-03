package es.unex.asee.frojomar.asee_ses.model;

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
        "doctor_id",
        "id",
        "medical_field",
        "person_id",
        "sol_aplazamiento",
        "time"
})
public class AppointmentSpecialist implements Parcelable {

    @JsonProperty("date")
    @Expose
    @SerializedName("date")
    private String date;
    @JsonProperty("description")
    @Expose
    @SerializedName("description")
    private String description;
    @JsonProperty("doctor_id")
    @Expose
    @SerializedName("doctor_id")
    private Integer doctorId;
    @JsonProperty("id")
    @Expose
    @SerializedName("id")
    private Integer id;
    @JsonProperty("medical_field")
    @Expose
    @SerializedName("medical_field")
    private String medicalField;
    @JsonProperty("person_id")
    @Expose
    @SerializedName("person_id")
    private Integer personId;
    @JsonProperty("sol_aplazamiento")
    @Expose
    @SerializedName("sol_aplazamiento")
    private String solAplazamiento;
    @JsonProperty("time")
    @Expose
    @SerializedName("time")
    private String time;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public static final Creator<AppointmentSpecialist> CREATOR = new Creator<AppointmentSpecialist>() {
        @Override
        public AppointmentSpecialist createFromParcel(Parcel in) {
            return new AppointmentSpecialist(in);
        }

        @Override
        public AppointmentSpecialist[] newArray(int size) {
            return new AppointmentSpecialist[size];
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

    public AppointmentSpecialist withDate(String date) {
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

    public AppointmentSpecialist withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("doctor_id")
    public Integer getDoctorId() {
        return doctorId;
    }

    @JsonProperty("doctor_id")
    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public AppointmentSpecialist withDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
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

    public AppointmentSpecialist withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("medical_field")
    public String getMedicalField() {
        return medicalField;
    }

    @JsonProperty("medical_field")
    public void setMedicalField(String medicalField) {
        this.medicalField = medicalField;
    }

    public AppointmentSpecialist withMedicalField(String medicalField) {
        this.medicalField = medicalField;
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

    public AppointmentSpecialist withPersonId(Integer personId) {
        this.personId = personId;
        return this;
    }

    @JsonProperty("sol_aplazamiento")
    public String getSolAplazamiento() {
        return solAplazamiento;
    }

    @JsonProperty("sol_aplazamiento")
    public void setSolAplazamiento(String solAplazamiento) {
        this.solAplazamiento = solAplazamiento;
    }

    public AppointmentSpecialist withSolAplazamiento(String solAplazamiento) {
        this.solAplazamiento = solAplazamiento;
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

    public AppointmentSpecialist withTime(String time) {
        this.time = time;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public AppointmentSpecialist withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public AppointmentSpecialist(){}

    public AppointmentSpecialist(Parcel in){
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(description);
        dest.writeInt(doctorId);
        dest.writeInt(id);
        dest.writeString(medicalField);
        dest.writeInt(personId);
        dest.writeString(solAplazamiento);
        dest.writeString(time);
    }

    private void readFromParcel(Parcel in){
        date= in.readString();
        description= in.readString();
        doctorId= in.readInt();
        id= in.readInt();
        medicalField= in.readString();
        personId=in.readInt();
        solAplazamiento= in.readString();
        time= in.readString();
    }



}