package DTO;

public class SummaryDTO {

    private Long users;
    private Long loggedUsers;
    private Long swimmers;
    private Long loggedSwimmers;
    private Long coaches;
    private Long loggedCoaches;
    private Long researchers;
    private Long loggedResearchers;
    private Long admins;
    private Long loggedAdmins;
    private Long feedbacks;

    public SummaryDTO(
            Long users,
            Long loggedUsers,
            Long swimmers,
            Long loggedSwimmers,
            Long coaches,
            Long loggedCoaches,
            Long researchers,
            Long loggedResearchers,
            Long admins,
            Long loggedAdmins,
            Long feedbacks) {
        this.users = users;
        this.loggedUsers = loggedUsers;
        this.swimmers = swimmers;
        this.loggedSwimmers = loggedSwimmers;
        this.coaches = coaches;
        this.loggedCoaches = loggedCoaches;
        this.researchers = researchers;
        this.loggedResearchers = loggedResearchers;
        this.admins = admins;
        this.loggedAdmins = loggedAdmins;
        this.feedbacks = feedbacks;
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

    public Long getResearchers() {
        return researchers;
    }

    public Long getAdmins() {
        return admins;
    }

    public Long getFeedbacks() {
        return feedbacks;
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

    public Long getLoggedResearchers() {
        return loggedResearchers;
    }

    public Long getLoggedAdmins() {
        return loggedAdmins;
    }
}
