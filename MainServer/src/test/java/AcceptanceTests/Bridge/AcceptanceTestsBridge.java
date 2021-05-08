package AcceptanceTests.Bridge;

import DTO.ActionResult;
import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;

public interface AcceptanceTestsBridge {
    //here need to be all the function from the Swimfix api
    boolean uploadVideoForStreamer(String user, byte[] video);
    String streamFile(String user, String path);
    boolean login(String uid, String email, String name);

    boolean logout(String uid);
}
