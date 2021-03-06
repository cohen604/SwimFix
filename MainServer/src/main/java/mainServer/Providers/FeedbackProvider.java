package mainServer.Providers;

import DTO.*;
import Domain.Streaming.*;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;
import Domain.UserData.Interfaces.IUser;
import ExernalSystems.MLConnectionHandler;
import mainServer.Completions.ISkeletonsCompletion;
import mainServer.FileLoaders.ISkeletonsLoader;
import mainServer.Interpolations.ISkeletonInterpolation;
import mainServer.SwimmingErrorDetectors.IFactoryErrorDetectors;
import mainServer.SwimmingErrorDetectors.SwimmingErrorDetector;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
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

    public FeedbackProvider(MLConnectionHandler mlConnectionHandler,
                            IFactoryFeedbackVideo iFactoryFeedbackVideo,
                            ISkeletonInterpolation iSkeletonInterpolation,
                            ISkeletonsCompletion iSkeletonsCompletionBeforeInterpolation,
                            ISkeletonsCompletion iSkeletonsCompletionAfterInterpolation,
                            IFactoryVideo iFactoryVideo,
                            IFactoryErrorDetectors iFactoryErrorDetectors,
                            ISkeletonsLoader iSkeletonsLoader) {
        this.mlConnectionHandler = mlConnectionHandler;
        this.iFactoryFeedbackVideo = iFactoryFeedbackVideo;
        this.iSkeletonInterpolation = iSkeletonInterpolation;
        this.iSkeletonsCompletionBeforeInterpolation = iSkeletonsCompletionBeforeInterpolation;
        this.iSkeletonsCompletionAfterInterpolation = iSkeletonsCompletionAfterInterpolation;
        this.iFactoryVideo = iFactoryVideo;
        this.iFactoryErrorDetectors = iFactoryErrorDetectors;
        this.iSkeletonsLoader = iSkeletonsLoader;
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
    public IFeedbackVideo getFeedbackVideo(IVideo video, List<SwimmingErrorDetector> errorDetectors,
                                           String feedbackFolderPath, List<String> detectorsNames,
                                           LocalDateTime time) {
        TaggedVideo taggedVideo = mlConnectionHandler.getSkeletons(video);
        Map<Integer, List<SwimmingError>> errorMap = new HashMap<>();
        List<ISwimmingSkeleton> skeletons = taggedVideo.getTags();
        skeletons = completeAndInterpolate(skeletons);
        taggedVideo.setTags(skeletons);
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
            errorMap.put(i, errors);
        }
        return iFactoryFeedbackVideo.create(video, taggedVideo, errorMap, feedbackFolderPath, time);
    }

    @Override
    public IFeedbackVideo generateFeedbackVideo(ConvertedVideoDTO convertedVideoDTO,
                                                String videoPath,
                                                String feedbackPath,
                                                String skeletonsPath,
                                                List<String> detectorsNames) {
        LocalDateTime localDateTime = LocalDateTime.now();
        IVideo video = iFactoryVideo.create(convertedVideoDTO, videoPath, localDateTime);
        if (video.isVideoExists()) {
            // decoders step
            List<SwimmingErrorDetector> errorDetectors = getSwimmingErrorDetectors();
            // create feedback
            IFeedbackVideo feedbackVideo = getFeedbackVideo(
                    video, errorDetectors, feedbackPath, detectorsNames, localDateTime);
            if(feedbackVideo != null) {
                iSkeletonsLoader.save(feedbackVideo.getSwimmingSkeletons(), skeletonsPath, localDateTime);
            }
            return feedbackVideo;
        }
        return null;
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
                                              FeedbackFilterDTO filterDTO,
                                              IVideo video,
                                              List<String> detectorsNames) {
        //TODO remove this - need to call filter form the origin skeletons saved
        // and not create a new feedback that we save in the server file system
        LocalDateTime localDateTime = LocalDateTime.now();
        List<SwimmingErrorDetector> errorDetectors = buildDetectors(filterDTO);
        return getFeedbackVideo(video, errorDetectors, feedbackFolderPath, detectorsNames, localDateTime);
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
        // TODO problem with interpolation
        //skeletons = iSkeletonInterpolation.interpolate(skeletons);
        //skeletons = iSkeletonsCompletionAfterInterpolation.complete(skeletons);
        return skeletons;
    }

}
