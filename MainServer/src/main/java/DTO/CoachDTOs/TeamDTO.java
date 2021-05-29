package DTO.CoachDTOs;

import DTO.SwimmerDTOs.DateDTO;
import DTO.SwimmerDTOs.SwimmerDTO;
import DTO.SwimmerDTOs.SwimmerInvitationDTO;

import java.util.List;

public class TeamDTO {

    private String name;
    private DateDTO dateDTO;
    private String coachId;
    private List<SwimmerDTO> swimmers;
    private List<SwimmerInvitationDTO> invitations;

    public TeamDTO(String name, DateDTO dateDTO, String coachId, List<SwimmerDTO> swimmers, List<SwimmerInvitationDTO> invitations) {
        this.name = name;
        this.dateDTO = dateDTO;
        this.coachId = coachId;
        this.swimmers = swimmers;
        this.invitations = invitations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateDTO getDateDTO() {
        return dateDTO;
    }

    public void setDateDTO(DateDTO dateDTO) {
        this.dateDTO = dateDTO;
    }

    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public List<SwimmerDTO> getSwimmers() {
        return swimmers;
    }

    public void setSwimmers(List<SwimmerDTO> swimmers) {
        this.swimmers = swimmers;
    }

    public List<SwimmerInvitationDTO> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<SwimmerInvitationDTO> invitations) {
        this.invitations = invitations;
    }
}
