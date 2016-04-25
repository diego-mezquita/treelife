package diegomezquita.treelife;

import java.util.Date;

/**
 * Created by diegomezquita on 16/04/16.
 */
public class User {
    private String userName;
    private String userEmail;
    private String userProfilePictureUrl;
    private String userPassword;
    private String CHANGENAMElugaresHabituales;
    private Date userSignInDate;

    public User(String userName, String userEmail, String userProfilePictureUrl, String userPassword, String CHANGENAMElugaresHabituales, Date userSignInDate) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userProfilePictureUrl = userProfilePictureUrl;
        this.userPassword = userPassword;
        this.CHANGENAMElugaresHabituales = CHANGENAMElugaresHabituales;
        this.userSignInDate = userSignInDate;
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
}
