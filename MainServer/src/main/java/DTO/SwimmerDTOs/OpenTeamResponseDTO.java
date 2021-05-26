package DTO.SwimmerDTOs;

public class OpenTeamResponseDTO {

    private String teamName;
    private boolean isAdded;
    private boolean isAlreadyExists;
    private boolean isAlreadyCoach;

    public OpenTeamResponseDTO(String teamName) {
        this.teamName = teamName;
        this.isAdded = false;
        this.isAlreadyExists = false;
        this.isAlreadyCoach = false;
    }

    public OpenTeamResponseDTO(String teamName, boolean isAdded, boolean isAlreadyExists, boolean isAlreadyCoach) {
        this.teamName = teamName;
        this.isAdded = isAdded;
        this.isAlreadyExists = isAlreadyExists;
        this.isAlreadyCoach = isAlreadyCoach;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public void setAlreadyExists(boolean alreadyExists) {
        isAlreadyExists = alreadyExists;
    }

    public void setAlreadyCoach(boolean alreadyCoach) {
        isAlreadyCoach = alreadyCoach;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public boolean isAlreadyExists() {
        return isAlreadyExists;
    }

    public boolean isAlreadyCoach() {
        return isAlreadyCoach;
    }
}
