package mainServer.Controllers;

import DTO.*;
import com.google.gson.Gson;
import mainServer.SingleServiceAPI;
import mainServer.SwimFixAPI;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
            System.out.println("Sending the researcher report");
            return result.toJson();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
