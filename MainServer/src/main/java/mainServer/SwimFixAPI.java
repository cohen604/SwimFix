package mainServer;

import DTO.*;
import Domain.Streaming.FactoryVideo;
import Domain.Streaming.FactoryVideoHandler;
import Domain.Streaming.IFactoryVideo;
import Domain.Streaming.IFactoryVideoHandler;
import mainServer.SwimmingErrorDetectors.FactoryDraw;
import mainServer.SwimmingErrorDetectors.FactoryErrorDetectors;
import mainServer.SwimmingErrorDetectors.IFactoryDraw;
import mainServer.SwimmingErrorDetectors.IFactoryErrorDetectors;

public class SwimFixAPI {

   private LogicManager logicManager;

   public SwimFixAPI() {
      IFactoryDraw iFactoryDraw = new FactoryDraw();
      IFactoryVideoHandler iFactoryVideoHandler = new FactoryVideoHandler();
      IFactoryErrorDetectors iFactoryErrorDetectors = new FactoryErrorDetectors();
      IFactoryVideo iFactoryVideo = new FactoryVideo(iFactoryDraw, iFactoryVideoHandler);
      this.logicManager = new LogicManager(iFactoryErrorDetectors, iFactoryVideo);
   }

   public ActionResult<UserDTO> login(UserDTO userDTO) {
      return logicManager.login(userDTO);
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

   public ActionResult<FeedbackVideoStreamer> filterFeedbackVideo(FeedbackFilterDTO filterDTO) {
      return logicManager.filterFeedbackVideo(filterDTO);
   }
}
