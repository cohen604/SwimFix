package mainServer.Controllers;

import DTO.ActionResult;
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
    public String loginSwimmer(@RequestPart(name = "uid") String uid,
                               @RequestPart(name = "email") String email,
                               @RequestPart(name = "name") String name,
                               @RequestPart(name = "to") String to) {
        System.out.println("Received invite request from "+ email + " to " + to);
        UserDTO userDTO = new UserDTO(uid, email, name);
        ActionResult<Boolean> actionResult = swimFixAPI.invite(userDTO, to);
        System.out.println("Send invite response");
        return actionResult.toJson();
    }

}
