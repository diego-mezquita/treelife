package diegomezquita.treelife.Models;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import diegomezquita.treelife.DatabaseAccess.DBHelper;

/**
 * Created by diegomezquita on 04/06/16.
 */
public class RecycleInAction {

    private User user;
    private Container container;
    private Long id;
    private Integer points = 3;
    private String time;

    public RecycleInAction() {
        this.setUser(User.getInstance());
        this.setContainer(new Container());
    }

    public RecycleInAction(Container container) {
        this.setUser(User.getInstance());
        this.setContainer(new Container());
        this.setTime(this.getDateTime());
    }

    public RecycleInAction(Container container, Context context) {
        this.setUser(User.getInstance());
        this.setContainer(container);
        this.setTime(this.getDateTime());

        DBHelper db = DBHelper.getInstance(context);
        this.setId(db.createRecycleInAction(this));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }


    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
