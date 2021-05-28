package DTO.SwimmerDTOs;

public class MyTeamDTO {

    private boolean hasTeam;
    private String teamId;

    public MyTeamDTO(boolean hasTeam, String teamId) {
        this.hasTeam = hasTeam;
        this.teamId = teamId;
    }

    public boolean isHasTeam() {
        return hasTeam;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setHasTeam(boolean hasTeam) {
        this.hasTeam = hasTeam;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
