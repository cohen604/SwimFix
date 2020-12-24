package mainServer;

import DTO.*;
import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import Domain.Swimmer;
import Domain.SwimmingData.SwimmingError;
import Domain.SwimmingData.SwimmingSkeleton;
import Domain.User;
import ExernalSystems.MLConnectionHandler;
import ExernalSystems.MLConnectionHandlerProxy;
import Storage.Swimmer.SwimmerDao;
import Storage.User.UserDao;
import mainServer.SwimmingErrorDetectors.ElbowErrorDetector;
import mainServer.SwimmingErrorDetectors.SwimmingErrorDetector;


import java.io.File;
import java.nio.file.Files;
import java.util.*;

public class LogicManager {

    private MLConnectionHandler mlConnectionHandler;
    //TODO: hold all the logged users
    List<User> userList;

    public LogicManager() {
        mlConnectionHandler = new MLConnectionHandlerProxy();
    }


    /**
     * The function handle login of swimmers to the system
     * @param userDTO the simmers information
     * @return true
     */
    public synchronized ActionResult<UserDTO> login(UserDTO userDTO) {
        UserDao userDao = new UserDao();
        // TODO synchronized(getLocker(user.getUid())){};
        User user = userDao.find(userDTO.getUid());
        if(user!=null) {
            user.login();
            userDao.update(user);
            return new ActionResult<>(Response.SUCCESS, userDTO);
        }
        // user not exits
        user = new User(userDTO);
        user.login();
        Swimmer swimmer = new Swimmer(user.getUid());
        user.addState(swimmer);
        if(userDao.insert(user)!=null) {
            SwimmerDao swimmerDao = new SwimmerDao();
            if(swimmerDao.insert(swimmer)!=null) {
                return new ActionResult<>(Response.SUCCESS, userDTO);
            }
            else {
                // todo delete user from db
                // return fail
            }
        }
        return new ActionResult<>(Response.FAIL,null);
    }

    /**
     * The function generate a feedback video form a swimming video
     * @param convertedVideoDTO the video
     * @return the feedback video
     */
    private FeedbackVideo getFeedbackVideo(ConvertedVideoDTO convertedVideoDTO) {
        Video video = new Video(convertedVideoDTO);
        TaggedVideo taggedVideo = mlConnectionHandler.getSkeletons(video);
        Map<Integer, List<SwimmingError>> errorMap = new HashMap<>();
        List<SwimmingSkeleton> skeletons = taggedVideo.getTags();
        for(int i =0; i<skeletons.size(); i++) {
            SwimmingSkeleton skeleton = skeletons.get(i);
            SwimmingErrorDetector detector = new ElbowErrorDetector(90, 175);
            List<SwimmingError> detectorErrors = detector.detect(skeleton);
            if(!detectorErrors.isEmpty()) {
                System.out.println("found for frame "+ i);
            }
            errorMap.put(i, detectorErrors);
        }
        FeedbackVideo feedbackVideo = new FeedbackVideo(video, taggedVideo, errorMap);
        return feedbackVideo;
    }

    /**
     * The function handle upload video that want to receives a downloading file
     * @param convertedVideoDTO the video we got from the client
     * @return the feedback video
     */
    public ActionResult<FeedbackVideoDTO> uploadVideoForDownload(ConvertedVideoDTO convertedVideoDTO) {
        FeedbackVideo feedbackVideo = getFeedbackVideo(convertedVideoDTO);
        FeedbackVideoDTO feedbackVideoDTO = feedbackVideo.generateFeedbackDTO();
        if(feedbackVideoDTO == null) {
            //TODO return here a action result error!!
        }
        return new ActionResult<>(Response.SUCCESS, feedbackVideoDTO);
    }

    /**
     * The function handle upload video that want a streaming result
     * @param convertedVideoDTO the video we want to view
     * @return the streaming path for the feedback video
     * @precondition the feedback video we are generating doesn't exits!
     */
    public ActionResult<FeedbackVideoStreamer> uploadVideoForStreamer(ConvertedVideoDTO convertedVideoDTO) {
        FeedbackVideo feedbackVideo = getFeedbackVideo(convertedVideoDTO);
        FeedbackVideoStreamer feedbackVideoStreamer = feedbackVideo.generateFeedbackStreamer();
        if(feedbackVideoStreamer == null) {
            //TODO return here action result error!!
        }
        return new ActionResult<>(Response.SUCCESS, feedbackVideoStreamer);
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
        if(!file.exists()) {
            //TODO return error
            //TODO maybe always generate a video if it a error video then return error video ?
        }
        try {
            byte[] data = Files.readAllBytes(file.toPath());
            FeedbackVideoDTO output = new FeedbackVideoDTO(file.getPath() ,data);
            return new ActionResult<>(Response.SUCCESS, output);
        } catch (Exception e ){
            //TODO return here error
            System.out.println(e.getMessage());
        }
        return null;
    }

}
