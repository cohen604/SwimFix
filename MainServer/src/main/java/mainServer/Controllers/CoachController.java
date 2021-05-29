package mainServer.Controllers;

import DTO.ActionResult;
import DTO.CoachDTOs.InvitationResponseDTO;
import DTO.CoachDTOs.SendEmailInvitiationDTO;
import DTO.CoachDTOs.TeamDTO;
import DTO.UserDTOs.UserDTO;
import mainServer.SingleServiceAPI;
import mainServer.SwimFixAPI;
import org.springframework.web.bind.annotation.*;

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

}
