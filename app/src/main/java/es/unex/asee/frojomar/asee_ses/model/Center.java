
package es.unex.asee.frojomar.asee_ses.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
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
        "address",
        "b64_image",
        "city_id",
        "id",
        "lat",
        "long",
        "name",
        "telephone",
        "telephone_urg"
})
@Entity(tableName = "Centers")
public class Center implements Parcelable {

    @JsonProperty("address")
    private String address;
    @JsonProperty("b64_image")
    private String b64Image;
    @JsonProperty("city_id")
    private Integer cityId;
    @PrimaryKey
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("long")
    private Double _long;
    @JsonProperty("name")
    private String name;
    @JsonProperty("telephone")
    private String telephone;
    @JsonProperty("telephone_urg")
    private String telephoneUrg;
    @JsonIgnore
    @Ignore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @Ignore
    public Center(){

    }

    @Ignore
    public Center(Parcel in){
        readFromParcel(in);
    }

    @Ignore
    public static final Parcelable.Creator<Center> CREATOR
            = new Parcelable.Creator<Center>() {
        public Center createFromParcel(Parcel in) {
            return new Center(in);
        }

        public Center[] newArray(int size) {
            return new Center[size];
        }
    };

    public Center(String address, String b64Image, Integer cityId, Integer id,
                  Double lat, Double _long, String name, String telephone, String telephoneUrg) {
        this.address = address;
        this.b64Image = b64Image;
        this.cityId = cityId;
        this.id = id;
        this.lat = lat;
        this._long = _long;
        this.name = name;
        this.telephone = telephone;
        this.telephoneUrg = telephoneUrg;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("b64_image")
    public String getB64Image() {
        return b64Image;
    }

    @JsonProperty("b64_image")
    public void setB64Image(String b64Image) {
        this.b64Image = b64Image;
    }

    @JsonProperty("city_id")
    public Integer getCityId() {
        return cityId;
    }

    @JsonProperty("city_id")
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("lat")
    public Double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    @JsonProperty("long")
    public Double getLong() {
        return _long;
    }

    @JsonProperty("long")
    public void setLong(Double _long) {
        this._long = _long;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("telephone")
    public String getTelephone() {
        return telephone;
    }

    @JsonProperty("telephone")
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @JsonProperty("telephone_urg")
    public String getTelephoneUrg() {
        return telephoneUrg;
    }

    @JsonProperty("telephone_urg")
    public void setTelephoneUrg(String telephoneUrg) {
        this.telephoneUrg = telephoneUrg;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(b64Image);
        dest.writeInt(cityId);
        dest.writeInt(id);
        dest.writeDouble(lat);
        dest.writeDouble(_long);
        dest.writeString(name);
        dest.writeString(telephone);
        dest.writeString(telephoneUrg);
    }

    private void readFromParcel(Parcel in){
        address= in.readString();
        b64Image= in.readString();
        cityId= in.readInt();
        id= in.readInt();
        lat= in.readDouble();
        _long= in.readDouble();
        name= in.readString();
        telephone= in.readString();
        telephoneUrg= in.readString();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        try {
            return this.id == ((Center) obj).getId();
        }catch (Exception e){
            return false;
        }
    }
}
