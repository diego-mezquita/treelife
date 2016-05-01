package diegomezquita.treelife;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by diegomezquita on 06/04/16.
 */
public class Containers implements Parcelable {
    @SerializedName("contenedorropa")
    private ArrayList<Container> containerList;

    public Containers(Parcel container_parcel) {
        this.setContainerList(container_parcel.readArrayList(Container.class.getClassLoader()));
    }

    public ArrayList<Container> getContainerList() {
        return containerList;
    }

    public void setContainerList(ArrayList<Container> containerList) {
        this.containerList = containerList;
    }

    public static final Parcelable.Creator<Containers> CREATOR = new Creator<Containers>() {
        public Containers createFromParcel(Parcel source) {
            return new Containers(source);
        }

        public Containers[] newArray(int size) {
            return new Containers[size];
        }
    };

        public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.getContainerList());
    }

}
