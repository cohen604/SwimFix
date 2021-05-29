package DTO.SwimmerDTOs;

import java.time.LocalDateTime;

public class SwimmerInvitationDTO {

    private String id;
    private String teamId;
    private DateDTO dateDTO;
    private boolean isPending;
    private boolean isApprove;
    private boolean isDenied;

    public SwimmerInvitationDTO(String id,
                                String teamId,
                                LocalDateTime dateTime,
                                boolean isPending,
                                boolean isApprove,
                                boolean isDenied) {
        this.id = id;
        this.teamId = teamId;
        this.dateDTO = new DateDTO(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());
        this.isPending = isPending;
        this.isApprove = isApprove;
        this.isDenied = isDenied;
    }

    public String getId() {
        return id;
    }

    public String getTeamId() {
        return teamId;
    }

    public DateDTO getDateDTO() {
        return dateDTO;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setDateDTO(DateDTO dateDTO) {
        this.dateDTO = dateDTO;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public boolean isApprove() {
        return isApprove;
    }

    public void setApprove(boolean approve) {
        isApprove = approve;
    }

    public boolean isDenied() {
        return isDenied;
    }

    public void setDenied(boolean denied) {
        isDenied = denied;
    }
}
