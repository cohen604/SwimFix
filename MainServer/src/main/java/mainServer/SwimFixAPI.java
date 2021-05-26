package mainServer;

import DTO.*;
import DTO.AdminDTOs.SummaryDTO;
import DTO.FeedbackDTOs.ConvertedVideoDTO;
import DTO.FeedbackDTOs.FeedbackVideoDTO;
import DTO.FeedbackDTOs.FeedbackVideoStreamer;
import DTO.ResearcherDTOs.FileDTO;
import DTO.ResearcherDTOs.FileDownloadDTO;
import DTO.ResearcherDTOs.ResearcherReportDTO;
import DTO.SwimmerDTOs.DateDTO;
import DTO.SwimmerDTOs.OpenTeamResponseDTO;
import DTO.UserDTOs.UserDTO;
import DTO.UserDTOs.UserPermissionsDTO;
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
import Storage.Feedbacks.FeedbacksDao;
import Storage.Feedbacks.IFeedbackDao;
import Storage.Swimmer.SwimmerDao;
import Storage.Team.TeamDao;
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

   public SwimFixAPI(String dbName) {
      IUserProvider userProvider = new UserProvider(
              new UserDao(),
              new SwimmerDao(),
              new TeamDao());

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
      IFeedbackDao iFeedbackDao = new FeedbacksDao();

      IFeedbackProvider feedbackProvider = new FeedbackProvider(mlConnectionHandler, iFactoryFeedbackVideo,
              iFactorySkeletonInterpolation, completionBefore, iFactoryVideo, detectProvider, skeletonsLoaderFeedback,
              iFactoryVideoHandler, iFactoryDraw, iPeriodTimeProvider, iFeedbackDao);

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
              zipProvider,
              dbName);
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

   public ActionResult<List<UserDTO>> findUsersThatNotAdmin(UserDTO userDTO) {
      return logicManager.findUsersThatNotAdmin(userDTO);
   }

   public ActionResult<List<UserDTO>> findUsersThatNotResearcher(UserDTO userDTO) {
      return logicManager.findUsersThatNotResearcher(userDTO);
   }

   public ActionResult<Boolean> addAdmin(UserDTO admin, UserDTO addToUser) {
      return logicManager.addAdmin(admin, addToUser);
   }

   public ActionResult<Boolean> addResearcher(UserDTO admin, UserDTO addToUser) {
      return logicManager.addResearcher(admin, addToUser);
   }

   public ActionResult<SummaryDTO> getSummary(UserDTO admin) {
      return logicManager.getSummary(admin);
   }

   public ActionResult<OpenTeamResponseDTO> openSwimmingTeam(UserDTO coachDTO, String teamName) {
      return logicManager.addCoach(coachDTO, teamName);
   }
}
