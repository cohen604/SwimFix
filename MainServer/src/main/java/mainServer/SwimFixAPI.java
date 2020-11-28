package mainServer;

import DTO.ActionResult;
import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;

public class SwimFixAPI {
   private LogicManager logicManager = new LogicManager();

   public ActionResult<FeedbackVideoDTO> uploadVideo(ConvertedVideoDTO convertedVideoDTO) {
      return logicManager.uploadVideo(convertedVideoDTO);
   }
}
