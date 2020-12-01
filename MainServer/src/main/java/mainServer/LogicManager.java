package mainServer;

import DTO.*;
import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.SwimmingError;
import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import Domain.User;
import ExernalSystems.MLConnectionHandler;
import ExernalSystems.MLConnectionHandlerProxy;

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
}
