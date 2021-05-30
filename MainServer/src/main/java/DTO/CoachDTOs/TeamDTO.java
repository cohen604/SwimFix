package DTO.CoachDTOs;

import DTO.SwimmerDTOs.DateDayDTO;
import DTO.SwimmerDTOs.SwimmerDTO;
import DTO.SwimmerDTOs.SwimmerInvitationDTO;

import java.util.List;

public class TeamDTO {

    private String name;
    private DateDayDTO dateDayDTO;
    private String coachId;
    private List<SwimmerDTO> swimmers;
    private List<SwimmerInvitationDTO> invitations;

    public TeamDTO(String name, DateDayDTO dateDayDTO, String coachId, List<SwimmerDTO> swimmers, List<SwimmerInvitationDTO> invitations) {
        this.name = name;
        this.dateDayDTO = dateDayDTO;
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

    public DateDayDTO getDateDayDTO() {
        return dateDayDTO;
    }

    public void setDateDayDTO(DateDayDTO dateDayDTO) {
        this.dateDayDTO = dateDayDTO;
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
