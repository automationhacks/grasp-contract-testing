
package twitter.datamodels.login;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class LoggedInUser {

    public LoggedInUser(String _id, String name, String username, long _V) {
        this._id = _id;
        this.name = name;
        this.username = username;
        this._V = _V;
    }

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
