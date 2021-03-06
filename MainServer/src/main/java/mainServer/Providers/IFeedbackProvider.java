package mainServer.Providers;

import DTO.ConvertedVideoDTO;
import DTO.FeedbackFilterDTO;
import DTO.FeedbackVideoDTO;
import Domain.Streaming.IFeedbackVideo;
import Domain.Streaming.IVideo;
import mainServer.SwimmingErrorDetectors.SwimmingErrorDetector;

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
     * TODO - add comments
     * @param video
     * @param errorDetectors
     * @param feedbackFolderPath
     * @return
     */
    IFeedbackVideo getFeedbackVideo(IVideo video,
                                    List<SwimmingErrorDetector> errorDetectors,
                                    String feedbackFolderPath,
                                    List<String> detectorsNames,
                                    LocalDateTime time);

    /**
     * TODO - add comments
     * @param convertedVideoDTO
     * @param videoPath
     * @param feedbackPath
     * @return
     */
    IFeedbackVideo generateFeedbackVideo(ConvertedVideoDTO convertedVideoDTO,
                                         String videoPath,
                                         String feedbackPath,
                                         String skeletonsPath,
                                         List<String> detectorsNames);

    /**
     * TODO - add comments
     * @return
     */
    List<String> getErrorDetectorsNames();

    /**
     * TODO - add comments
     * @param feedbackFolderPath
     * @param filterDTO
     * @param video
     * @return
     */
    IFeedbackVideo filterFeedbackVideo(String feedbackFolderPath,
                                       FeedbackFilterDTO filterDTO,
                                       IVideo video,
                                       List<String> detectorsNames);

}
