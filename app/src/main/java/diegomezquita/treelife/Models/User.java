package diegomezquita.treelife.Models;

import java.text.SimpleDateFormat;
import android.content.Context;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import diegomezquita.treelife.DatabaseAccess.DBHelper;

/**
 * Created by diegomezquita on 16/04/16.
 */
public class User {
    private static User singletoneUser;

    private String userName;
    private String userEmail;
    private String userProfilePictureUrl;
    private String userPassword;
    private String CHANGENAMElugaresHabituales;
    private Integer userKiriState;
    private String userSignInDate;
    private Long id;

    public User(Long id, String userName, String userEmail, String userPassword, String userPathToAvatar,
                String userCreatedAt, Integer userKiriState) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userProfilePictureUrl = userPathToAvatar;
        this.userSignInDate = userCreatedAt;
        this.userKiriState = userKiriState;
        this.CHANGENAMElugaresHabituales = "";
    }

    private User(String userName, String userEmail, String userProfilePictureUrl, String userPassword, String CHANGENAMElugaresHabituales) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.CHANGENAMElugaresHabituales = CHANGENAMElugaresHabituales;
    }

    private User(String userName, String userEmail,  String userPassword, String userPathToAvatar, Context context) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userProfilePictureUrl = userPathToAvatar;
        this.userSignInDate = this.getDateTime();

        DBHelper db = DBHelper.getInstance(context);
        this.setId(db.createUser(this));
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserProfilePictureUrl() {
        return userProfilePictureUrl;
    }

    public void setUserProfilePictureUrl(String userProfilePictureUrl) {
        this.userProfilePictureUrl = userProfilePictureUrl;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getCHANGENAMElugaresHabituales() {
        return CHANGENAMElugaresHabituales;
    }

    public void setCHANGENAMElugaresHabituales(String CHANGENAMElugaresHabituales) {
        this.CHANGENAMElugaresHabituales = CHANGENAMElugaresHabituales;
    }

    public String getUserSignInDate() {
        return userSignInDate;
    }

    public void setUserSignInDate(String userSignInDate) {
        this.userSignInDate = userSignInDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public List<RecycleInAction> getActionsList() {
        DBHelper db = DBHelper.getInstance();

        List<RecycleInAction> actionsByUser = db.getActionsByUser(this.getId());
        return actionsByUser;
    }

    // Singleton manager
    public static synchronized User getInstance() {

        return singletoneUser;
    }

    public static synchronized User getInstance(String userName, String userEmail, String userPassword,
                                                String userPathToAvatar, Context context) {
        if (singletoneUser == null) {
            singletoneUser = new User(userName, userEmail, userPassword, userPathToAvatar, context);
        }
        return singletoneUser;
    }
}
