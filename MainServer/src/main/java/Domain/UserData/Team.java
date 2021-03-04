package Domain.UserData;

import Domain.UserData.Coach;
import Domain.UserData.Invitation;
import Domain.UserData.Swimmer;

import java.util.HashMap;

public class Team {

    // team id
    String tid;
    Coach coach;
    // <key, swimmer> = <swimmer id>
    HashMap<String, Swimmer> swimmers;
    // <key, invitation> = <swimmer id>
    HashMap<String, Invitation> invatation;

}
