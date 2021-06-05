package mainServer.Providers;

import DTO.FeedbackDTOs.ConvertedVideoDTO;
import DTO.FeedbackDTOs.FeedbackVideoDTO;
import Domain.PeriodTimeData.ISwimmingPeriodTime;
import Domain.Streaming.*;
import Domain.Summaries.FeedbacksSummary;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Errors.Interfaces.SwimmingError;
import DomainLogic.Interpolations.IFactorySkeletonInterpolation;
import ExernalSystems.MLConnectionHandler;
import DomainLogic.Completions.ISkeletonsCompletion;
import DomainLogic.FileLoaders.ISkeletonsLoader;
import DomainLogic.Interpolations.ISkeletonInterpolation;
import Domain.Drawing.IFactoryDraw;
import Storage.Feedbacks.IFeedbackDao;
import mainServer.Providers.Interfaces.IDetectProvider;
import mainServer.Providers.Interfaces.IFeedbackProvider;
import mainServer.Providers.Interfaces.IPeriodTimeProvider;
import org.opencv.core.Mat;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


public class FeedbackProvider implements IFeedbackProvider {

    private MLConnectionHandler mlConnectionHandler;
    private IFactoryFeedbackVideo iFactoryFeedbackVideo;
    private IFactorySkeletonInterpolation iFactorySkeletonInterpolation;
    private ISkeletonsCompletion iSkeletonsCompletionBeforeInterpolation; //TODO delete this if we are in good position
    private IFactoryVideo iFactoryVideo;
    private IDetectProvider iDetectProvider;
    private ISkeletonsLoader iSkeletonsLoader;
    private IFactoryVideoHandler iFactoryVideoHandler;
    private IFactoryDraw iFactoryDraw;
    private IPeriodTimeProvider periodTimeProvider;
    private IFeedbackDao dao;

    public FeedbackProvider(MLConnectionHandler mlConnectionHandler,
                            IFactoryFeedbackVideo iFactoryFeedbackVideo,
                            IFactorySkeletonInterpolation iFactorySkeletonInterpolation,
                            ISkeletonsCompletion iSkeletonsCompletionBeforeInterpolation,
                            IFactoryVideo iFactoryVideo,
                            IDetectProvider iDetectProvider,
                            ISkeletonsLoader iSkeletonsLoader,
                            IFactoryVideoHandler iFactoryVideoHandler,
                            IFactoryDraw iFactoryDraw,
                            IPeriodTimeProvider periodTimeProvider,
                            IFeedbackDao dao) {

        this.mlConnectionHandler = mlConnectionHandler;
        this.iFactoryFeedbackVideo = iFactoryFeedbackVideo;
        this.iFactorySkeletonInterpolation = iFactorySkeletonInterpolation;
        this.iSkeletonsCompletionBeforeInterpolation = iSkeletonsCompletionBeforeInterpolation;
        this.iFactoryVideo = iFactoryVideo;
        this.iDetectProvider = iDetectProvider;
        this.iSkeletonsLoader = iSkeletonsLoader;
        this.iFactoryVideoHandler = iFactoryVideoHandler;
        this.iFactoryDraw = iFactoryDraw;
        this.periodTimeProvider = periodTimeProvider;
        this.dao = dao;
    }

    @Override
    public FeedbackVideoDTO streamFeedback(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                byte[] data = Files.readAllBytes(file.toPath());
                return new FeedbackVideoDTO(file.getPath(), data);
            } catch (Exception e) {
                //TODO return here error
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }

    @Override
    public IFeedbackVideo getFeedbackVideo(IVideo video,
                                           String feedbackFolderPath,
                                           String skeletonsPath,
                                           String mlSkeletonsFolderPath,
                                           List<String> detectorsNames,
                                           LocalDateTime time) throws Exception {

        IVideoHandler  videoHandler = iFactoryVideoHandler.create(iFactoryDraw.create());
        //TODO change this when changing the combination with th ml server
        List<Mat> frames = videoHandler.getFrames(video.getPath());
        int size = frames.size();
        int height = frames.get(0).height();
        int width = frames.get(0).width();
        // ml skeletons
        List<ISwimmingSkeleton> skeletons = mlConnectionHandler.getSkeletons(video, size, height, width);
        String mlSkeletonsPath = generateName(mlSkeletonsFolderPath, ".csv", time);
        iSkeletonsLoader.save(skeletons, mlSkeletonsPath);
        // interpolate for the new skeletons
        skeletons = completeAndInterpolate(skeletons);
        //time period
        ISwimmingPeriodTime periodTime = periodTimeProvider.analyzeTimes(skeletons);
        skeletons = periodTimeProvider.correctSkeletons(skeletons, periodTime);
        periodTime = periodTimeProvider.analyzeTimes(skeletons);
        // tagged video
        TaggedVideo taggedVideo = new TaggedVideo(skeletons, mlSkeletonsPath, skeletonsPath);
        // error detection
        Map<Integer, List<SwimmingError>> errorMap = this.iDetectProvider.detect(skeletons, periodTime);
        // generate feedback
        String feedbackPath = generateName(feedbackFolderPath, ".mp4", time);
        File feedbackFile = videoHandler.getFeedBackVideoFile(feedbackPath, video.getPath(), skeletons,
                                errorMap, null);
        if(feedbackFile.exists()) {
            return iFactoryFeedbackVideo.create(video, taggedVideo, errorMap, feedbackPath, periodTime);
        }
        return null;
    }

    @Override
    public IFeedbackVideo generateFeedbackVideo(ConvertedVideoDTO convertedVideoDTO,
                                                String videoFolderPath,
                                                String feedbackFolderPath,
                                                String feedbackSkeletonsFolderPath,
                                                String mlSkeletonsFolderPath,
                                                List<String> detectorsNames) throws Exception {
        LocalDateTime localDateTime = LocalDateTime.now();
        IVideoHandler  videoHandler = iFactoryVideoHandler.create(iFactoryDraw.create());
        // create IVideo
        String videoPath = generateName(videoFolderPath, convertedVideoDTO.getVideoType(), localDateTime);
        if(videoHandler.createAndGetFrames(convertedVideoDTO.getBytes(), videoPath)) {
            IVideo video = iFactoryVideo.create(convertedVideoDTO, videoPath);
            if (video.isVideoExists()) {
                // create the skeleton path
                String skeletonsPath = generateName(feedbackSkeletonsFolderPath, ".csv", localDateTime);
                // create feedback
                IFeedbackVideo feedbackVideo = getFeedbackVideo(
                        video, feedbackFolderPath, skeletonsPath, mlSkeletonsFolderPath,
                        detectorsNames, localDateTime);
                if (feedbackVideo != null) {
                    iSkeletonsLoader.save(feedbackVideo.getSwimmingSkeletons(), skeletonsPath);
                }
                return feedbackVideo;
            }
        }
        return null;
    }

    @Override
    public FeedbacksSummary getSummary() {
        Long feedbacks = this.dao.countFeedbacks();
        return new FeedbacksSummary(feedbacks);
    }

    /***
     * The function generate a file name
     * @param folderPath
     * @param fileType
     * @param time
     * @return
     */
    private String generateName(String folderPath, String fileType, LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return folderPath + File.separator + time.format(formatter) + fileType;
    }

    /**
     * the function add more points to the swimmer, using interpolation and compilation
     * @param skeletons - the given skeleton the ML find
     * @return - new skeleton with more points
     */
    private List<ISwimmingSkeleton> completeAndInterpolate(List<ISwimmingSkeleton> skeletons) {
        //skeletons = iSkeletonsCompletionBeforeInterpolation.complete(skeletons);
        ISkeletonInterpolation interpolation = this.iFactorySkeletonInterpolation.factory();
        skeletons = interpolation.interpolate(skeletons);
        return skeletons;
    }

}
