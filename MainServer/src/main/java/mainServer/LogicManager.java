package mainServer;

import DTO.*;
import Domain.Streaming.*;
import mainServer.Providers.IUserProvider;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;
import ExernalSystems.MLConnectionHandler;
import mainServer.Completions.ISkeletonsCompletion;
import mainServer.Interpolations.ISkeletonInterpolation;
import mainServer.SwimmingErrorDetectors.*;


import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class LogicManager {

    private IUserProvider _userProvider;
    private IFeedbackVideo lastFeedbackVideo; //TODO delete this
    private IFactoryErrorDetectors iFactoryErrorDetectors;
    private IFactoryVideo iFactoryVideo;
    private IFactoryFeedbackVideo iFactoryFeedbackVideo;
    private ISkeletonInterpolation iSkeletonInterpolation;
    private ISkeletonsCompletion iSkeletonsCompletionBeforeInterpolation;
    private ISkeletonsCompletion iSkeletonsCompletionAfterInterpolation;
    private MLConnectionHandler mlConnectionHandler;

    public LogicManager(IUserProvider _userProvider,
                        IFactoryErrorDetectors iFactoryErrorDetectors,
                        IFactoryVideo iFactoryVideo,
                        IFactoryFeedbackVideo iFactoryFeedbackVideo,
                        ISkeletonInterpolation iSkeletonInterpolation,
                        ISkeletonsCompletion iSkeletonsCompletionBeforeInterpolation,
                        ISkeletonsCompletion iSkeletonsCompletionAfterInterpolation,
                        MLConnectionHandler mlConnectionHandler) {
        this._userProvider = _userProvider;
        this.iFactoryErrorDetectors = iFactoryErrorDetectors;
        this.iFactoryVideo = iFactoryVideo;
        this.iFactoryFeedbackVideo = iFactoryFeedbackVideo;
        this.iSkeletonInterpolation = iSkeletonInterpolation;
        this.iSkeletonsCompletionBeforeInterpolation = iSkeletonsCompletionBeforeInterpolation;
        this.iSkeletonsCompletionAfterInterpolation = iSkeletonsCompletionAfterInterpolation;
        this.mlConnectionHandler = mlConnectionHandler;
    }

    /**
     * The function handle login of swimmers to the system
     * @param userDTO the simmers information
     * @return true
     */
    public synchronized ActionResult<UserDTO> login(UserDTO userDTO) {
        if(_userProvider.login(userDTO)) {
            return new ActionResult<>(Response.SUCCESS, userDTO);
        }
        return new ActionResult<>(Response.FAIL,null);
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
     * The function generate a feedback video form a swimming video
     * @param video the video
     * @return the feedback video
     */
    private IFeedbackVideo getFeedbackVideo(IVideo video, List<SwimmingErrorDetector> errorDetectors) {
        TaggedVideo taggedVideo = mlConnectionHandler.getSkeletons(video);
        Map<Integer, List<SwimmingError>> errorMap = new HashMap<>();
        List<ISwimmingSkeleton> skeletons = taggedVideo.getTags();
        skeletons = iSkeletonsCompletionBeforeInterpolation.complete(skeletons);
        skeletons = iSkeletonInterpolation.interpolate(skeletons);
        skeletons = iSkeletonsCompletionAfterInterpolation.complete(skeletons);
        taggedVideo.setTags(skeletons);
        for(int i =0; i<skeletons.size(); i++) {
            ISwimmingSkeleton skeleton = skeletons.get(i);
            List<SwimmingError> errors = new LinkedList<>();
            for(SwimmingErrorDetector detector: errorDetectors) {
                List<SwimmingError> detectorErrors = detector.detect(skeleton);
                errors.addAll(detectorErrors);
            }
            errorMap.put(i, errors);
        }
        IFeedbackVideo feedbackVideo = iFactoryFeedbackVideo.create(video, taggedVideo, errorMap);
        return feedbackVideo;
    }

    /**
     * The function handle upload video that want to receives a downloading file
     * @param convertedVideoDTO the video we got from the client
     * @return the feedback video
     */
    public ActionResult<FeedbackVideoDTO> uploadVideoForDownload(ConvertedVideoDTO convertedVideoDTO) {
        IVideo video = iFactoryVideo.create(convertedVideoDTO);
        if(video.isVideoExists()) {
            List<SwimmingErrorDetector> errorDetectors = getSwimmingErrorDetectors();
            IFeedbackVideo feedbackVideo = getFeedbackVideo(video, errorDetectors);
            FeedbackVideoDTO feedbackVideoDTO = feedbackVideo.generateFeedbackDTO();
            if (feedbackVideoDTO == null) {
                //TODO return here a action result error!!
            }
            return new ActionResult<>(Response.SUCCESS, feedbackVideoDTO);
        }
        //TODO what to return when fail
        return new ActionResult<>(Response.FAIL, null);

    }

    /**
     * The function handle upload video that want a streaming result
     * @param convertedVideoDTO the video we want to view
     * @return the streaming path for the feedback video
     * @precondition the feedback video we are generating doesn't exits!
     */
    public ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(ConvertedVideoDTO convertedVideoDTO) {
        IVideo video = iFactoryVideo.create(convertedVideoDTO);
        if(video.isVideoExists()) {
            List<SwimmingErrorDetector> errorDetectors = getSwimmingErrorDetectors();
            List<String> detectorsNames = new LinkedList<>();
            for (SwimmingErrorDetector detector : errorDetectors) {
                detectorsNames.add(detector.getTag());
            }
            IFeedbackVideo feedbackVideo = getFeedbackVideo(video, errorDetectors);
            //TODO delete this after removing lastFeedbackVideo
            this.lastFeedbackVideo = feedbackVideo;
            FeedbackVideoStreamer feedbackVideoStreamer = feedbackVideo.generateFeedbackStreamer(detectorsNames);
            if (feedbackVideoStreamer == null) {
                //TODO return here action result error!!
            }
            return new ActionResult<>(Response.SUCCESS, feedbackVideoStreamer);
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
        //TODO need to refactor this in the future for a class responsible of our resources
        //TODO need here to be access check
        File file = new File(path);
        if(file.exists()) {
            try {
                byte[] data = Files.readAllBytes(file.toPath());
                FeedbackVideoDTO output = new FeedbackVideoDTO(file.getPath(), data);
                return new ActionResult<>(Response.SUCCESS, output);
            } catch (Exception e) {
                //TODO return here error
                System.out.println(e.getMessage());
            }
        }
        //TODO return error
        //TODO maybe always generate a video if it a error video then return error video ?
        return null;
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
     * The function create a new feedback video, filter, and send a new feedback link
     * @param filterDTO - the feedback to filter
     * @return new feedbackVideoStreamer
     */
    public ActionResult<FeedbackVideoStreamer> filterFeedbackVideo(FeedbackFilterDTO filterDTO) {
        //TODO check if feedback video exits
        if(this.lastFeedbackVideo!=null){
            // TODO - feedback video isn't video any more
            IVideo video = this.lastFeedbackVideo.getIVideo();
            List<SwimmingErrorDetector> errorDetectors = buildDetectors(filterDTO);
            List<String> detectorsNames = new LinkedList<>();
            for(SwimmingErrorDetector detector: errorDetectors) {
                detectorsNames.add(detector.getTag());
            }
            IFeedbackVideo feedbackVideo = getFeedbackVideo(video, errorDetectors);
            FeedbackVideoStreamer feedbackVideoStreamer = feedbackVideo.generateFeedbackStreamer(detectorsNames);
            if(feedbackVideoStreamer == null) {
                //TODO return here action result error!!
            }
            return new ActionResult<>(Response.SUCCESS, feedbackVideoStreamer);
        }
        return null;
    }
}
