package twitter.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import twitter.datamodels.login.LoginRequest;
import twitter.datamodels.login.LoginResponse;
import twitter.datamodels.user.User;
import twitter.db.UserDB;
import twitter.exceptions.UserNotLoggedInException;

import java.util.UUID;

@RestController
public class LoginController {
    UserDB db = UserDB.getInstance();

    @RequestMapping(value = "/api/auth/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest login) throws UserNotLoggedInException {
        LoginResponse response;
        User user = db.getUser(login.getUsername());

        if (user == null) {
            return new ResponseEntity<>(new LoginResponse(false, "", ""), HttpStatus.NOT_FOUND);
        } else if (login.getUsername().equals(user.getUserName()) && login.getPassword().equals(user.getUserPassword())) {
            String token = String.valueOf(UUID.randomUUID());

            response = new LoginResponse(true, token, user.getUserId());
            db.setToken(user.getUserName(), token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = new LoginResponse(false, null, null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/api/auth/me", method = RequestMethod.GET)
    public ResponseEntity getLoggedInUser() {
        return new ResponseEntity<>("", HttpStatus.OK);

    }
}
