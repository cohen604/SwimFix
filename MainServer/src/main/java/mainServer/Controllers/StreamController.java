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

@RestController()
@RequestMapping("/stream")
public class StreamController {

    private SwimFixAPI swimFixAPI = SingleServiceAPI.getInstance();

    @GetMapping("/{root}/{email}/{folder}/{fileName}")
    public ResponseEntity streamFile(@PathVariable String root,
                                     @PathVariable String email,
                                     @PathVariable String folder,
                                     @PathVariable String fileName) {
        System.out.println("Received file request for streaming ");
        System.out.println("file name "+ fileName);
        ActionResult<FeedbackVideoDTO> actionResult = swimFixAPI.streamFile(
                root + "\\" + email + "\\" + folder + "\\" + fileName);
        //TODO check here if the action result is ok
        FeedbackVideoDTO videoDTO = actionResult.getValue();
        System.out.println(videoDTO.getBytes().length);
        System.out.println("File opened, send file");
        return ResponseEntity.status(HttpStatus.OK)
                .header("Accept-Ranges","bytes")
                .contentType(MediaTypeFactory.getMediaType(videoDTO.getPath()).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(videoDTO.getBytes());
    }

    @GetMapping("/{path}")
    public ResponseEntity streamFile(@PathVariable String path) {
        System.out.println("Received file request for streaming ");
        ActionResult<FeedbackVideoDTO> actionResult = swimFixAPI.streamFile(path);
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
