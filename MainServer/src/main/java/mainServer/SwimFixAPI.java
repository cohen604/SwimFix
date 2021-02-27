package mainServer;

import DTO.*;
import Domain.Streaming.*;
import ExernalSystems.MLConnectionHandler;
import ExernalSystems.MLConnectionHandlerProxy;
import mainServer.Completions.ISkeletonsCompletion;
import mainServer.Completions.SkeletonsCompletionBefore;
import mainServer.Interpolations.ISkeletonInterpolation;
import mainServer.Interpolations.LinearInterpolation;
import mainServer.Interpolations.MedianInterpolation;
import mainServer.Interpolations.SkeletonInterpolation;
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
      IFactoryFeedbackVideo iFactoryFeedbackVideo = new FactoryFeedbackVideo();
      MLConnectionHandler mlConnectionHandler = new MLConnectionHandlerProxy();
      ISkeletonInterpolation iSkeletonInterpolation = new SkeletonInterpolation(
              new LinearInterpolation(), new MedianInterpolation());
      ISkeletonsCompletion completion = new SkeletonsCompletionBefore();
      this.logicManager = new LogicManager(iFactoryErrorDetectors, iFactoryVideo,
              iFactoryFeedbackVideo, mlConnectionHandler, iSkeletonInterpolation,
              completion);
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
