package twitter.exceptions;

public class UserNotLoggedInException extends Exception {
    public UserNotLoggedInException(String message) {
        super(message);
    }
}
