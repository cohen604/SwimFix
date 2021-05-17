package Domain.UserData;

public class Admin {

    private String _email;
    private boolean _researcherAuthorized;
    private boolean _adminAuthorized;

    public Admin(String email) {
        _email = email;
        _researcherAuthorized = true;
        _adminAuthorized = true;
    }

    public Admin(String email, boolean researcherAuthorized, boolean adminAuthorized) {
        _email = email;
        _researcherAuthorized = researcherAuthorized;
        _adminAuthorized = adminAuthorized;
    }

    public String getEmail() {
        return _email;
    }

    public boolean isResearcherAuthorized() {
        return _researcherAuthorized;
    }

    public boolean isAdminAuthorized() {
        return _adminAuthorized;
    }
}
