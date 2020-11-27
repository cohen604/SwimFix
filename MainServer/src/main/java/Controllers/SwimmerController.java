package Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/swimmer")
public class SwimmerController {


    @GetMapping("/upload")
    public String uploadVideo() {
        return String.format("Uploaded Video!");
    }

    @GetMapping("/viewFeedback")
    public String viewFeedBack() {
        return String.format("View Feedback Video!");
    }

}
