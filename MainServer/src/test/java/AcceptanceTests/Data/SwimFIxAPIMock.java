package AcceptanceTests.Data;

import DTO.ConvertedVideoDTO;
import DTO.UserDTO;
import Domain.UserData.User;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SwimFIxAPIMock {

    private List<String> logged;
    private HashMap<String, List<byte[]>> videos;

    public SwimFIxAPIMock(){
        logged = new ArrayList<>();
        videos = new HashMap<>();

    }

    public boolean login(UserDTO userDTO){
        if (logged.contains(userDTO.getUid())){
            return false;
        }
        String uid = userDTO.getUid();
        String email = userDTO.getEmail();
        String name = userDTO.getName();
        for (int i = 0; i < DataBase.Users.length; i++){
            String[] user = DataBase.Users[i];
            if(Integer.toString(i).equals(uid) && email.equals(user[0]) && name.equals(user[1])){
                logged.add(Integer.toString(i));
                this.videos.put(uid, new ArrayList<>());
                return true;
            }
        }
        return false;
    }

    public boolean logout(String user){
        if (logged.contains(user)){
            logged.remove(user);
            return true;
        }
        return false;
    }

    public String viewVideo(String uid, String path){
        if (!logged.contains(uid)){
            return null;
        }
        return DataBase.videos.getOrDefault(path, null);
    }

    public boolean uploadVideoForStreamer(String uid, byte[] video) {
        List<byte[]> userVideos = this.videos.get(uid);

        if (userVideos == null || userVideos.contains(video)){
            return false;
        }
        else{
            userVideos.add(video);
            return true;
        }
    }
}
