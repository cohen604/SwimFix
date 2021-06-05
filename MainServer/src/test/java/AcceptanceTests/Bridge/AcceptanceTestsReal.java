package AcceptanceTests.Bridge;

import DTO.FeedbackDTOs.ConvertedVideoDTO;
import DTO.Response;
import DTO.UserDTOs.UserDTO;
import mainServer.SwimFixAPI;

public class AcceptanceTestsReal implements AcceptanceTestsBridge {

    private SwimFixAPI swimFixAPI;

    public AcceptanceTestsReal() {
        this.swimFixAPI = SwimFixDriver.swimFixAPIFactory();
    }

    @Override
    public boolean uploadVideoForStreamer(String user, byte[] video) {
        if(this.swimFixAPI != null) {
            UserDTO u = new UserDTO(user, "foo@bar.com", "foo");
            ConvertedVideoDTO v = new ConvertedVideoDTO(".mp4", video);
            return this.swimFixAPI.uploadVideoForStreamer(u, v).getResponse() == Response.SUCCESS;

        }
        return false;
    }

    @Override
    public boolean streamFile(String user, String path) {
        if(this.swimFixAPI != null) {
            return this.swimFixAPI.streamFile(path).getResponse() == Response.SUCCESS;
        }
        return false;
    }

    @Override
    public boolean login(String uid, String email, String name) {
        UserDTO userDTO = new UserDTO(uid, email, name);
        if(this.swimFixAPI != null) {
            return this.swimFixAPI.login(userDTO).getResponse() == Response.SUCCESS;
        }
        return false;
    }

    @Override
    public boolean logout(String uid) {
        if(this.swimFixAPI != null) {
            //return this.swimFixAPI.logout(uid);
        }
        return false;
    }
}
