package Storage.Invitation;

import Domain.UserData.Invitation;
import Storage.Dao;
import com.mongodb.client.MongoCollection;

public class InvitiationDao extends Dao<Invitation> implements IInvitiationDao {

    @Override
    protected MongoCollection<Invitation> getCollection() {
        return null;
    }

    @Override
    public Invitation update(Invitation value) {
        return null;
    }
}
