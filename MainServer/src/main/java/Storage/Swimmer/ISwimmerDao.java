package Storage.Swimmer;

import Domain.UserData.Swimmer;

public interface ISwimmerDao {

    Swimmer update(Swimmer value);

    Swimmer tryInsertThenUpdate(Swimmer swimmer);
}
