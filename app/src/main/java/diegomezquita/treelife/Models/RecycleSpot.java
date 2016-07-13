package diegomezquita.treelife.Models;

import diegomezquita.treelife.Models.Container;

/**
 * Created by diegomezquita on 02/06/16.
 */
public class RecycleSpot {
    private Container container;
    /* TODO develop these classes
    private RecentActivityInfo recentActivity;
    private SpotRanking spotRanking;
    */

    public RecycleSpot() {

    }

    public RecycleSpot(Container container) {
        this.container = container;
    }

    public String getTitle() {
        return this.container.getTitle();
    }

    // TODO remove the set methods if they are not used/needed
    public void setTitle(String title) {
        this.container.setTitle(title);
    }

    public String getPlace() {
        return this.container.getPlace();
    }

    public void setPlace(String place) {
        this.container.setPlace(place);
    }

    public Double getLongitude() {
        return this.container.getLongitude();
    }

    public void setLongitude(Double longitude) {
        this.container.setLongitude(longitude);
    }

    public Double getLatitude() {
        return this.container.getLatitude();
    }

    public void setLatitude(Double latitude) {
        this.container.setLatitude(latitude);
    }

    public String getType() {
        return this.container.getType();
    }

    public void setType(String type) {
        this.container.setType(type);
    }


}
