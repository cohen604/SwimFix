package Domain.UserData;

public class SwimmerInvitation extends Invitation{

    private final Object _invitationLock;

    public SwimmerInvitation(String id, String teamId, String swimmerId) {
        super(id, teamId, swimmerId);
        _invitationLock = new Object();
    }

    public SwimmerInvitation(Invitation invitation) {
        super(invitation);
        _invitationLock = new Object();
    }

    public boolean approveInvitation() {
        synchronized (_invitationLock) {
            if(this.status == InvitationStatus.PENDING) {
                this.status = InvitationStatus.ACCEPTED;
                return true;
            }
        }
        return false;
    }

    public boolean denyInvitation() {
        synchronized (_invitationLock) {
            if(this.status == InvitationStatus.PENDING) {
                this.status = InvitationStatus.DENY;
                return true;
            }
        }
        return false;
    }

    public boolean resetInvitation() {
        synchronized (_invitationLock) {
            if(this.status == InvitationStatus.ACCEPTED || this.status == InvitationStatus.DENY) {
                this.status = InvitationStatus.PENDING;
                return true;
            }
        }
        return false;
    }
}