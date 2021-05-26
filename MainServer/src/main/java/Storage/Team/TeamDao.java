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
import com.mongodb.client.model.Filters;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

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
        return defaultUpdate(value, value.getName());
    }

    @Override
    public Team tryInsertThenUpdate(Team team) {
        return defaultTryInsertThenUpdate(team, team.getName());
    }

    @Override
    public boolean isTeamExists(String teamId) {
        try {
            MongoCollection<Team> collection = getCollection();
            Bson query  = Filters.and(
                    Filters.eq("_id", teamId));
            return collection.countDocuments(query) != 0;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
