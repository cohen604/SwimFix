package Domain.UserData.Interfaces;

public interface IUser {

    String getUid();

    String getEmail();

    String getName();

    boolean isLogged();

    boolean login();

    boolean logout();

}
