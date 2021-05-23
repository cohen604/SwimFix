package Storage.Team;

import Domain.UserData.Team;
import Storage.Dao;
import Storage.DbContext;
import Storage.Team.Codecs.TeamCodec;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

import static com.mongodb.internal.async.client.AsyncMongoClients.getDefaultCodecRegistry;

public class TeamDao extends Dao<Team> implements ITeamDao {

    @Override
    protected MongoCollection<Team> getCollection() {
        CodecRegistry codecRegistryTeam =
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromCodecs(new TeamCodec()), //here we define the codec
                        getDefaultCodecRegistry());

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(codecRegistryTeam).build();

        // here we define the connection
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DbContext.DATABASE_NAME);
        return mongoDatabase.getCollection(DbContext.COLLECTION_NAME_TEAMS, Team.class);
    }

    @Override
    public Team update(Team value) {
        return null;
    }
}
