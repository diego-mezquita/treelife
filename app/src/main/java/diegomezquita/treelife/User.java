package diegomezquita.treelife;

import android.content.Context;

import java.util.Date;

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
    private Date userSignInDate;
    private Long id;

    private User(String userName, String userEmail, String userProfilePictureUrl, String userPassword, String CHANGENAMElugaresHabituales, Date userSignInDate) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userProfilePictureUrl = userProfilePictureUrl;
        this.userPassword = userPassword;
        this.CHANGENAMElugaresHabituales = CHANGENAMElugaresHabituales;
        this.userSignInDate = userSignInDate;
    }

    private User(String userName, String userEmail, String userProfilePictureUrl, String userPassword, String CHANGENAMElugaresHabituales, Date userSignInDate, Context context) {
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

    public Date getUserSignInDate() {
        return userSignInDate;
    }

    public void setUserSignInDate(Date userSignInDate) {
        this.userSignInDate = userSignInDate;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    // Singleton manager
    public static synchronized User getInstance() {
        return singletoneUser;
    }

    public static synchronized User getInstance(String userName, String userEmail,
                                                String userProfilePictureUrl, String userPassword,
                                                String CHANGENAMElugaresHabituales, Date userSignInDate,
                                                Context context) {
        if (singletoneUser == null) {
            singletoneUser = new User(userName, userEmail, userProfilePictureUrl, userPassword,
                    CHANGENAMElugaresHabituales, userSignInDate, context);
        }
        return singletoneUser;
    }
}
