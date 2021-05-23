package Domain.UserData;

public class SwimmerInvitation extends Invitation{

    public SwimmerInvitation(String id, String teamId, String swimmerId) {
        super(id, teamId, swimmerId);
    }

    public SwimmerInvitation(Invitation invitation) {
        super(invitation);
    }
}