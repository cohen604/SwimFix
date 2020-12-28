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
public class SwimmerController {

    private SwimFixAPI swimFixAPI = SingleServiceAPI.getInstance();

    //TODO delete this
    @PostMapping("/uploadForDownload")
    @CrossOrigin(origins = "*")
    public String uploadVideo(@RequestPart(name = "file", required = false) MultipartFile data) {
        System.out.println("Received Video for Download feedback");
        ConvertedVideoDTO convertedVideo = null;
        try {
            convertedVideo = new ConvertedVideoDTO(data.getOriginalFilename(), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ActionResult<FeedbackVideoDTO> actionResult = swimFixAPI.uploadVideoForDownload(convertedVideo);
        //TODO check here if the action result is ok
        System.out.println("Feedback generated, send feedback");
        return actionResult.toJson();
    }

    @PostMapping("/uploadForStream")
    @CrossOrigin(origins = "*")
    public String viewFeedBack(@RequestPart(name = "file", required = false) MultipartFile data) {
        System.out.println("Received Video for Streaming path");
        ConvertedVideoDTO convertedVideo = null;
        try {
            convertedVideo = new ConvertedVideoDTO(data.getOriginalFilename(), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ActionResult<FeedbackVideoStreamer> actionResult = swimFixAPI.uploadVideoForStreamer(convertedVideo);
        System.out.println("Streaming link generated, send link");
        return actionResult.toJson();
    }

    @PostMapping("/filterFeedback")
    @CrossOrigin(origins = "*")
    public String filterFeedback(@RequestBody FeedbackFilterDTO filterDTO) {
        System.out.println("Received feedback video Filter DTO");
        for(String filter: filterDTO.getFilters()) {
            System.out.println(filter);
        }
        ActionResult<FeedbackVideoStreamer> actionResult = swimFixAPI.filterFeedbackVideo(filterDTO);
        System.out.println("Streaming link generated, send link");
        return actionResult.toJson();
    }

    @GetMapping("/stream/{folder}/{fileName}")
    public ResponseEntity streamFile(@PathVariable String folder, @PathVariable String fileName) {
        System.out.println("Received file request for streaming");
        ActionResult<FeedbackVideoDTO> actionResult = swimFixAPI.streamFile(folder+"/"+fileName);
        //TODO check here if the action result is ok
        FeedbackVideoDTO videoDTO = actionResult.getValue();
        System.out.println(videoDTO.getBytes().length);
        System.out.println("File opened, send file");
        return ResponseEntity.status(HttpStatus.OK)
                .header("Accept-Ranges","bytes")
                .contentType(MediaTypeFactory.getMediaType(videoDTO.getPath()).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(videoDTO.getBytes());
    }

}
