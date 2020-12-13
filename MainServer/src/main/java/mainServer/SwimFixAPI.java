package mainServer;

import DTO.ActionResult;
import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;

public class SwimFixAPI {
   private LogicManager logicManager = new LogicManager();

   public ActionResult<FeedbackVideoDTO> uploadVideoForDownload(ConvertedVideoDTO convertedVideoDTO) {
      return logicManager.uploadVideoForDownload(convertedVideoDTO);
   }

   public ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(ConvertedVideoDTO convertedVideoDTO) {
      return logicManager.uploadVideoForStreamer(convertedVideoDTO);
   }

   public ActionResult<FeedbackVideoDTO> streamFile(String path) {
      return logicManager.streamFile(path);
   }
}
