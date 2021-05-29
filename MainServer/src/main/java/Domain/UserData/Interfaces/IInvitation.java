package Domain.UserData.Interfaces;

import java.time.LocalDateTime;

public interface IInvitation {

    String getId();

    String getTeamId();

    LocalDateTime getCreationTime();

}
