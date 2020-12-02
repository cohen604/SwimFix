package mainServer;

import DTO.*;
import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.SwimmingError;
import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import Domain.User;
import ExernalSystems.MLConnectionHandler;
import ExernalSystems.MLConnectionHandlerProxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.List;

public class LogicManager {
    MLConnectionHandler mlConnectionHandler;
    //TODO: hold all the logged users
    List<User> userList;

    public LogicManager() {
        mlConnectionHandler = new MLConnectionHandlerProxy();
    }

    /**
     * The function generate a feedback video form a swimming video
     * @param convertedVideoDTO the video
     * @return the feedback video
     */
    private FeedbackVideo getFeedbackVideo(ConvertedVideoDTO convertedVideoDTO) {
        Video video = new Video(convertedVideoDTO);
        TaggedVideo taggedVideo = mlConnectionHandler.tagFrames(video);
        FeedbackVideo feedbackVideo = new FeedbackVideo(video, taggedVideo, null);
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
        File file = new File(path);
        if(!file.exists()) {
            //TODO return error
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
