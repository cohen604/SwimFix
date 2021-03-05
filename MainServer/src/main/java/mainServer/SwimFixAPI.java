package mainServer;

import DTO.*;
import Domain.Streaming.*;
import ExernalSystems.MLConnectionHandler;
import ExernalSystems.MLConnectionHandlerProxy;
import Storage.User.UserDao;
import mainServer.Completions.ISkeletonsCompletion;
import mainServer.Completions.SkeletonsCompletionAfter;
import mainServer.Completions.SkeletonsCompletionBefore;
import mainServer.Interpolations.*;
import mainServer.Providers.IStreamProvider;
import mainServer.Providers.IUserProvider;
import mainServer.Providers.StreamProvider;
import mainServer.Providers.UserProvider;
import mainServer.SwimmingErrorDetectors.FactoryDraw;
import mainServer.SwimmingErrorDetectors.FactoryErrorDetectors;
import mainServer.SwimmingErrorDetectors.IFactoryDraw;
import mainServer.SwimmingErrorDetectors.IFactoryErrorDetectors;

public class SwimFixAPI {

   private LogicManager logicManager;

   public SwimFixAPI() {
      IUserProvider userProvider = new UserProvider(new UserDao());
      IStreamProvider streamProvider = new StreamProvider();
      IFactoryErrorDetectors iFactoryErrorDetectors = new FactoryErrorDetectors();
      IFactoryVideo iFactoryVideo = new FactoryVideo(new FactoryDraw(), new FactoryVideoHandler());
      IFactoryFeedbackVideo iFactoryFeedbackVideo = new FactoryFeedbackVideo();

      ISkeletonInterpolation iSkeletonInterpolation = new SkeletonInterpolation(
              new LinearInterpolation(), new MedianInterpolation());
      ISkeletonsCompletion completionBefore = new SkeletonsCompletionBefore();
      ISkeletonsCompletion completionAfter = new SkeletonsCompletionAfter();

      MLConnectionHandler mlConnectionHandler = new MLConnectionHandlerProxy();

      this.logicManager = new LogicManager(
              userProvider, streamProvider ,iFactoryErrorDetectors, iFactoryVideo, iFactoryFeedbackVideo,
              iSkeletonInterpolation, completionBefore, completionAfter, mlConnectionHandler);

   }

   public ActionResult<UserDTO> login(UserDTO userDTO) {
      return logicManager.login(userDTO);
   }

   public ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(UserDTO userDTO, ConvertedVideoDTO convertedVideoDTO) {
      return logicManager.uploadVideoForStreamer(userDTO, convertedVideoDTO);
   }

   public ActionResult<FeedbackVideoDTO> streamFile(String path) {
      return logicManager.streamFile(path);
   }

   public ActionResult<FeedbackVideoStreamer> filterFeedbackVideo(UserDTO userDTO, FeedbackFilterDTO filterDTO) {
      return logicManager.filterFeedbackVideo(userDTO, filterDTO);
   }
}
