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
    public String uploadVideo(@RequestPart(name = "file", required = false) MultipartFile data) {
        System.out.println("Received Upload");
        ConvertedVideoDTO convertedVideo = null;
        try {
            //TODO get here the type of the file as paramater
            String type = ".mov";
            convertedVideo = new ConvertedVideoDTO(type, data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ActionResult<FeedbackVideoDTO> actionResult = swimFixAPI.uploadVideo(convertedVideo);
        System.out.println("Result generated, send result");
        return actionResult.toJson();
    }

    @GetMapping("/viewFeedback")
    public String viewFeedBack() {
        return "View Feedback Video!";
    }

}
