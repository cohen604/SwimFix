package Domain.UserData.Interfaces;

import Domain.UserData.Swimmer;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ITeam {

    String getName();

    LocalDateTime getOpenDate();

    String getCoachId();

    Collection<? extends ISwimmer> getSwimmersCollection();

    Collection<? extends IInvitation> getInvitationsCollection();

    boolean hasSwimmer(ISwimmer swimmer);
}
