
package twitter.login;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class LoginResponse {

    private Boolean auth;
    private String token;
    @JsonProperty("user_id")
    private String userId;

    public LoginResponse(Boolean auth, String token, String userId) {
        this.auth = auth;
        this.token = token;
        this.userId = userId;
    }

    public Boolean getAuth() {
        return auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
