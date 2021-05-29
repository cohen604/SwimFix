package AcceptanceTests.Bridge;

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
            //return this.swimFixAPI.uploadVideoForStreamer(user, video);

        }
        return false;
    }

    @Override
    public String streamFile(String user, String path) {
        if(this.swimFixAPI != null) {
            //return this.swimFixAPI.viewVideo(user, path);
        }
        return null;
    }

    @Override
    public boolean login(String uid, String email, String name) {
        UserDTO userDTO = new UserDTO(uid, email, name);
        if(this.swimFixAPI != null) {
            //return this.swimFixAPI.login(userDTO);
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
