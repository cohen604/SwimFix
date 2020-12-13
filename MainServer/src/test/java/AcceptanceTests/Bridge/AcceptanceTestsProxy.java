package AcceptanceTests.Bridge;

import DTO.ActionResult;
import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;

public class AcceptanceTestsProxy implements AcceptanceTestsBridge {

    private AcceptanceTestsReal acceptanceTestsReal;

    public AcceptanceTestsProxy() {
        acceptanceTestsReal = new AcceptanceTestsReal();
    }

    @Override
    public ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(ConvertedVideoDTO convertedVideoDTO) {
        if(acceptanceTestsReal != null) {
            return acceptanceTestsReal.uploadVideoForStreamer(convertedVideoDTO);
        }
        return null;
    }

    @Override
    public ActionResult<FeedbackVideoDTO> streamFile(String path) {
        if(acceptanceTestsReal != null) {
            return acceptanceTestsReal.streamFile(path);
        }
        return null;
    }
}
