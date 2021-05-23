package Domain.UserData;

import java.time.LocalDateTime;

public class Invitation {

    private String id;
    private String teamId;
    private String swimmerId;
    private LocalDateTime creationTime;
    private InvitationStatus status;

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

    public InvitationStatus getStatus() {
        return status;
    }
}



