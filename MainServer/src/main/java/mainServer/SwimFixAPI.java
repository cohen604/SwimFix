package mainServer;

import DTO.*;
import Domain.Errors.Factories.FactoryElbowError;
import Domain.Errors.Factories.FactoryForearmError;
import Domain.Errors.Factories.FactoryPalmCrossHeadError;
import Domain.PeriodTimeData.FactorySwimmingPeriodTime;
import Domain.PeriodTimeData.IFactorySwimmingPeriodTime;
import Domain.StatisticsData.FactoryStatistic;
import Domain.StatisticsData.IFactoryStatistic;
import Domain.Streaming.*;
import DomainLogic.PdfDrawing.IPdfDrawer;
import DomainLogic.PdfDrawing.PdfDrawer;
import Domain.Drawing.IFactoryDraw;
import ExernalSystems.MLConnectionHandler;
import ExernalSystems.MLConnectionHandlerProxy;
import Storage.User.UserDao;
import DomainLogic.Completions.ISkeletonsCompletion;
import DomainLogic.Completions.SkeletonsCompletionBefore;
import DomainLogic.FileLoaders.ISkeletonsLoader;
import DomainLogic.FileLoaders.SkeletonsLoader;
import DomainLogic.Interpolations.*;
import mainServer.Providers.*;
import Domain.Drawing.FactoryDraw;
import DomainLogic.SwimmingErrorDetectors.FactoryErrorDetectors;
import DomainLogic.SwimmingErrorDetectors.IFactoryErrorDetectors;
import mainServer.Providers.Interfaces.*;

import java.util.List;

public class SwimFixAPI {

   private LogicManager logicManager;

   public SwimFixAPI() {
      IUserProvider userProvider = new UserProvider(new UserDao());

      IFactoryDraw iFactoryDraw = new FactoryDraw();
      IFactoryErrorDetectors iFactoryErrorDetectors = new FactoryErrorDetectors(
              new FactoryElbowError(iFactoryDraw),
              new FactoryPalmCrossHeadError(iFactoryDraw),
              new FactoryForearmError(iFactoryDraw)
      );
      IDetectProvider detectProvider = new DetectProvider(iFactoryErrorDetectors);
      IFactoryVideo iFactoryVideo = new FactoryVideo();
      IFactoryFeedbackVideo iFactoryFeedbackVideo = new FactoryFeedbackVideo();
      IFactorySkeletonInterpolation iFactorySkeletonInterpolation = new FactorySkeletonInterpolation();
      ISkeletonsCompletion completionBefore = new SkeletonsCompletionBefore();
      MLConnectionHandler mlConnectionHandler = new MLConnectionHandlerProxy();
      ISkeletonsLoader skeletonsLoaderFeedback = new SkeletonsLoader(); // TODO add only one
      IFactoryVideoHandler iFactoryVideoHandler =  new FactoryVideoHandler();
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

      IEmailSenderProvider emailSenderProvider = new EmailSenderProvider();

      IZipProvider zipProvider = new ZipProvider();

      this.logicManager = new LogicManager(
              userProvider,
              feedbackProvider,
              skeletonsLoaderLogic,
              statisticProvider,
              reportProvider,
              emailSenderProvider,
              zipProvider);
   }

   public ActionResult<UserDTO> login(UserDTO userDTO) {
      return logicManager.login(userDTO);
   }


   public ActionResult<Boolean> logout(UserDTO user) {
      return this.logicManager.logout(user);
   }


   public ActionResult<UserPermissionsDTO> getPermissions(UserDTO user) {
      return this.logicManager.getPermissions(user);
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

    public ActionResult<Boolean> invite(UserDTO userDTO, String to) {
      return logicManager.invite(userDTO, to);
   }

   public ActionResult<FileDownloadDTO> downloadFile(UserDTO userDTO, String root, String email, String folder, String fileName) {
      return logicManager.downloadFile(userDTO, root, email, folder, fileName);
   }

   public ActionResult<FileDownloadDTO> downloadFilesAsZip(UserDTO user, String[] files) {
      return logicManager.downloadFilesAsZip(user, files);
   }


   public ActionResult<List<DateDTO>> getSwimmerHistoryDays(UserDTO userDto) {
      return logicManager.getSwimmerHistoryDays(userDto);
   }

   public ActionResult<List<FeedbackVideoStreamer>> getSwimmerHistoryPoolsBy(UserDTO userDto, DateDTO date) {
      return logicManager.getSwimmerHistoryPoolsByDay(userDto, date);
   }

   public ActionResult<Boolean> deleteFeedback(UserDTO userDTO, DateDTO dateDTO, String path) {
      return logicManager.deleteFeedbackByID(userDTO, dateDTO, path);
   }

}
