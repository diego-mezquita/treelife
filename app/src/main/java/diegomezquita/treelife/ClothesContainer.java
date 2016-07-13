package diegomezquita.treelife;

import android.os.Parcel;
import android.os.Parcelable;

import diegomezquita.treelife.Models.Container;

/**
 * Created by diegomezquita on 03/05/16.
 */
public class ClothesContainer extends Container {
    String type = "clothes";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ClothesContainer(Parcel type_parcel) {
        this.setTitle(type_parcel.readString());
    }

    public static final Parcelable.Creator<ClothesContainer> CREATOR = new Parcelable.Creator<ClothesContainer>()
    {
        @Override
        public ClothesContainer createFromParcel(Parcel source)
        {
            return new ClothesContainer(source);
        }

        @Override
        public ClothesContainer[] newArray(int size)
        {
            return new ClothesContainer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getType());
    }
}
