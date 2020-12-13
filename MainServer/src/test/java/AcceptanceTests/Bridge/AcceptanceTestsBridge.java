package AcceptanceTests.Bridge;

import DTO.ActionResult;
import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;

public interface AcceptanceTestsBridge {
    ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(ConvertedVideoDTO convertedVideoDTO);
    ActionResult<FeedbackVideoDTO> streamFile(String path);
}
