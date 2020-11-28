package finalproject.mainserver.Controllers;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwimmerController {

    @GetMapping("/upload")
    @CrossOrigin(origins = "*")
    public String uploadVideo() {
        System.out.println("Received Upload");
        return "Uploaded Video!";
    }

    @GetMapping("/viewFeedback")
    public String viewFeedBack() {
        return "View Feedback Video!";
    }

}
