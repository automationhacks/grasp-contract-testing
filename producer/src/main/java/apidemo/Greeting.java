package apidemo;

/*
Tut from: https://spring.io/guides/gs/rest-service/
Below is a simple representation of a Greeting resource

Our api would return a body payload of:

{
    "id": 1,
    "content": "Hello World"
}
 */

public class Greeting {
    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }
}
