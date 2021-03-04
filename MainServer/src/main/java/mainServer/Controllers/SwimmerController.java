package mainServer.Controllers;

import DTO.*;
import mainServer.SingleServiceAPI;
import mainServer.SwimFixAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/swimmer")
public class SwimmerController {

    private SwimFixAPI swimFixAPI = SingleServiceAPI.getInstance();

    @PostMapping("/feedback/link")
    @CrossOrigin(origins = "*")
    public String viewFeedBack(@RequestPart(name = "uid") String uid,
                               @RequestPart(name = "email") String email,
                               @RequestPart(name = "name") String name,
                               @RequestPart(name = "file", required = false) MultipartFile data) {
        System.out.println("Received Video for Streaming path");
        ConvertedVideoDTO convertedVideo = null;
        try {
            convertedVideo = new ConvertedVideoDTO(data.getOriginalFilename(), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserDTO userDTO = new UserDTO(uid, email, name);
        ActionResult<FeedbackVideoStreamer> actionResult =
                swimFixAPI.uploadVideoForStreamer(userDTO, convertedVideo);
        System.out.println("Streaming link generated, send link");
        return actionResult.toJson();
    }

    @PostMapping("/feedback/filter")
    @CrossOrigin(origins = "*")
    public String filterFeedback(@RequestPart(name = "uid") String uid,
                                 @RequestPart(name = "email") String email,
                                 @RequestPart(name = "name") String name,
                                 @RequestPart(name = "body") FeedbackFilterDTO filterDTO) {
        System.out.println("Received feedback video Filter DTO");
        System.out.println(filterDTO);
        System.out.println(filterDTO.getFilters());
        for(String filter: filterDTO.getFilters()) {
            System.out.println(filter);
        }
        ActionResult<FeedbackVideoStreamer> actionResult = swimFixAPI.filterFeedbackVideo(filterDTO);
        System.out.println("Streaming link generated, send link");
        return actionResult.toJson();
    }
}
