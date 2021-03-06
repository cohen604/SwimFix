package mainServer;

import DTO.*;
import Domain.Streaming.*;
import ExernalSystems.MLConnectionHandler;
import ExernalSystems.MLConnectionHandlerProxy;
import Storage.User.UserDao;
import mainServer.Completions.ISkeletonsCompletion;
import mainServer.Completions.SkeletonsCompletionAfter;
import mainServer.Completions.SkeletonsCompletionBefore;
import mainServer.FileLoaders.ISkeletonsLoader;
import mainServer.FileLoaders.SkeletonsLoader;
import mainServer.Interpolations.*;
import mainServer.Providers.IFeedbackProvider;
import mainServer.Providers.IUserProvider;
import mainServer.Providers.FeedbackProvider;
import mainServer.Providers.UserProvider;
import mainServer.SwimmingErrorDetectors.FactoryDraw;
import mainServer.SwimmingErrorDetectors.FactoryErrorDetectors;
import mainServer.SwimmingErrorDetectors.IFactoryErrorDetectors;

public class SwimFixAPI {

   private LogicManager logicManager;

   public SwimFixAPI() {
      IUserProvider userProvider = new UserProvider(new UserDao());

      IFactoryErrorDetectors iFactoryErrorDetectors = new FactoryErrorDetectors();
      IFactoryVideo iFactoryVideo = new FactoryVideo(new FactoryDraw(), new FactoryVideoHandler());
      IFactoryFeedbackVideo iFactoryFeedbackVideo = new FactoryFeedbackVideo();
      ISkeletonInterpolation iSkeletonInterpolation = new SkeletonInterpolation(
              new LinearInterpolation(), new MedianInterpolation());
      ISkeletonsCompletion completionBefore = new SkeletonsCompletionBefore();
      ISkeletonsCompletion completionAfter = new SkeletonsCompletionAfter();
      MLConnectionHandler mlConnectionHandler = new MLConnectionHandlerProxy();
      ISkeletonsLoader skeletonsLoader = new SkeletonsLoader();
      IFeedbackProvider feedbackProvider = new FeedbackProvider(mlConnectionHandler, iFactoryFeedbackVideo,
              iSkeletonInterpolation, completionBefore,
              completionAfter, iFactoryVideo, iFactoryErrorDetectors, skeletonsLoader);

      this.logicManager = new LogicManager(userProvider, feedbackProvider);
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
