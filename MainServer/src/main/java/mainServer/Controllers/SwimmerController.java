package mainServer.Controllers;

import DTO.*;
import Domain.Streaming.IFeedbackVideo;
import com.google.gson.Gson;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph.Pair;
import mainServer.SingleServiceAPI;
import mainServer.SwimFixAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/swimmer")
public class SwimmerController {

    private SwimFixAPI swimFixAPI;

    public SwimmerController() {
        swimFixAPI = SingleServiceAPI.getInstance();
    }

    @PostMapping("/feedback/link")
    @CrossOrigin(origins = "*")
    public String viewFeedBack(@RequestPart(name = "uid") String uid,
                               @RequestPart(name = "email") String email,
                               @RequestPart(name = "name") String name,
                               @RequestPart(name = "file", required = false) MultipartFile data) {
        try {
            System.out.println("Received Video for Streaming path");
            ConvertedVideoDTO convertedVideo = new ConvertedVideoDTO(data.getOriginalFilename(), data.getBytes());
            UserDTO userDTO = new UserDTO(uid, email, name);
            ActionResult<FeedbackVideoStreamer> actionResult = swimFixAPI.uploadVideoForStreamer(
                    userDTO,
                    convertedVideo);
            System.out.println("Streaming link generated, send link");
            return actionResult.toJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * return the days when a swimmer swim
     * @param user - the user that swim
     * @return - the days
     */
    @PostMapping("/history")
    @CrossOrigin(origins = "*")
    public String viewHistoryDays(@RequestBody UserDTO user) {
        try {
            System.out.println("request view history days");
            UserDTO userDTO = new UserDTO(user.getUid(), user.getEmail(), user.getName());
            ActionResult<List<DateDTO>> actionResult = swimFixAPI.getSwimmerHistoryDays(userDTO);
            System.out.println("send view history days");
            return actionResult.toJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/history/day")
    @CrossOrigin(origins = "*")
    public String viewHistoryByDay(@RequestBody HistoryDayDTO historyDayDTO) {
        try {
            System.out.println("request view history pools by day");
            ActionResult<List<FeedbackVideoStreamer>> actionResult =
                    swimFixAPI.getSwimmerHistoryPoolsBy(historyDayDTO.getUser(), historyDayDTO.getDate());
            System.out.println("send view history pools by day");
            return actionResult.toJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/history/day/delete")
    @CrossOrigin(origins = "*")
    public String deleteFeedback(@RequestBody DeleteFeedbackDTO deleteFeedbackDTO) {
        try {
            System.out.println("request delete feedback");
            UserDTO userDTO = deleteFeedbackDTO.getUser();
            DateDTO dateDTO = deleteFeedbackDTO.getDate();
            String link = deleteFeedbackDTO.getLink();
            if(link.contains("/")) {
                link = link.replaceAll("/","\\");
            }
            ActionResult<Boolean> deleted = swimFixAPI.deleteFeedback(
                    userDTO,
                    dateDTO,
                    link);
            System.out.println("send request delete feedback");
            return deleted.toJson();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
