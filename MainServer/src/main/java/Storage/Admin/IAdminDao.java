package Storage.Admin;

import Domain.UserData.Admin;

public interface IAdminDao {

    Admin update(Admin value);

    Admin tryInsertThenUpdate(Admin admin);
}
