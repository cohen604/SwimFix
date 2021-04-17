package DTO;

public class SendEmailInvitiationDTO {

    public String uid;
    public String email;
    public String name;
    public String to;

    public SendEmailInvitiationDTO(String uid, String email, String name, String to) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.to = to;
    }
}
