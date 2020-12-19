package Domain;

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
