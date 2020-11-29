package mainServer.Controllers;

import DTO.ActionResult;
import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;
import mainServer.SingleServiceAPI;
import mainServer.SwimFixAPI;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
public class SwimmerController {

    private SwimFixAPI swimFixAPI = SingleServiceAPI.getInstance();

    @PostMapping("/upload")
    @CrossOrigin(origins = "*")
    public ActionResult<FeedbackVideoDTO> uploadVideo(@RequestParam (name="id") String id, @RequestBody MultipartFile data) {
        System.out.println("Received Upload");
        ConvertedVideoDTO convertedVideo = null;
        try {
            convertedVideo = new ConvertedVideoDTO(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ActionResult<FeedbackVideoDTO> actionResult = swimFixAPI.uploadVideo(convertedVideo);
        System.out.println("Result generated, send result");
        return actionResult;
    }

    @GetMapping("/viewFeedback")
    public String viewFeedBack() {
        return "View Feedback Video!";
    }

}
