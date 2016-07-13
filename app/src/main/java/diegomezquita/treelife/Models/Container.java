package diegomezquita.treelife.Models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import diegomezquita.treelife.DatabaseAccess.DBHelper;

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
    private String type;
    private Long id;

    public Container() {
    }

    public Container(String location, String place, String type) {
        this.setTitle(location);
        this.setLatitude(0.0);
        this.setLongitude(0.0);
        this.setPlace(place);
        this.setType(type);
    }

    public Container(String location, String place, String type, Context context) {
        this.setTitle(location);
        this.setLatitude(0.0);
        this.setLongitude(0.0);
        this.setPlace(place);
        this.setType(type);

        DBHelper db = DBHelper.getInstance(context);
        this.setId(db.createContainer(this));
    }

    public Container(Parcel container_parcel) {
        this.setTitle(container_parcel.readString());
        this.setLatitude(container_parcel.readDouble());
        this.setLongitude(container_parcel.readDouble());
        this.setPlace(container_parcel.readString());
        this.setType(container_parcel.readString());
        this.setId(container_parcel.readLong());
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

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getPlace() { return place; }

    public void setPlace(String place) { this.place = place; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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
        dest.writeString(this.getType());

        // TODO think about a good solution in here
        //      option: check somehow if the container (from OpenData) is already in the database
        //             or not. handle the situation
        if(this.getId() == null) {
            this.setId(new Long(-2222));
        }

        dest.writeLong(this.getId());
    }
}
