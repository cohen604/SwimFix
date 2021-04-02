package mainServer;

import DTO.*;
import Domain.PeriodTimeData.FactorySwimmingPeriodTime;
import Domain.PeriodTimeData.IFactorySwimmingPeriodTime;
import Domain.StatisticsData.FactoryStatistic;
import Domain.StatisticsData.IFactoryStatistic;
import Domain.Streaming.*;
import DomainLogic.PdfDrawing.IPdfDrawer;
import DomainLogic.PdfDrawing.PdfDrawer;
import DomainLogic.SwimmingErrorDetectors.IFactoryDraw;
import ExernalSystems.MLConnectionHandler;
import ExernalSystems.MLConnectionHandlerProxy;
import Storage.User.UserDao;
import DomainLogic.Completions.ISkeletonsCompletion;
import DomainLogic.Completions.SkeletonsCompletionBefore;
import DomainLogic.FileLoaders.ISkeletonsLoader;
import DomainLogic.FileLoaders.SkeletonsLoader;
import DomainLogic.Interpolations.*;
import mainServer.Providers.*;
import DomainLogic.SwimmingErrorDetectors.FactoryDraw;
import DomainLogic.SwimmingErrorDetectors.FactoryErrorDetectors;
import DomainLogic.SwimmingErrorDetectors.IFactoryErrorDetectors;
import mainServer.Providers.Interfaces.*;

public class SwimFixAPI {

   private LogicManager logicManager;

   public SwimFixAPI() {
      IUserProvider userProvider = new UserProvider(new UserDao());

      IFactoryErrorDetectors iFactoryErrorDetectors = new FactoryErrorDetectors();
      IDetectProvider detectProvider = new DetectProvider(iFactoryErrorDetectors);
      IFactoryVideo iFactoryVideo = new FactoryVideo();
      IFactoryFeedbackVideo iFactoryFeedbackVideo = new FactoryFeedbackVideo();
      IFactorySkeletonInterpolation iFactorySkeletonInterpolation = new FactorySkeletonInterpolation();
      ISkeletonsCompletion completionBefore = new SkeletonsCompletionBefore();
      MLConnectionHandler mlConnectionHandler = new MLConnectionHandlerProxy();
      ISkeletonsLoader skeletonsLoaderFeedback = new SkeletonsLoader();
      IFactoryVideoHandler iFactoryVideoHandler =  new FactoryVideoHandler();
      IFactoryDraw iFactoryDraw = new FactoryDraw();
      IFactorySwimmingPeriodTime factorySwimmingPeriodTime = new FactorySwimmingPeriodTime();
      IPeriodTimeProvider iPeriodTimeProvider = new PeriodTimeProvider(factorySwimmingPeriodTime);
      IFeedbackProvider feedbackProvider = new FeedbackProvider(mlConnectionHandler, iFactoryFeedbackVideo,
              iFactorySkeletonInterpolation, completionBefore, iFactoryVideo, detectProvider, skeletonsLoaderFeedback,
              iFactoryVideoHandler, iFactoryDraw, iPeriodTimeProvider);

      ISkeletonsLoader skeletonsLoaderLogic = new SkeletonsLoader();

      IFactoryStatistic factoryStatistic = new FactoryStatistic();
      IStatisticProvider statisticProvider = new StatisticProvider(factoryStatistic);

      IPdfDrawer graphDrawer = new PdfDrawer();
      IReportProvider reportProvider = new ReportProvider(graphDrawer);

      this.logicManager = new LogicManager(
              userProvider,
              feedbackProvider,
              skeletonsLoaderLogic,
              statisticProvider,
              reportProvider);
   }

   public ActionResult<UserDTO> login(UserDTO userDTO) {
      return logicManager.login(userDTO);
   }


   public ActionResult<Boolean> logout(UserDTO user) {
      return  this.logicManager.logout(user);
   }

   public ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(UserDTO userDTO, ConvertedVideoDTO convertedVideoDTO) {
      return logicManager.uploadVideoForStreamer(userDTO, convertedVideoDTO);
   }

   public ActionResult<FeedbackVideoDTO> streamFile(String path) {
      return logicManager.streamFile(path);
   }

   public ActionResult<ResearcherReportDTO> getResearcherReport(UserDTO userDTO, ConvertedVideoDTO videoDTO, FileDTO fileDTO) {
      return logicManager.getResearcherReport(userDTO, videoDTO, fileDTO);
   
   }
}
