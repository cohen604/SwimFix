package Domain.UserData;
import Domain.UserData.Interfaces.ISwimmer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public class Team {

    private String name;
    private LocalDateTime openDate;
    private String coachId;
    private HashMap<String, ISwimmer> swimmers;
    private int sendInvitations;
    private List<Invitation> invitations;

    public Team(String name, String coachId) {
        this.name = name;
        this.coachId = coachId;
    }

    public Team(
            String name,
            LocalDateTime openDate,
            String coachId,
            HashMap<String, ISwimmer> swimmers,
            int sendInvitations,
            List<Invitation> invitations) {
        this.name = name;
        this.openDate = openDate;
        this.coachId = coachId;
        this.swimmers = swimmers;
        this.sendInvitations = sendInvitations;
        this.invitations = invitations;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getOpenDate() {
        return openDate;
    }

    public String getCoachId() {
        return coachId;
    }

    public HashMap<String, ISwimmer> getSwimmers() {
        return swimmers;
    }

    public int getSendInvitations() {
        return sendInvitations;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }
}
