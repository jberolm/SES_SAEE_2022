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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "dni",
        "doctor_id",
        "id",
        "name",
        "telephone"
})
public class Person implements Parcelable {

    @JsonProperty("dni")
    private String dni;
    @JsonProperty("doctor_id")
    private Integer doctorId;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("telephone")
    private String telephone;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @JsonProperty("dni")
    public String getDni() {
        return dni;
    }

    @JsonProperty("dni")
    public void setDni(String dni) {
        this.dni = dni;
    }

    public Person withDni(String dni) {
        this.dni = dni;
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

    public Person withDoctorId(Integer doctorId) {
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

    public Person withId(Integer id) {
        this.id = id;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Person withName(String name) {
        this.name = name;
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

    public Person withTelephone(String telephone) {
        this.telephone = telephone;
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

    public Person withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public Person(){}
    public Person(Parcel in){
        readFromParcel(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dni);
        dest.writeInt(doctorId);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(telephone);
    }

    private void readFromParcel(Parcel in){
        dni= in.readString();
        doctorId= in.readInt();
        id= in.readInt();
        name= in.readString();
        telephone= in.readString();
    }
}