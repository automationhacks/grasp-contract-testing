
package model.twitter.authprofile;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class AuthProfile {

    @JsonProperty("__v")
    private long _V;
    private String _id;
    private String name;
    private String username;

    public long get_V() {
        return _V;
    }

    public void set_V(long _V) {
        this._V = _V;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
