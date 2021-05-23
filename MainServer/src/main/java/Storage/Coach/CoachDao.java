package Storage.Coach;

import Domain.UserData.Coach;
import Storage.Dao;
import com.mongodb.client.MongoCollection;

public class CoachDao extends Dao<Coach> implements ICoachDao {

    @Override
    protected MongoCollection<Coach> getCollection() {
        return null;
    }

    @Override
    public Coach update(Coach value) {
        return null;
    }

    @Override
    public Coach tryInsertThenUpdate(Coach coach) {
        return null;
    }
}
