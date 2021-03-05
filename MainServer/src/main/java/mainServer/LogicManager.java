package mainServer;

import DTO.*;
import Domain.Streaming.*;
import Domain.UserData.Interfaces.IUser;
import mainServer.Providers.IFeedbackProvider;
import mainServer.Providers.IUserProvider;
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
    private IFeedbackProvider _feedbackProvider;
    private IFeedbackVideo lastFeedbackVideo; //TODO delete this


    public LogicManager(IUserProvider userProvider,
                        IFeedbackProvider streamProvider) {
        _userProvider = userProvider;
        _feedbackProvider = streamProvider;
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
                        convertedVideoDTO,user.getVideosPath(), user.getFeedbacksPath(), detectorsNames);
            if (feedbackVideo != null) {
                //TODO delete this after removing lastFeedbackVideo
                this.lastFeedbackVideo = feedbackVideo;
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
     * The function create a new feedback video, filter, and send a new feedback link
     * @param userDTO - the user info
     * @param filterDTO - the feedback to filter
     * @return new feedbackVideoStreamer
     */
    public ActionResult<FeedbackVideoStreamer> filterFeedbackVideo(UserDTO userDTO, FeedbackFilterDTO filterDTO) {
        IUser user = _userProvider.getUser(userDTO);
        if(user != null && lastFeedbackVideo != null) {
            // TODO - feedback video isn't video any more
            IVideo video = this.lastFeedbackVideo.getIVideo();
            List<String> detectorsNames = new LinkedList<>();
            IFeedbackVideo feedbackVideo = _feedbackProvider.filterFeedbackVideo(user.getFeedbacksPath(),
                   filterDTO, video, detectorsNames);
            FeedbackVideoStreamer feedbackVideoStreamer = feedbackVideo.generateFeedbackStreamer(detectorsNames);
            if(feedbackVideoStreamer == null) {
                //TODO return here action result error!!
            }
            return new ActionResult<>(Response.SUCCESS, feedbackVideoStreamer);
        }
        return null;
    }
}
