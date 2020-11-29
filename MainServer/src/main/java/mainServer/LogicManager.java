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

import java.util.List;

public class LogicManager {
    MLConnectionHandler mlConnectionHandler;
    //TODO: hold all the logged users
    List<User> userList;

    public ActionResult<FeedbackVideoDTO> uploadVideo(ConvertedVideoDTO convertedVideoDTO) {
        System.out.println("here 1");
        Video video = new Video(convertedVideoDTO);
        System.out.println("here 2");
        TaggedVideo taggedVideo = mlConnectionHandler.tagVideo(video);
        System.out.println("here 3");
        List<SwimmingError> errorList = null; //TODO
        FeedbackVideo feedbackVideo = new FeedbackVideo(video, taggedVideo, errorList);
        System.out.println("here 4");
        FeedbackVideoDTO feedbackVideoDTO = feedbackVideo.generateDTO();
        System.out.println("here 5");
        ActionResult<FeedbackVideoDTO> actionResult = new ActionResult<>(Response.SUCCESS, feedbackVideoDTO);
        return actionResult;
    }
}
