package Storage.Coach;

import Domain.UserData.Coach;

public interface ICoachDao {

    Coach update(Coach coach);

    Coach tryInsertThenUpdate(Coach coach);
}
