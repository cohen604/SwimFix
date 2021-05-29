package Storage.Invitation;

import Domain.UserData.Invitation;

public interface IInvitationDao {

    Invitation update(Invitation invitation);

    Invitation tryInsertThenUpdate(Invitation invitation);
}
