package DTO.SwimmerDTOs;

import java.time.LocalDateTime;

public class SwimmerInvitationDTO {

    private String teamId;
    private DateDTO dateDTO;

    public SwimmerInvitationDTO(String teamId, DateDTO dateDTO) {
        this.teamId = teamId;
        this.dateDTO = dateDTO;
    }

    public SwimmerInvitationDTO(String teamId, LocalDateTime dateTime) {
        this.teamId = teamId;
        this.dateDTO = new DateDTO(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());
    }

    public String getTeamId() {
        return teamId;
    }

    public DateDTO getDateDTO() {
        return dateDTO;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setDateDTO(DateDTO dateDTO) {
        this.dateDTO = dateDTO;
    }
}
