package twitter;

import model.twitter.authprofile.AuthProfile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProviderService {
    private String backendUrl = "http://localhost:8080/api/auth/me";

    public String getBackendUrl() {
        return backendUrl;
    }

    public void setBackendUrl(String backendUrl) {
        this.backendUrl = backendUrl;
    }

    public AuthProfile getProfile() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(getBackendUrl(), AuthProfile.class);
    }
}
