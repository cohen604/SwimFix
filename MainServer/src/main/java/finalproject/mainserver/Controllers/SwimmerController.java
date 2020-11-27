package finalproject.mainserver.Controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwimmerController {

    @GetMapping("/upload")
    public String uploadVideo() {
        return "Uploaded Video!";
    }

    @GetMapping("/viewFeedback")
    public String viewFeedBack() {
        return "View Feedback Video!";
    }

}
