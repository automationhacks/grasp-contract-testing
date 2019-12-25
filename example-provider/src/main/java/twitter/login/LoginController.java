package twitter.login;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import twitter.constants.UserIds;

import java.io.IOException;
import java.util.UUID;

@RestController
public class LoginController {
    @RequestMapping(value = "/api/auth/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO login) {
        LoginResponse response;
        String userName = "sayantan";
        String passWord = "secretpwd";

        if (login.getUsername().equals(userName) && login.getPassword().equals(passWord)) {
            String token = String.valueOf(UUID.randomUUID());
            String userId = UserIds.userIds.get(userName);

            response = new LoginResponse(true, token, userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            response = new LoginResponse(false, null, null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }


}
