package DTO.SwimmerDTOs;

import java.time.LocalDateTime;

public class SwimmerInvitationDTO {

    private String id;
    private String teamId;
    private String email;
    private DateDayDTO dateDayDTO;
    private boolean isPending;
    private boolean isApprove;
    private boolean isDenied;

    public SwimmerInvitationDTO(String id,
                                String teamId,
                                String email,
                                LocalDateTime dateTime,
                                boolean isPending,
                                boolean isApprove,
                                boolean isDenied) {
        this.id = id;
        this.teamId = teamId;
        this.email = email;
        this.dateDayDTO = new DateDayDTO(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());
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

    public DateDayDTO getDateDayDTO() {
        return dateDayDTO;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setDateDayDTO(DateDayDTO dateDayDTO) {
        this.dateDayDTO = dateDayDTO;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
