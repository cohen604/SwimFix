package Storage;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoDatabaseImpl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class DbContext {

    public static final String DATABASE_NAME = "swimfix";
    public static final String COLLECTION_NAME_USERS = "user";
    public static final String COLLECTION_NAME_SWIMMERS = "swimmer";
    public static final String COLLECTION_NAME_FEEDBACKS = "feedback";
    public static final String COLLECTION_NAME_COACHES = "coach";
    public static final String COLLECTION_NAME_GROUPS = "group";
    public static final String COLLECTION_NAME_RESEARCHERS = "researcher";
    public static final String COLLECTION_NAME_ADMINS = "admin";

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
            try {
                mongoDatabase.createCollection(collection);
                System.out.println("Created Db Collection "+collection);
            } catch (Exception e) {
                System.out.println("Collection already exists - "+collection);
            }
        }
        mongoClient.close();

    }

}

