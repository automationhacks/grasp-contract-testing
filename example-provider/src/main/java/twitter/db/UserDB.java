package twitter.db;

import twitter.datamodels.user.User;
import twitter.exceptions.UserNotLoggedInException;

import java.util.LinkedList;
import java.util.List;



public class UserDB {
    private static List<User> users = new LinkedList<>();
    private static UserDB instance;

    private UserDB() {

    }

    public static UserDB getInstance() {
        if (instance == null) {
            instance = new UserDB();
        }
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void setToken(String userName, String token) throws UserNotLoggedInException {
        boolean updated = false;

        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                user.setUserLoginToken(token);
                updated = true;
            }
        }

        if (!updated) {
            throw new UserNotLoggedInException(String.format("User %s not found in db. Please register", userName));
        }
    }

    public User getUser(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public User getUserByToken(String token) {
        for (User user : users) {
            if (user.getUserLoginToken().equals(token)) {
                return user;
            }
        }
        return null;
    }

}
