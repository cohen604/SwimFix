package DTO.SwimmerDTOs;

import java.time.LocalDateTime;

public class SwimmerInvitationDTO {

    private String id;
    private String teamId;
    private DateDTO dateDTO;


    public SwimmerInvitationDTO(String id, String teamId, DateDTO dateDTO) {
        this.id = id;
        this.teamId = teamId;
        this.dateDTO = dateDTO;
    }

    public SwimmerInvitationDTO(String id, String teamId, LocalDateTime dateTime) {
        this.id = id;
        this.teamId = teamId;
        this.dateDTO = new DateDTO(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());
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
}
