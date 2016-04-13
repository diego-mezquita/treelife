package diegomezquita.treelife;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by diegomezquita on 06/04/16.
 */
public class Container {
    @SerializedName("titulo")
    private String title;
    @SerializedName("latitud")
    private Double latitude;
    @SerializedName("longitud")
    private Double longitude;
    @SerializedName("lugar")
    private String place;
    // @SerializedName("titulo")
    // private String type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
