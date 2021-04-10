package mainServer.Providers.Interfaces;

import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.Streaming.IVideo;

import java.time.LocalDateTime;
import java.util.List;

public interface IFeedbackProvider {

    /**
     * the function create a feedback video representation from a file
     * @param path - the path of the file
     * @return - feedback video representation
     */
    FeedbackVideoDTO streamFeedback(String path);

    /**
     *
     * @param video
     * @param feedbackFolderPath
     * @param skeletonsPath
     * @param mlSkeletonsPath
     * @param detectorsNames
     * @param time
     * @return
     */
    IFeedbackVideo getFeedbackVideo(IVideo video,
                                    String feedbackFolderPath,
                                    String skeletonsPath,
                                    String mlSkeletonsPath,
                                    List<String> detectorsNames,
                                    LocalDateTime time) throws Exception;

    /**
     * TODO - add comments
     * @param convertedVideoDTO
     * @param videoFolderPath
     * @param feedbackFolderPath
     * @return
     */
    IFeedbackVideo generateFeedbackVideo(ConvertedVideoDTO convertedVideoDTO,
                                         String videoFolderPath,
                                         String feedbackFolderPath,
                                         String feedbackSkeletonsFolderPath,
                                         String mlSkeletonsFolderPath,
                                         List<String> detectorsNames) throws Exception;

}
