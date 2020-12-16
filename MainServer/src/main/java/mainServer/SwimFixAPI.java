package mainServer;

import DTO.*;

public class SwimFixAPI {
   private LogicManager logicManager = new LogicManager();

   public ActionResult<SwimmerDTO> login(SwimmerDTO swimmerDTO) {
      return logicManager.login(swimmerDTO);
   }

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
