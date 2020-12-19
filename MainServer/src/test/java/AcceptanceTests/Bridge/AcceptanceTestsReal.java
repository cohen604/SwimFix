package AcceptanceTests.Bridge;

import DTO.ActionResult;
import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;
import mainServer.SwimFixAPI;

public class AcceptanceTestsReal implements AcceptanceTestsBridge {

    private SwimFixAPI swimFixAPI;

    public AcceptanceTestsReal() {
        this.swimFixAPI = SwimFixDriver.swimFixAPIFactory();
    }

    @Override
    public ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(ConvertedVideoDTO convertedVideoDTO) {
        if(this.swimFixAPI != null) {
            return this.swimFixAPI.uploadVideoForStreamer(convertedVideoDTO);
        }
        return null;
    }

    @Override
    public ActionResult<FeedbackVideoDTO> streamFile(String path) {
        if(this.swimFixAPI != null) {
            return this.swimFixAPI.streamFile(path);
        }
        return null;
    }
}
