package mainServer.Controllers;
import DTO.ActionResult;
import DTO.AdminAddRequestDTO;
import DTO.SummaryDTO;
import DTO.UserDTO;
import mainServer.SingleServiceAPI;
import mainServer.SwimFixAPI;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private SwimFixAPI swimFixAPI;

    public AdminController() {
        swimFixAPI = SingleServiceAPI.getInstance();
    }

    @PostMapping("/search/users/not/admins")
    @CrossOrigin(origins = "*")
    public String findUsersThatNotAdmin(@RequestBody UserDTO userDTO) {
        System.out.println("Received admin search request for users that not admin");
        try {
            ActionResult<List<UserDTO>> users = swimFixAPI.findUsersThatNotAdmin(userDTO);
            System.out.println("Sending admin search request for users that not admin");
            return users.toJson();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/add/admin")
    @CrossOrigin(origins = "*")
    public String addAdmin(@RequestBody AdminAddRequestDTO addRequestDTO) {
        System.out.println("Received add admin request");
        try {
            UserDTO admin = addRequestDTO.getAdmin();
            UserDTO addToUser = addRequestDTO.getUser();
            ActionResult<Boolean> added = swimFixAPI.addAdmin(admin, addToUser);
            System.out.println("Sending add admin request response");
            return added.toJson();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/search/users/not/researchers")
    @CrossOrigin(origins = "*")
    public String findUsersThatNotResearcher(@RequestBody UserDTO userDTO) {
        System.out.println("Received admin search request for users that not researcher");
        try {
            ActionResult<List<UserDTO>> users = swimFixAPI.findUsersThatNotResearcher(userDTO);
            System.out.println("Sending admin search request for users that not researcher");
            return users.toJson();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/add/researcher")
    @CrossOrigin(origins = "*")
    public String addResearcher(@RequestBody AdminAddRequestDTO addRequestDTO) {
        System.out.println("Received add researcher request");
        try {
            UserDTO admin = addRequestDTO.getAdmin();
            UserDTO addToUser = addRequestDTO.getUser();
            ActionResult<Boolean> added = swimFixAPI.addResearcher(admin, addToUser);
            System.out.println("Sending add researcher request response");
            return added.toJson();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/summary")
    @CrossOrigin(origins = "*")
    public String getSummary(@RequestBody UserDTO admin) {
        System.out.println("Received get summary request");
        try {
            ActionResult<SummaryDTO> result = swimFixAPI.getSummary(admin);
            System.out.println("Sending get summary response");
            return result.toJson();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
