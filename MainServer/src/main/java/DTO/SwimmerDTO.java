package DTO;

public class SwimmerDTO {

    private String uid;
    private String email;
    private String name;

    public SwimmerDTO() {
    }

    public SwimmerDTO(String uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;
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
}
