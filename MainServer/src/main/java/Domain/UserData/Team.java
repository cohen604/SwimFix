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
    private List<Invitation> invitations;
    private int sendInvitations;

    public Team(String name, String coachId) {
        this.name = name;
        this.coachId = coachId;
    }

    public Team(
            String name,
            LocalDateTime openDate,
            String coachId,
            HashMap<String, ISwimmer> swimmers,
            List<Invitation> invitations) {
        this.name = name;
        this.openDate = openDate;
        this.coachId = coachId;
        this.swimmers = swimmers;
        this.invitations = invitations;
    }
}
