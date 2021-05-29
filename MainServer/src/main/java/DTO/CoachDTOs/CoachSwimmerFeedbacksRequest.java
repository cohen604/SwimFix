package DTO.CoachDTOs;

import DTO.UserDTOs.UserDTO;

public class CoachSwimmerFeedbacksRequest {

    private UserDTO coachDTO;
    private String swimmersEmail;

    public CoachSwimmerFeedbacksRequest(UserDTO coachDTO, String swimmersEmail) {
        this.coachDTO = coachDTO;
        this.swimmersEmail = swimmersEmail;
    }

    public UserDTO getCoachDTO() {
        return coachDTO;
    }

    public void setCoachDTO(UserDTO coachDTO) {
        this.coachDTO = coachDTO;
    }

    public String getSwimmersEmail() {
        return swimmersEmail;
    }

    public void setSwimmersEmail(String swimmersEmail) {
        this.swimmersEmail = swimmersEmail;
    }
}
