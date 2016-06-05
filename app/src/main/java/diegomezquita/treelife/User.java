package diegomezquita.treelife;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by diegomezquita on 16/04/16.
 */
public class User {
    private String userName;
    private String userEmail;
    private String userProfilePictureUrl;
    private String userPassword;
    private String CHANGENAMElugaresHabituales;
    private String userSignInDate;

    public User(String userName, String userEmail, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userProfilePictureUrl = "";
        this.userPassword = userPassword;
        this.CHANGENAMElugaresHabituales = "";
        this.userSignInDate = this.getDateTime();
    }

    public User(String userName, String userEmail, String userProfilePictureUrl, String userPassword, String CHANGENAMElugaresHabituales) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userProfilePictureUrl = userProfilePictureUrl;
        this.userPassword = userPassword;
        this.CHANGENAMElugaresHabituales = CHANGENAMElugaresHabituales;
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

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
