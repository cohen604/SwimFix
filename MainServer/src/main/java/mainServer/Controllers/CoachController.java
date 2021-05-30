package mainServer.Controllers;

import DTO.ActionResult;
import DTO.CoachDTOs.*;
import DTO.UserDTOs.UserDTO;
import mainServer.SingleServiceAPI;
import mainServer.SwimFixAPI;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coach")
public class CoachController {

    private SwimFixAPI swimFixAPI = SingleServiceAPI.getInstance();

    @PostMapping(value = "/invite")
    @CrossOrigin(origins = "*")
    public String sendInvitationEmail(@RequestBody SendEmailInvitiationDTO invitation) {
        try {
            System.out.println("Received invite request from " + invitation.getEmail() + " to " + invitation.getTo());
            UserDTO userDTO = new UserDTO(
                    invitation.getUid(),
                    invitation.getEmail(),
                    invitation.getName());
            ActionResult<InvitationResponseDTO> actionResult = swimFixAPI.invite(userDTO, invitation.getTo());
            System.out.println("Send invite response");
            return actionResult.toJson();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/team")
    @CrossOrigin(origins = "*")
    public String getCoachTeam(@RequestBody UserDTO userDTO) {
        try {
            System.out.println("Received coach team request");
            ActionResult<TeamDTO> actionResult = swimFixAPI.getCoachTeam(userDTO);
            System.out.println("Send coach team request");
            return actionResult.toJson();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/swimmer/feedbacks")
    @CrossOrigin(origins = "*")
    public String coachGetSwimmerFeedbacks(@RequestBody CoachSwimmerFeedbacksRequest request) {
        try {
            System.out.println("Received coach get swimmers feedbacks request");
            UserDTO coachDto = request.getCoachDTO();
            String swimmerEmail = request.getSwimmersEmail();
            ActionResult<List<CoachSwimmerFeedbackDTO>> result = swimFixAPI.coachGetSwimmerFeedbacks(coachDto, swimmerEmail);
            System.out.println("Send coach swimmers feedbacks");
            return result.toJson();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/swimmer/feedback")
    @CrossOrigin(origins = "*")
    public String coachGetSwimmerFeedback(@RequestBody CoachFeedbackRequestDTO requestDTO) {
        return null;
    }

    @PostMapping("/swimmer/feedback/comment/add")
    @CrossOrigin(origins = "*")
    public String coachAddCommentToFeedback() {
        return null;
    }



}
