package Domain.UserData;
import Domain.UserData.Interfaces.ISwimmer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Team {

    private String name;
    private LocalDateTime openDate;
    private String coachId;
    private HashMap<String, ISwimmer> swimmers;
    private AtomicInteger sendInvitations;
    private List<Invitation> invitations;

    public Team(String name, String coachId) {
        this.name = name;
        this.coachId = coachId;
        this.openDate = LocalDateTime.now();
        this.swimmers = new HashMap<>();
        this.sendInvitations = new AtomicInteger(0);
        this.invitations = new LinkedList<>();
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
        this.sendInvitations = new AtomicInteger(sendInvitations);
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
        return sendInvitations.get();
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }
}
