package mainServer;

import DTO.*;
import Domain.Streaming.*;
import Domain.UserData.Interfaces.IUser;
import mainServer.Providers.IStreamProvider;
import mainServer.Providers.IUserProvider;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingError;
import ExernalSystems.MLConnectionHandler;
import mainServer.Completions.ISkeletonsCompletion;
import mainServer.Interpolations.ISkeletonInterpolation;
import mainServer.SwimmingErrorDetectors.*;
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
    private IStreamProvider _streamProvider;
    private IFeedbackVideo lastFeedbackVideo; //TODO delete this
    private IFactoryErrorDetectors iFactoryErrorDetectors;
    private IFactoryVideo iFactoryVideo;
    private IFactoryFeedbackVideo iFactoryFeedbackVideo;
    private ISkeletonInterpolation iSkeletonInterpolation;
    private ISkeletonsCompletion iSkeletonsCompletionBeforeInterpolation;
    private ISkeletonsCompletion iSkeletonsCompletionAfterInterpolation;
    private MLConnectionHandler mlConnectionHandler;

    public LogicManager(IUserProvider _userProvider,
                        IStreamProvider streamProvider,
                        IFactoryErrorDetectors iFactoryErrorDetectors,
                        IFactoryVideo iFactoryVideo,
                        IFactoryFeedbackVideo iFactoryFeedbackVideo,
                        ISkeletonInterpolation iSkeletonInterpolation,
                        ISkeletonsCompletion iSkeletonsCompletionBeforeInterpolation,
                        ISkeletonsCompletion iSkeletonsCompletionAfterInterpolation,
                        MLConnectionHandler mlConnectionHandler) {
        this._userProvider = _userProvider;
        _streamProvider = streamProvider;
        this.iFactoryErrorDetectors = iFactoryErrorDetectors;
        this.iFactoryVideo = iFactoryVideo;
        this.iFactoryFeedbackVideo = iFactoryFeedbackVideo;
        this.iSkeletonInterpolation = iSkeletonInterpolation;
        this.iSkeletonsCompletionBeforeInterpolation = iSkeletonsCompletionBeforeInterpolation;
        this.iSkeletonsCompletionAfterInterpolation = iSkeletonsCompletionAfterInterpolation;
        this.mlConnectionHandler = mlConnectionHandler;
        createClientsDir();
    }

    /**
     * create the client directory in the server memory
     */
    private void createClientsDir() {
        try {
            Path path = Paths.get("clients");
            // create the clients directory if not exist
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
    private IFeedbackVideo getFeedbackVideo(IVideo video, List<SwimmingErrorDetector> errorDetectors,
                                            String feedbackFolderPath) {
        TaggedVideo taggedVideo = mlConnectionHandler.getSkeletons(video);
        Map<Integer, List<SwimmingError>> errorMap = new HashMap<>();
        List<ISwimmingSkeleton> skeletons = taggedVideo.getTags();
        skeletons = completeAndInterpolate(skeletons);
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
        IFeedbackVideo feedbackVideo = iFactoryFeedbackVideo.create(video, taggedVideo, errorMap, feedbackFolderPath);
        return feedbackVideo;
    }

    /**
     * the function add more points to the swimmer, using interpolation and compilation
     * @param skeletons - the given skeleton the ML find
     * @return - new skeleton with more points
     */
    private List<ISwimmingSkeleton> completeAndInterpolate(List<ISwimmingSkeleton> skeletons) {
        skeletons = iSkeletonsCompletionBeforeInterpolation.complete(skeletons);
        skeletons = iSkeletonInterpolation.interpolate(skeletons);
        skeletons = iSkeletonsCompletionAfterInterpolation.complete(skeletons);
        return skeletons;
    }

    /**
     * The function handle upload video that want a streaming result
     * @param convertedVideoDTO the video we want to view
     * @return the streaming path for the feedback video
     * @ pre condition - the feedback video we are generating doesn't exits!
     */
    public ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(UserDTO userDTO, ConvertedVideoDTO convertedVideoDTO) {
        IUser user = _userProvider.getUser(userDTO);
        if(user!=null) {
            // create video
            IVideo video = iFactoryVideo.create(convertedVideoDTO, user.getVideosPath());
            if (video.isVideoExists()) {
                // decoders step
                List<SwimmingErrorDetector> errorDetectors = getSwimmingErrorDetectors();
                List<String> detectorsNames = new LinkedList<>();
                for (SwimmingErrorDetector detector : errorDetectors) {
                    detectorsNames.add(detector.getTag());
                }
                // create feedback
                IFeedbackVideo feedbackVideo = getFeedbackVideo(video, errorDetectors, user.getFeedbacksPath());
                //TODO delete this after removing lastFeedbackVideo
                this.lastFeedbackVideo = feedbackVideo;
                FeedbackVideoStreamer feedbackVideoStreamer = feedbackVideo.generateFeedbackStreamer(detectorsNames);
                if (feedbackVideoStreamer == null) {
                    //TODO return here action result error!!
                }
                return new ActionResult<>(Response.SUCCESS, feedbackVideoStreamer);
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
            FeedbackVideoDTO output = _streamProvider.streamFile(path);
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
     * @param userDTO - the user info
     * @param filterDTO - the feedback to filter
     * @return new feedbackVideoStreamer
     */
    public ActionResult<FeedbackVideoStreamer> filterFeedbackVideo(UserDTO userDTO, FeedbackFilterDTO filterDTO) {
        //TODO check if feedback video exits
        if(this.lastFeedbackVideo!=null) {
            // TODO - feedback video isn't video any more
            IVideo video = this.lastFeedbackVideo.getIVideo();
            List<SwimmingErrorDetector> errorDetectors = buildDetectors(filterDTO);
            List<String> detectorsNames = new LinkedList<>();
            for(SwimmingErrorDetector detector: errorDetectors) {
                detectorsNames.add(detector.getTag());
            }
            //TODO fix folder name
            IFeedbackVideo feedbackVideo = getFeedbackVideo(video, errorDetectors, "");
            FeedbackVideoStreamer feedbackVideoStreamer = feedbackVideo.generateFeedbackStreamer(detectorsNames);
            if(feedbackVideoStreamer == null) {
                //TODO return here action result error!!
            }
            return new ActionResult<>(Response.SUCCESS, feedbackVideoStreamer);
        }
        return null;
    }
}
