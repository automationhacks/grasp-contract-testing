import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactFolder;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import twitter.Application;
import twitter.datamodels.login.LoginResponse;
import twitter.datamodels.user.User;
import twitter.db.UserDB;
import twitter.exceptions.UserNotLoggedInException;
import twitter.login.LoginTokens;

import java.util.HashMap;
import java.util.Map;

@RunWith(PactRunner.class)
@Provider("TwitterProvider")
@PactFolder("pacts")
public class PactVerificationTest {
    UserDB db = UserDB.getInstance();

    @State("User is already logged in")
    public Map<String, String> loginExistingUser() throws UserNotLoggedInException {
        User user = db.getUser("sayantan");
        LoginTokens tokens = new LoginTokens();
        LoginResponse response = tokens.generateTokenAndLoginUser(user);

        String token = response.getToken();
        Map<String, String> setupData = new HashMap<>();
        setupData.put("token", token);
        return setupData;

    }

    // Annotation denotes Target that will be used for tests
    @TestTarget
    public final Target target = new HttpTarget(8082);

    private static ConfigurableWebApplicationContext application;

    @BeforeClass
    public static void start() {
        application = (ConfigurableWebApplicationContext)
                SpringApplication.run(Application.class);
    }

}

