package mainServer.Providers;

import DTO.*;
import Domain.Streaming.*;
import Domain.SwimmingData.Drawing.Draw;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;
import ExernalSystems.MLConnectionHandler;
import DomainLogic.Completions.ISkeletonsCompletion;
import DomainLogic.FileLoaders.ISkeletonsLoader;
import DomainLogic.Interpolations.ISkeletonInterpolation;
import DomainLogic.SwimmingErrorDetectors.IFactoryDraw;
import DomainLogic.SwimmingErrorDetectors.IFactoryErrorDetectors;
import DomainLogic.SwimmingErrorDetectors.SwimmingErrorDetector;
import org.opencv.core.Mat;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class FeedbackProvider implements IFeedbackProvider {

    private MLConnectionHandler mlConnectionHandler;
    private IFactoryFeedbackVideo iFactoryFeedbackVideo;
    private ISkeletonInterpolation iSkeletonInterpolation;
    private ISkeletonsCompletion iSkeletonsCompletionBeforeInterpolation;
    private ISkeletonsCompletion iSkeletonsCompletionAfterInterpolation;
    private IFactoryVideo iFactoryVideo;
    private IFactoryErrorDetectors iFactoryErrorDetectors;
    private ISkeletonsLoader iSkeletonsLoader;
    private IFactoryVideoHandler iFactoryVideoHandler;
    private IFactoryDraw iFactoryDraw;

    public FeedbackProvider(MLConnectionHandler mlConnectionHandler,
                            IFactoryFeedbackVideo iFactoryFeedbackVideo,
                            ISkeletonInterpolation iSkeletonInterpolation,
                            ISkeletonsCompletion iSkeletonsCompletionBeforeInterpolation,
                            ISkeletonsCompletion iSkeletonsCompletionAfterInterpolation,
                            IFactoryVideo iFactoryVideo,
                            IFactoryErrorDetectors iFactoryErrorDetectors,
                            ISkeletonsLoader iSkeletonsLoader,
                            IFactoryVideoHandler iFactoryVideoHandler,
                            IFactoryDraw iFactoryDraw) {
        this.mlConnectionHandler = mlConnectionHandler;
        this.iFactoryFeedbackVideo = iFactoryFeedbackVideo;
        this.iSkeletonInterpolation = iSkeletonInterpolation;
        this.iSkeletonsCompletionBeforeInterpolation = iSkeletonsCompletionBeforeInterpolation;
        this.iSkeletonsCompletionAfterInterpolation = iSkeletonsCompletionAfterInterpolation;
        this.iFactoryVideo = iFactoryVideo;
        this.iFactoryErrorDetectors = iFactoryErrorDetectors;
        this.iSkeletonsLoader = iSkeletonsLoader;
        this.iFactoryVideoHandler = iFactoryVideoHandler;
        this.iFactoryDraw = iFactoryDraw;
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
                                           List<SwimmingErrorDetector> errorDetectors,
                                           String feedbackFolderPath,
                                           String skeletonsPath,
                                           String mlSkeletonsFolderPath,
                                           List<String> detectorsNames,
                                           LocalDateTime time) {

        IVideoHandler  videoHandler = iFactoryVideoHandler.create(iFactoryDraw.create());
        //TODO change this when changing the combination with th ml server
        List<Mat> frames = videoHandler.getFrames(video.getPath());
        int size = frames.size();
        int height = frames.get(0).height();
        int width = frames.get(0).width();

        List<ISwimmingSkeleton> skeletons = mlConnectionHandler.getSkeletons(video, size, height, width);
        String mlSkeletonsPath = generateName(mlSkeletonsFolderPath, ".csv", time);
        TaggedVideo taggedVideo = new TaggedVideo(skeletons, skeletonsPath, mlSkeletonsPath);
        // save the ml skeletons
        iSkeletonsLoader.save(taggedVideo.getTags(), mlSkeletonsPath);
        Map<Integer, List<SwimmingError>> errorMap = new HashMap<>();
        skeletons = taggedVideo.getTags();
        //interpolate for the new skeletons
        skeletons = completeAndInterpolate(skeletons);
        taggedVideo.setTags(skeletons);
        // error detection
        for(int i =0; i<skeletons.size(); i++) {
            ISwimmingSkeleton skeleton = skeletons.get(i);
            List<SwimmingError> errors = new LinkedList<>();
            for(SwimmingErrorDetector detector: errorDetectors) {
                if (detectorsNames != null && !detectorsNames.contains(detector.getTag())) {
                    detectorsNames.add(detector.getTag());
                }
                List<SwimmingError> detectorErrors = detector.detect(skeleton);
                errors.addAll(detectorErrors);
            }
            if(!errors.isEmpty()) {
                errorMap.put(i, errors);
            }
        }

        String feedbackPath = generateName(feedbackFolderPath, ".mp4", time);
        File feedbackFile = videoHandler.getFeedBackVideoFile(feedbackPath, video.getPath(), skeletons,
                                errorMap, null);
        if(feedbackFile.exists()) {
            return iFactoryFeedbackVideo.create(video, taggedVideo, errorMap, feedbackPath);
        }
        return null;
    }

    @Override
    public IFeedbackVideo generateFeedbackVideo(ConvertedVideoDTO convertedVideoDTO,
                                                String videoFolderPath,
                                                String feedbackFolderPath,
                                                String feedbackSkeletonsFolderPath,
                                                String mlSkeletonsFolderPath,
                                                List<String> detectorsNames) {
        LocalDateTime localDateTime = LocalDateTime.now();
        IVideoHandler  videoHandler = iFactoryVideoHandler.create(iFactoryDraw.create());
        // create IVideo
        String videoPath = generateName(videoFolderPath, convertedVideoDTO.getVideoType(), localDateTime);
        if(videoHandler.createAndGetFrames(convertedVideoDTO.getBytes(), videoPath)) {
            IVideo video = iFactoryVideo.create(convertedVideoDTO, videoPath);
            if (video.isVideoExists()) {
                // decoders step
                List<SwimmingErrorDetector> errorDetectors = getSwimmingErrorDetectors();
                // create the skeleton path
                String skeletonsPath = generateName(feedbackSkeletonsFolderPath, ".csv", localDateTime);
                // create feedback
                IFeedbackVideo feedbackVideo = getFeedbackVideo(
                        video, errorDetectors,
                        feedbackFolderPath, skeletonsPath, mlSkeletonsFolderPath,
                        detectorsNames, localDateTime);
                if (feedbackVideo != null) {
                    iSkeletonsLoader.save(feedbackVideo.getSwimmingSkeletons(), skeletonsPath);
                }
                return feedbackVideo;
            }
        }
        return null;
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
        return folderPath + "\\" + time.format(formatter) + fileType;
    }

    @Override
    public List<String> getErrorDetectorsNames() {
        List<SwimmingErrorDetector> errorDetectors = getSwimmingErrorDetectors();
        List<String> detectorsNames = new LinkedList<>();
        for (SwimmingErrorDetector detector : errorDetectors) {
            detectorsNames.add(detector.getTag());
        }
        return detectorsNames;
    }


    @Override
    public IFeedbackVideo filterFeedbackVideo(String feedbackFolderPath,
                                              String skeletonsPath,
                                              String mlSkeletonsPath,
                                              FeedbackFilterDTO filterDTO,
                                              IVideo video,
                                              List<String> detectorsNames) {
        //TODO remove this - need to call filter form the origin skeletons saved
        // and not create a new feedback that we save in the server file system
        LocalDateTime localDateTime = LocalDateTime.now();
        List<SwimmingErrorDetector> errorDetectors = buildDetectors(filterDTO);
        return getFeedbackVideo(video, errorDetectors,
                feedbackFolderPath, skeletonsPath, mlSkeletonsPath,
                detectorsNames, localDateTime);
    }

    /**
     * The function build an error detector list from a given filter
     * @param filterDTO - filter
     * @return
     */
    private List<SwimmingErrorDetector> buildDetectors(FeedbackFilterDTO filterDTO) {
        List<SwimmingErrorDetector> output = new LinkedList<>();
        for(String name: filterDTO.getFilters()) {
            //todo Change this in the future to recive not a list of string but list of errorsTDO
            //  for now its the same as the getTag function in the detectors
            switch (name) {
                case "Elbow":
                    output.add(iFactoryErrorDetectors.createElbowErrorDetector(90, 175));
                    break;
                case "Forearm":
                    output.add(iFactoryErrorDetectors.createForearmErrorDetector(-10, 45));
                    break;
                case "Palm":
                    output.add(iFactoryErrorDetectors.createPalmCrossHeadErrorDetector());
                    break;
            }
        }
        return output;
    }

    /**
     * The function return a list of error detectors to check
     * @return
     */
    private List<SwimmingErrorDetector> getSwimmingErrorDetectors() {
        //TODO change this by the selected filters
        List<SwimmingErrorDetector> output = new LinkedList<>();
        output.add(iFactoryErrorDetectors.createElbowErrorDetector(90, 175));
        output.add(iFactoryErrorDetectors.createForearmErrorDetector(-10, 45));
        output.add(iFactoryErrorDetectors.createPalmCrossHeadErrorDetector());
        return output;
    }


    /**
     * the function add more points to the swimmer, using interpolation and compilation
     * @param skeletons - the given skeleton the ML find
     * @return - new skeleton with more points
     */
    private List<ISwimmingSkeleton> completeAndInterpolate(List<ISwimmingSkeleton> skeletons) {
        skeletons = iSkeletonsCompletionBeforeInterpolation.complete(skeletons);
        skeletons = iSkeletonInterpolation.interpolate(skeletons);
        //skeletons = iSkeletonsCompletionAfterInterpolation.complete(skeletons);
        return skeletons;
    }

}
