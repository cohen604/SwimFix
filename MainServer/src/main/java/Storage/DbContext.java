package Storage;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DbContext {

    public static String DATABASE_NAME = "swimfix";
    public static final String COLLECTION_NAME_USERS = "user";
    public static final String COLLECTION_NAME_SWIMMERS = "swimmer";
    public static final String COLLECTION_NAME_FEEDBACKS = "feedback";
    public static final String COLLECTION_NAME_COACHES = "coach";
    public static final String COLLECTION_NAME_GROUPS = "group";
    public static final String COLLECTION_NAME_RESEARCHERS = "researcher";
    public static final String COLLECTION_NAME_ADMINS = "admin";

    public DbContext(String dbName) {
        DATABASE_NAME = dbName;
    }

    public void initialize() {
        List<String> collections = new LinkedList<>();
        collections.add(COLLECTION_NAME_USERS);
        collections.add(COLLECTION_NAME_SWIMMERS);
        collections.add(COLLECTION_NAME_FEEDBACKS);
        collections.add(COLLECTION_NAME_COACHES);
        collections.add(COLLECTION_NAME_GROUPS);
        collections.add(COLLECTION_NAME_RESEARCHERS);
        collections.add(COLLECTION_NAME_ADMINS);
        initialize(collections);
    }

    private void initialize(Collection<String> collections) {

        MongoClientSettings settings = MongoClientSettings.builder().build();
        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE_NAME);

        for (String collection : collections) {
            if(!mongoDatabase.listCollectionNames().into(new ArrayList<String>()).contains(collection)) {
                mongoDatabase.createCollection(collection);
                System.out.println("Created Db Collection " + collection);
            }
        }
        mongoClient.close();

    }

}

