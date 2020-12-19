package AcceptanceTests.Bridge;

import DTO.ActionResult;
import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;

public interface AcceptanceTestsBridge {
    //here need to be all the function from the Swimfix api
    ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(ConvertedVideoDTO convertedVideoDTO);
    ActionResult<FeedbackVideoDTO> streamFile(String path);

}
