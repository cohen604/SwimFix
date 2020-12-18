package Storage.User;

import Domain.State;
import Domain.Streaming.Video;
import Domain.User;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UserCodec implements Codec<User> {
    @Override
    public User decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String id = bsonReader.readString("_id");
        String email = bsonReader.readString("email");
        String name = bsonReader.readString("name");
        List<String> keys = new LinkedList<>();
        bsonReader.readName("states");
        bsonReader.readStartArray();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String state = bsonReader.readString();
            System.out.println(state);
            keys.add(state);
        }
        bsonReader.readEndArray();
        bsonReader.readEndDocument();
        return new User(id, email, name, keys);
    }

    @Override
    public void encode(BsonWriter bsonWriter, User user, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", user.getUid());
        bsonWriter.writeString("email", user.getEmail());
        bsonWriter.writeString("name", user.getName());
        bsonWriter.writeName("states"); // = key
        bsonWriter.writeStartArray();
        Set<String> states = user.getStateKeys();
        if(states != null) {
            for(String key: states) {
                bsonWriter.writeString(key);
            }
        }
        bsonWriter.writeEndArray();
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<User> getEncoderClass() {
        return User.class;
    }
}
