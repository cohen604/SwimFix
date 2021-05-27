package Domain.UserData.Interfaces;

import java.time.LocalDateTime;

public interface IInvitation {

    String getTeamId();

    LocalDateTime getCreationTime();
}
