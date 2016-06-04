package diegomezquita.treelife;

/**
 * Created by diegomezquita on 04/06/16.
 */
public class RecycleInAction {/*
    TODO uncomment after add User class
    private User user;*/
    private Container container;

    public RecycleInAction() {
        // this.user = TODO figure out how to get the user - Singleton is a possible option
        this.container = new Container();
    }

    public RecycleInAction(Container container) {
        // this.user = TODO figure out how to get the user - Singleton is a possible option
        this.container = container;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
