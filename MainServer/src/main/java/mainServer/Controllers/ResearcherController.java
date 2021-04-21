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

import java.io.File;
import java.nio.file.Files;

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
            FileDTO fileDTO = null;
            if(labelsFile != null) {
                fileDTO = new FileDTO(
                    labelsFile.getOriginalFilename(),
                    labelsFile.getBytes());
            }
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
    @PostMapping("/{root}/{email}/{folder}/{fileName}")
    public ResponseEntity getFile(
            @PathVariable String root,
            @PathVariable String email,
            @PathVariable String folder,
            @PathVariable String fileName,
            @RequestBody UserDTO userDTO) {
        System.out.println("Received file request for download ");
        try {
            ActionResult<FileDownloadDTO> result =
                    _swimFixAPI.downloadFile(userDTO, root, email, folder, fileName);
            return returnResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/files/zip")
    public ResponseEntity getFilesAsZip(@RequestBody FilesDownloadRequest request) {
        System.out.println("Received files request for download as zip");
        try {
            ActionResult<FileDownloadDTO> result = _swimFixAPI.downloadFilesAsZip(request.getUser(), request.getFiles());
            return returnResponseEntity(result);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResponseEntity returnResponseEntity(ActionResult<FileDownloadDTO> result) {
        if(result != null &&  result.getResponse() == Response.SUCCESS) {
            FileDownloadDTO fileDTO = result.getValue();
            return ResponseEntity.status(HttpStatus.OK)
                    .header("Content-Disposition","attachment; filename=\"" + fileDTO.getName() + "\"")
                    .contentType(MediaTypeFactory.getMediaType(fileDTO.getPath()).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .body(fileDTO.getBytes());
        }
        return null;
    }

}
