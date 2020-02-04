package twitter.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import twitter.datamodels.login.LoggedInUser;
import twitter.datamodels.login.LoginRequest;
import twitter.datamodels.login.LoginResponse;
import twitter.datamodels.user.User;
import twitter.db.UserDB;
import twitter.exceptions.UserNotLoggedInException;

import java.util.UUID;

@RestController
public class LoginController {
    UserDB db = UserDB.getInstance();
    LoginTokens tokens = new LoginTokens();

    @RequestMapping(value = "/api/auth/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) throws UserNotLoggedInException {
        User user = db.getUser(login.getUsername());

        if (isUserNameAndPasswordValid(login, user)) {
            return userSuccessfullyLogsIn(user);
        } else if (user == null) {
            return userNotFound();
        } else {
            return userNotAuthorized();
        }
    }

    @RequestMapping(value = "/api/auth/me", method = RequestMethod.GET)
    public ResponseEntity getLoggedInUser(@RequestHeader("Authorization") String authorization) {
        String token = authorization.split(" ")[1];
        User user = db.getUserByToken(token);

        LoggedInUser loggedInUser = new LoggedInUser(user.getUserId(), user.getUserFullName(), user.getUserName(), 0);
        return new ResponseEntity<>(loggedInUser, HttpStatus.OK);

    }

    private boolean isUserNameAndPasswordValid(@RequestBody LoginRequest login, User user) {
        return login.getUsername().equals(user.getUserName()) && login.getPassword().equals(user.getUserPassword());
    }

    private ResponseEntity<LoginResponse> userNotAuthorized() {
        LoginResponse response;
        response = new LoginResponse(false, null, null);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<LoginResponse> userNotFound() {
        return new ResponseEntity<>(new LoginResponse(false, "", ""), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<LoginResponse> userSuccessfullyLogsIn(User user) throws UserNotLoggedInException {
        LoginResponse response;
        response = tokens.generateTokenAndLoginUser(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

