package Domain.UserData.Interfaces;

import Domain.UserData.Invitation;
import java.util.Collection;

public interface ISwimmer {

    String getEmail();

    boolean addInvitation(Invitation invitation);

    Collection<? extends IInvitation> getInvitations();

    Collection<? extends IInvitation> getInvitationsHistory();

    int getNumberOfFeedbacks();

}
