import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import twitter.datamodels.user.User;
import twitter.db.UserDB;
import twitter.exceptions.UserNotLoggedInException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RunWith(PactRunner.class)
@Provider("TwitterProvider")
@PactBroker(host = "localhost", port = "80")
public class ContractTest {
    UserDB db = UserDB.getInstance();

    @State("")
    public void defaultState() {

    }

    @State("user is logged in")
    public Map<String, String> givenUserIsLoggedIn() throws UserNotLoggedInException {
        String userName = "sayantan";

        User user = db.getUser(userName);
        String token = String.valueOf(UUID.randomUUID());

        db.setToken(user.getUserName(), token);

        Map<String, String> headers  = new HashMap<>();
        headers.put("Authorization", String.format("Bearer %s", token));
        return headers;
    }

    @TestTarget
    public final Target target = new HttpTarget("http", "localhost", 8082, "", true);
}
