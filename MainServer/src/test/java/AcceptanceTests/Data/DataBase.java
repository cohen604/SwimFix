package AcceptanceTests.Data;

import java.util.HashMap;

public class DataBase {

    public static String[][] Users = {
            //email, name
            {"foo@bar.com", "foo"},
            {"goo@bar.com", "goo"},
    };

    public static HashMap<String, String> videos = new HashMap<String, String>() {{
        put("/path/to/video1", "www.video1.stream.com");
        put("/path/to/video2", "www.video22.stream.com");
    }};

}
