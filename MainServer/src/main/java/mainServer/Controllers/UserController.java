package mainServer.Controllers;

import DTO.ActionResult;
import DTO.UserDTOs.UserDTO;
import DTO.UserDTOs.UserPermissionsDTO;
import mainServer.SingleServiceAPI;
import mainServer.SwimFixAPI;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private SwimFixAPI swimFixAPI = SingleServiceAPI.getInstance();

    @PostMapping(value = "/login")
    @CrossOrigin(origins = "*")
    public String loginSwimmer(@RequestBody UserDTO user) {
        System.out.println("Received login request from "+user.getEmail());
        ActionResult<UserDTO> actionResult = swimFixAPI.login(user);
        System.out.println("Send Login response");
        return actionResult.toJson();
    }

    @PostMapping(value = "/logout")
    @CrossOrigin(origins = "*")
    public String logoutSwimmer(@RequestBody UserDTO user) {
        System.out.println("Received logout request from "+user.getEmail());
        ActionResult<Boolean> actionResult = swimFixAPI.logout(user);
        System.out.println("Send Logout response");
        return actionResult.toJson();
    }

    @PostMapping(value = "/permissions")
    @CrossOrigin(origins = "*")
    public String userPermissions(@RequestBody UserDTO user) {
        System.out.println("Received user permissions request from "+user.getEmail());
        ActionResult<UserPermissionsDTO> actionResult = swimFixAPI.getPermissions(user);
        System.out.println("Send user permissions response");
        return actionResult.toJson();
    }
}
