package diegomezquita.treelife;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

/**
 * Created by diegomezquita on 06/04/16.
 */
public class Container implements Parcelable {
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

    public Container(Parcel container_parcel) {
        this.setTitle(container_parcel.readString());
        this.setLatitude(container_parcel.readDouble());
        this.setLongitude(container_parcel.readDouble());
        this.setPlace(container_parcel.readString());
    }

    public static final Parcelable.Creator<Container> CREATOR = new Parcelable.Creator<Container>()
    {
        @Override
        public Container createFromParcel(Parcel source)
        {
            return new Container(source);
        }

        @Override
        public Container[] newArray(int size)
        {
            return new Container[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getTitle());
        dest.writeDouble(this.getLatitude());
        dest.writeDouble(this.getLongitude());
        dest.writeString(this.getPlace());
    }
}
