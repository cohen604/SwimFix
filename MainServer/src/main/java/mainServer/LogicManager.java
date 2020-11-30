package mainServer;

import DTO.ActionResult;
import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;
import DTO.Response;
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

    public ActionResult<FeedbackVideoDTO> uploadVideo(ConvertedVideoDTO convertedVideoDTO) {
        Video video = new Video(convertedVideoDTO);
        TaggedVideo taggedVideo = mlConnectionHandler.tagFrames(video);
        List<SwimmingError> errorList = null; //TODO
        FeedbackVideo feedbackVideo = new FeedbackVideo(video, taggedVideo, errorList);
        FeedbackVideoDTO feedbackVideoDTO = feedbackVideo.generateDTO();
        ActionResult<FeedbackVideoDTO> actionResult = new ActionResult<>(Response.SUCCESS, feedbackVideoDTO);
        return actionResult;
    }
}
