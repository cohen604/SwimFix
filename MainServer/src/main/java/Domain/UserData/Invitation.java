package Domain.UserData;

import Domain.UserData.Interfaces.IInvitation;

import java.time.LocalDateTime;

public class Invitation implements IInvitation {

    private String id;
    private String teamId;
    private String swimmerId;
    private LocalDateTime creationTime;
    protected InvitationStatus status;

    public Invitation(String id, String teamId, String swimmerId) {
        this.id = id;
        this.teamId = teamId;
        this.swimmerId = swimmerId;
        this.creationTime = LocalDateTime.now();
        this.status = InvitationStatus.PENDING;
    }

    public Invitation(String id, String teamId, String swimmerId, LocalDateTime time, InvitationStatus status) {
        this.id = id;
        this.teamId = teamId;
        this.swimmerId = swimmerId;
        this.creationTime = time;
        this.status = status;
    }

    public Invitation(Invitation other) {
        this.id = other.id;
        this.teamId = other.teamId;
        this.swimmerId = other.swimmerId;
        this.creationTime = other.creationTime;
        this.status = other.status;
    }

    public String getId() {
        return id;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getSwimmerId() {
        return swimmerId;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public boolean isPending() {
        return status == InvitationStatus.PENDING;
    }

    @Override
    public boolean isApprove() {
        return status == InvitationStatus.ACCEPTED;
    }

    @Override
    public boolean isDenied() {
        return status == InvitationStatus.DENY;
    }

    public InvitationStatus getStatus() {
        return status;
    }
}



