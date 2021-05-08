package DTO;

public class SendEmailInvitiationDTO {

    private String uid;
    private String email;
    private String name;
    private String to;

    public SendEmailInvitiationDTO(String uid, String email, String name, String to) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.to = to;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getTo() {
        return to;
    }
}
