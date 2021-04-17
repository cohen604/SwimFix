package mainServer.Controllers;

import DTO.ActionResult;
import DTO.SendEmailInvitiationDTO;
import DTO.UserDTO;
import mainServer.SingleServiceAPI;
import mainServer.SwimFixAPI;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coach")
public class CoachController {

    private SwimFixAPI swimFixAPI = SingleServiceAPI.getInstance();

    @PostMapping(value = "/invite")
    @CrossOrigin(origins = "*")
    public String loginSwimmer(@RequestBody SendEmailInvitiationDTO invitation) {
        System.out.println("Received invite request from "+ invitation.email + " to " + invitation.to);
        UserDTO userDTO = new UserDTO(
                invitation.uid,
                invitation.email,
                invitation.name);
        ActionResult<Boolean> actionResult = swimFixAPI.invite(userDTO, invitation.to);
        System.out.println("Send invite response");
        return actionResult.toJson();
    }

}
