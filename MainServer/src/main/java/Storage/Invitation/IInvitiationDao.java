package Storage.Invitation;

import Domain.UserData.Invitation;

public interface IInvitiationDao {

    Invitation update(Invitation invitation);

    Invitation tryInsertThenUpdate(Invitation invitation);
}
