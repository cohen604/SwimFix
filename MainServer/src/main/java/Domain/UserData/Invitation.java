package Domain.UserData;

enum Status{
    VIEW, DENY, RECIVED, ACCEPTED
}

public class Invitation {

    Status status;
    Team team;
    Swimmer swimmer;

}



