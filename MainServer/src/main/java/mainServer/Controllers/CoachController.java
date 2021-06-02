package mainServer.Controllers;

import DTO.ActionResult;
import DTO.CoachDTOs.*;
import DTO.FeedbackDTOs.FeedbackDataDTO;
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
    public String coachGetSwimmerFeedbacks(@RequestBody CoachAccessSwimmerRequest request) {
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
        try {
            System.out.println("Received coach get swimmer feedback");
            UserDTO userDTO = requestDTO.getCoachDTO();
            String swimmerEmail = requestDTO.getSwimmerEmail();
            String feedbackKey = requestDTO.getKey();
            ActionResult<FeedbackDataDTO> result = swimFixAPI.coachGetSwimmerFeedback(
                    userDTO,
                    swimmerEmail,
                    feedbackKey
            );
            System.out.println("Send coach get swimmer feedback");
            return result.toJson();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/swimmer/feedback/comment/add")
    @CrossOrigin(origins = "*")
    public String coachAddCommentToFeedback(@RequestBody CoachAddCommentDTO commentDTO) {
        try {
            System.out.println("Received coach add comment to feedback");
            UserDTO coachDTO = commentDTO.getCoachDTO();
            String swimmerEmail = commentDTO.getSwimmerEmail();
            String feedbackKey = commentDTO.getKey();
            String commentText = commentDTO.getCommentText();
            ActionResult<Boolean> response = swimFixAPI.coachAddCommentToFeedback(
                    coachDTO,
                    swimmerEmail,
                    feedbackKey,
                    commentText
            );
            System.out.println("Send coach add comment to feedback");
            return response.toJson();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/swimmer/remove")
    @CrossOrigin(origins = "*")
    public String coachRemoveSwimmerFromTeam(@RequestBody CoachAccessSwimmerRequest request) {
        try {
            System.out.println("Received coach add comment to feedback");
            UserDTO coachDTO = request.getCoachDTO();
            String swimmerEmail = request.getSwimmersEmail();
            ActionResult<Boolean> actionResult = swimFixAPI.coachRemoveSwimmer(coachDTO, swimmerEmail);
            System.out.println("Send coach removed swimmer from team");
            return actionResult.toJson();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
