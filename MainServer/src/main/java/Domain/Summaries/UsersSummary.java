package Domain.Summaries;

public class UsersSummary {

    private Long users;
    private Long loggedUsers;
    private Long swimmers;
    private Long loggedSwimmers;
    private Long coaches;
    private Long loggedCoaches;
    private Long admins;
    private Long loggedAdmins;
    private Long researchers;
    private Long loggedResearchers;

    public UsersSummary(
            Long users,
            Long loggedUsers,
            Long swimmers,
            Long loggedSwimmers,
            Long coaches,
            Long loggedCoaches,
            Long admins,
            Long loggedAdmins,
            Long researchers,
            Long loggedResearchers) {
        this.users = users;
        this.loggedUsers = loggedUsers;
        this.swimmers = swimmers;
        this.loggedSwimmers = loggedSwimmers;
        this.coaches = coaches;
        this.loggedCoaches = loggedCoaches;
        this.admins = admins;
        this.loggedAdmins = loggedAdmins;
        this.researchers = researchers;
        this.loggedResearchers = loggedResearchers;
    }

    public Long getLoggedUsers() {
        return loggedUsers;
    }

    public Long getLoggedSwimmers() {
        return loggedSwimmers;
    }

    public Long getLoggedCoaches() {
        return loggedCoaches;
    }

    public Long getLoggedAdmins() {
        return loggedAdmins;
    }

    public Long getLoggedResearchers() {
        return loggedResearchers;
    }

    public Long getUsers() {
        return users;
    }

    public Long getSwimmers() {
        return swimmers;
    }

    public Long getCoaches() {
        return coaches;
    }

    public Long getAdmins() {
        return admins;
    }

    public Long getResearchers() {
        return researchers;
    }
}
