package twitter.login;

import twitter.datamodels.login.LoginResponse;
import twitter.datamodels.user.User;
import twitter.db.UserDB;
import twitter.exceptions.UserNotLoggedInException;

import java.util.UUID;

public class LoginTokens {
    UserDB db = UserDB.getInstance();

    public LoginResponse generateTokenAndLoginUser(User user) throws UserNotLoggedInException {
        LoginResponse response;
        String token = String.valueOf(UUID.randomUUID());

        response = new LoginResponse(true, token, user.getUserId());
        db.setToken(user.getUserName(), token);
        return response;
    }
}
