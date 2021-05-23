package Storage.Invitation;

import Domain.UserData.Invitation;
import Storage.Dao;
import Storage.DbContext;
import Storage.Invitation.Codecs.InvitationCodec;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class InvitationDao extends Dao<Invitation> implements IInvitationDao {

    @Override
    protected MongoCollection<Invitation> getCollection() {
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new InvitationCodec()), //here we define the codec
                        getDefaultCodecRegistry());

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry).build();

        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DbContext.DATABASE_NAME);
        return mongoDatabase.getCollection(DbContext.COLLECTION_NAME_INVITATIONS, Invitation.class);
    }

    @Override
    public Invitation update(Invitation value) {
        return null;
    }

    @Override
    public Invitation tryInsertThenUpdate(Invitation invitation) {
        return defaultTryInsertThenUpdate(invitation, invitation.getId());
    }
}
