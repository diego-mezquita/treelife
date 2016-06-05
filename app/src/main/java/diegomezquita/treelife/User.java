package diegomezquita.treelife;

import java.text.SimpleDateFormat;
import android.content.Context;
import java.util.Date;
import java.util.Locale;

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
    private String userSignInDate;
    private Long id;

    public User(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userProfilePictureUrl = "";
        this.userPassword = userPassword;
        this.CHANGENAMElugaresHabituales = "";
        this.userSignInDate = this.getDateTime();
    }

    private User(String userName, String userEmail, String userProfilePictureUrl, String userPassword, String CHANGENAMElugaresHabituales) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userProfilePictureUrl = userProfilePictureUrl;
        this.userPassword = userPassword;
        this.CHANGENAMElugaresHabituales = CHANGENAMElugaresHabituales;
    }

    private User(String userName, String userEmail, String userProfilePictureUrl, String userPassword, String CHANGENAMElugaresHabituales, Context context) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userProfilePictureUrl = userProfilePictureUrl;
        this.userPassword = userPassword;
        this.CHANGENAMElugaresHabituales = CHANGENAMElugaresHabituales;
        this.userSignInDate = userSignInDate;

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

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // Singleton manager
    public static synchronized User getInstance() {
        return singletoneUser;
    }

    public static synchronized User getInstance(String userName, String userEmail,
                                                String userProfilePictureUrl, String userPassword,
                                                String CHANGENAMElugaresHabituales, Context context) {
        if (singletoneUser == null) {
            singletoneUser = new User(userName, userEmail, userProfilePictureUrl, userPassword,
                    CHANGENAMElugaresHabituales, context);
        }
        return singletoneUser;
    }
}
