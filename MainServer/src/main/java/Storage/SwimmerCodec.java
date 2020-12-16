package Storage;

import Domain.Streaming.Video;
import Domain.Swimmer;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class SwimmerCodec implements Codec<Swimmer> {

    @Override
    public Swimmer decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String uid = bsonReader.readString("_id");
        String email = bsonReader.readString("email");
        String name = bsonReader.readString("name");
        bsonReader.readEndDocument();
        return new Swimmer(uid, email, name);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Swimmer swimmer, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", swimmer.getUid());
        bsonWriter.writeString("email", swimmer.getEmail());
        bsonWriter.writeString("name", swimmer.getName());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Swimmer> getEncoderClass() {
        return Swimmer.class;
    }
}
