package Domain;

import java.util.HashMap;

public class Team {

    Coach coach;
    // <key, swimmer> = <swimmer id>
    HashMap<String, Swimmer> swimmers;
    // <key, invitation> = <swimmer id>
    HashMap<String, Invitation> invatation;

}
