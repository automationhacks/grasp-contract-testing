package twitter;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import twitter.datamodels.login.LoginResponse;
import twitter.datamodels.user.User;
import twitter.db.UserDB;
import twitter.exceptions.UserNotLoggedInException;
import twitter.login.LoginTokens;

@Profile("pact")
@RestController
public class PactController {
    UserDB db = UserDB.getInstance();

    @RequestMapping(value = "/pactStateChange", method = RequestMethod.POST)
    public PactStateChangeResponseDTO providerState(@RequestBody PactState body) throws UserNotLoggedInException {
        PactStateChangeResponseDTO pactStateChangeResponse = new PactStateChangeResponseDTO();

        if ("User is already logged in".equals(body.getState())) {
            User user = db.getUser("sayantan");
            LoginTokens tokens = new LoginTokens();
            LoginResponse response = tokens.generateTokenAndLoginUser(user);
            pactStateChangeResponse.setState(response.getToken());
        }

        pactStateChangeResponse.setState(body.getState());
        return pactStateChangeResponse;
    }
}

class PactState {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

class PactStateChangeResponseDTO {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
