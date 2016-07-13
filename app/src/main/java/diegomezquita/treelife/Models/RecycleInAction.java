package diegomezquita.treelife.Models;

import android.content.Context;

import diegomezquita.treelife.DatabaseAccess.DBHelper;

/**
 * Created by diegomezquita on 04/06/16.
 */
public class RecycleInAction {

    private User user;
    private Container container;
    private Long id;

    public RecycleInAction() {
        this.setUser(User.getInstance());
        this.container = new Container();
    }

    public RecycleInAction(Container container) {
        this.setUser(User.getInstance());
        this.container = container;
    }

    public RecycleInAction(Container container, Context context) {
        this.setUser(User.getInstance());
        this.container = container;

        DBHelper db = DBHelper.getInstance(context);
        this.setId(db.createRecycleInAction(this, user, container));
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


    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
}
