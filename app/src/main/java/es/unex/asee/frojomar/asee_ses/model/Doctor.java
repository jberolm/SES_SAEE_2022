
package es.unex.asee.frojomar.asee_ses.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "b64_image",
        "center_id",
        "id",
        "name"
})
@Entity(tableName = "Doctors")
public class Doctor {

    @JsonProperty("b64_image")
    private String b64Image;
    @JsonProperty("center_id")
    private Integer centerId;
    @PrimaryKey
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    @Ignore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @Ignore
    public Doctor(){

    }

    public Doctor(String b64Image, Integer centerId, Integer id, String name) {
        this.b64Image = b64Image;
        this.centerId = centerId;
        this.id = id;
        this.name = name;
    }

    @JsonProperty("b64_image")
    public String getB64Image() {
        return b64Image;
    }

    @JsonProperty("b64_image")
    public void setB64Image(String b64Image) {
        this.b64Image = b64Image;
    }

    @JsonProperty("center_id")
    public Integer getCenterId() {
        return centerId;
    }

    @JsonProperty("center_id")
    public void setCenterId(Integer centerId) {
        this.centerId = centerId;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
