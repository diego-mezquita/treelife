package diegomezquita.treelife;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by diegomezquita on 06/04/16.
 */
public class Containers {
    @SerializedName("contenedorropa")
    private ArrayList<Container> containerList;

    public ArrayList<Container> getContainerList() {
        return containerList;
    }

    public void setContainerList(ArrayList<Container> containerList) {
        this.containerList = containerList;
    }
}
