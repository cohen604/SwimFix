package mainServer.Controllers;

import DTO.*;
import Domain.Streaming.IFeedbackVideo;
import com.google.gson.Gson;
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


//    @PostMapping("/report")
//    @CrossOrigin(origins = "*")
//    public String viewHistory(@RequestPart(name = "uid") String uid,
//                              @RequestPart(name = "email") String email,
//                              @RequestPart(name = "name") String name) {
//        try {
//            System.out.println("request view history");
//            UserDTO userDTO = new UserDTO(uid, email, name);
//            ActionResult<List<IFeedbackVideo>> actionResult = swimFixAPI.getSwimmerHistory(userDTO);
//            return actionResult.toJson();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
