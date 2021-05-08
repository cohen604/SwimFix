package DTO;

public class UserPermissionsDTO {

    private boolean isSwimmer;
    private boolean isCoach;
    private boolean isAdmin;
    private boolean isResearcher;

    public UserPermissionsDTO(boolean isSwimmer, boolean isCoach, boolean isAdmin, boolean isResearcher) {
        this.isSwimmer = isSwimmer;
        this.isCoach = isCoach;
        this.isAdmin = isAdmin;
        this.isResearcher = isResearcher;
    }
}
