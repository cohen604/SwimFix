package mainServer;

import DTO.*;
import Domain.StatisticsData.IStatistic;
import Domain.Streaming.*;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.UserData.Interfaces.IUser;
import DomainLogic.FileLoaders.ISkeletonsLoader;
import Storage.User.UserDao;
import mainServer.Providers.Interfaces.IFeedbackProvider;
import mainServer.Providers.Interfaces.IReportProvider;
import mainServer.Providers.Interfaces.IStatisticProvider;
import mainServer.Providers.Interfaces.IUserProvider;
import org.apache.tomcat.util.http.fileupload.FileUtils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class LogicManager {

    private IUserProvider _userProvider;
    private IFeedbackProvider _feedbackProvider;
    private ISkeletonsLoader _skeletonLoader;
    private IStatisticProvider _statisticProvider;
    private IReportProvider _reportProvider;

    public LogicManager(IUserProvider userProvider,
                        IFeedbackProvider streamProvider,
                        ISkeletonsLoader skeletonLoader,
                        IStatisticProvider statisticProvider,
                        IReportProvider reportProvider) {
        _userProvider = userProvider;
        _feedbackProvider = streamProvider;
        _skeletonLoader = skeletonLoader;
        _statisticProvider = statisticProvider;
        _reportProvider = reportProvider;
        createClientsDir();
        _userProvider.reload();
    }

    /**
     * create the client directory in the server memory
     */
    private void createClientsDir() {
        try {
            Path path = Paths.get("clients");
            // TODO - check if good
            if (!Files.isDirectory(path)) {
                Files.createDirectory(path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * delete all the files in the given path
     * @param file = the file to delete
     * @throws IOException - if fail
     */
    void delete(File file) throws IOException {
        if (file.isDirectory()) {
            FileUtils.deleteDirectory(file);
        }
        if (!file.delete())
            throw new FileNotFoundException("Failed to delete file: " + file);
    }

    /**
     * The function handle login of swimmers to the system
     * @param userDTO the swimmer information
     * @return true
     */
    public ActionResult<UserDTO> login(UserDTO userDTO) {
        if(_userProvider.login(userDTO)) {
            return new ActionResult<>(Response.SUCCESS, userDTO);
        }
        return new ActionResult<>(Response.FAIL,null);
    }

    /***
     * The function handle logout of swimmers from the system
     * @param user the swimmer information
     * @return
     */
    public ActionResult<Boolean> logout(UserDTO user) {
        if(_userProvider.logout(user)) {
            return new ActionResult<>(Response.SUCCESS, true);
        }
        return new ActionResult<>(Response.FAIL,null);
    }

    /**
     * The function handle upload video that want a streaming result
     * @param convertedVideoDTO the video we want to view
     * @return the streaming path for the feedback video
     * @ pre condition - the feedback video we are generating doesn't exits!
     */
    public ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(UserDTO userDTO, ConvertedVideoDTO convertedVideoDTO) {
        IUser user = _userProvider.getUser(userDTO);
        if(user != null) {
            // create video
            List<String> detectorsNames = new LinkedList<>();
            IFeedbackVideo feedbackVideo = _feedbackProvider.generateFeedbackVideo(
                    convertedVideoDTO, user.getVideosPath(),
                    user.getFeedbacksPath(), user.getSkeletonsPath(), user.getMLSkeletonsPath(), detectorsNames);
            if (feedbackVideo != null) {
                _userProvider.addFeedbackToUser(user, feedbackVideo);
                FeedbackVideoStreamer feedbackVideoStreamer = feedbackVideo.generateFeedbackStreamer(detectorsNames);
                if (feedbackVideoStreamer != null) {
                    return new ActionResult<>(Response.SUCCESS, feedbackVideoStreamer);
                }
            }
        }
        //TODO what to return when fail
        return new ActionResult<>(Response.FAIL, null);
    }

    /**
     * The function handle a streaming file request
     * @param path the path tp the file
     * @return the bytes for the file
     */
    public ActionResult<FeedbackVideoDTO> streamFile(String path) {
        //TODO need here to be access check
        try {
            FeedbackVideoDTO output = _feedbackProvider.streamFeedback(path);
            return new ActionResult<>(Response.SUCCESS, output);
        } catch (Exception e) {
            //TODO return here error
            System.out.println(e.getMessage());
        }
        //TODO return error
        //TODO maybe always generate a video if it a error video then return error video ?
        return null;
    }

    /**
     * The function return a researcher report from the given video and labels
     * @param userDTO - the user information
     * @param videoDTO - the video to created the analyze
     * @param fileDTO - the labels file
     * @return
     */
    public ActionResult<ResearcherReportDTO> getResearcherReport(UserDTO userDTO, ConvertedVideoDTO videoDTO, FileDTO fileDTO) {
        IUser user = _userProvider.getUser(userDTO);
        if(user != null && user.isLogged() && user.isResearcher()) {
            // create video
            List<String> detectorsNames = new LinkedList<>();
            IFeedbackVideo feedbackVideo = _feedbackProvider.generateFeedbackVideo(
                    videoDTO, user.getVideosPath(),
                    user.getFeedbacksPath(), user.getSkeletonsPath(), user.getMLSkeletonsPath(), detectorsNames);
            if (feedbackVideo != null) {
                // add a graph to the file
                List<ISwimmingSkeleton> raw = _skeletonLoader.read(fileDTO.getBytes());
                List<ISwimmingSkeleton> model = _skeletonLoader.read(feedbackVideo.getMLSkeletonsPath());
                List<ISwimmingSkeleton> modelAndInterpolation = feedbackVideo.getSwimmingSkeletons();
                IStatistic statistic = _statisticProvider.analyze(raw, model, modelAndInterpolation);
                String pdfPath = _reportProvider.generateReport(
                        raw,
                        model,
                        modelAndInterpolation,
                        user.getReportsPath(),
                        statistic,
                        feedbackVideo.getSwimmingPeriodTime(),
                        feedbackVideo.getSwimmingErrors());
                ResearcherReportDTO reportDTO = new ResearcherReportDTO(
                        feedbackVideo.getPath(),
                        feedbackVideo.getSkeletonsPath(),
                        pdfPath);
                return new ActionResult<>(Response.SUCCESS, reportDTO);
            }
        }
        return new ActionResult<>(Response.FAIL, null);
    }

//    public ActionResult<List<FeedbackVideoStreamer>> getSwimmerHistory(UserDTO userDto) {
//        IUser user = _userProvider.getUser(userDto);
//        Collection<IFeedbackVideo> feedbacks = user.getFeedbacks();
//        for (IFeedbackVideo v : feedbacks) {
//            String path = v.getPath();
//            File file = new File(path);
//        }
//        return new ActionResult<>(Response.SUCCESS, history);
//    }
}

