package mainServer.Controllers;

import DTO.*;
import com.google.gson.Gson;
import mainServer.SingleServiceAPI;
import mainServer.SwimFixAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@RestController
@RequestMapping("/researcher")
public class ResearcherController {

    private SwimFixAPI _swimFixAPI = SingleServiceAPI.getInstance();

    @PostMapping("/report")
    @CrossOrigin(origins = "*")
    public String getResearcherReport(
            @RequestPart(name = "uid") String uid,
            @RequestPart(name = "email") String email,
            @RequestPart(name = "name") String name,
            @RequestPart(name = "video", required = false) MultipartFile videoFile,
            @RequestPart(name = "labels", required = false) MultipartFile labelsFile) {
        System.out.println("Received Video And Labels for report");
        try {
            UserDTO userDTO = new UserDTO(uid, email, name);
            ConvertedVideoDTO videoDTO = new ConvertedVideoDTO(
                    videoFile.getOriginalFilename(),
                    videoFile.getBytes());
            FileDTO fileDTO = new FileDTO(
                    labelsFile.getOriginalFilename(),
                    labelsFile.getBytes());
            ActionResult<ResearcherReportDTO> result = _swimFixAPI.getResearcherReport(
                    userDTO,
                    videoDTO,
                    fileDTO
            );
            System.out.println("Sending the researcher report "+result.toJson());
            return result.toJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{root}/{email}/{folder}/{fileName}")
    public ResponseEntity getFile(
            @PathVariable String root,
            @PathVariable String email,
            @PathVariable String folder,
            @PathVariable String fileName) {
        System.out.println("Received file request for download ");
        String path = root + "\\" + email + "\\" + folder + "\\" + fileName;
        File file = new File(path);
        if (file.exists()) {
            try {
                byte[] data = Files.readAllBytes(file.toPath());
                return ResponseEntity.status(HttpStatus.OK)
                        .header("Content-Disposition","attachment; filename=\"" + file.getName() + "\"")
                        .contentType(MediaTypeFactory.getMediaType(file.getPath()).orElse(MediaType.APPLICATION_OCTET_STREAM))
                        .body(data);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
