package twitter.datamodels.user;

public class User {
    public User(String userFullName, String userName, String userPassword, String userId) {
        this.userFullName = userFullName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userId = userId;
    }

    private String userFullName;
    private String userName;
    private String userPassword;
    private String userLoginToken;
    private String userId;

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserLoginToken() {
        return userLoginToken;
    }

    public void setUserLoginToken(String userLoginToken) {
        this.userLoginToken = userLoginToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
