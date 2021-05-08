package AcceptanceTests.Bridge;

import AcceptanceTests.Data.SwimFIxAPIMock;
import DTO.*;
import mainServer.SwimFixAPI;

public class AcceptanceTestsReal implements AcceptanceTestsBridge {

    private SwimFIxAPIMock swimFixAPI;

    public AcceptanceTestsReal() {
        this.swimFixAPI = new SwimFIxAPIMock();
    }

    @Override
    public boolean uploadVideoForStreamer(String user, byte[] video) {
        if(this.swimFixAPI != null) {
            return this.swimFixAPI.uploadVideoForStreamer(user, video);

        }
        return false;
    }

    @Override
    public String streamFile(String user, String path) {
        if(this.swimFixAPI != null) {
            return this.swimFixAPI.viewVideo(user, path);
        }
        return null;
    }

    @Override
    public boolean login(String uid, String email, String name) {
        UserDTO userDTO = new UserDTO(uid, email, name);
        if(this.swimFixAPI != null) {
            return this.swimFixAPI.login(userDTO);
        }
        return false;
    }

    @Override
    public boolean logout(String uid) {
        if(this.swimFixAPI != null) {
            return this.swimFixAPI.logout(uid);
        }
        return false;
    }
}
