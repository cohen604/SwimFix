package Storage.User;

import Domain.UserData.Admin;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class AdminCodec implements Codec<Admin> {

    private final CodecRegistry _codecRegistry;

    public AdminCodec () {
        _codecRegistry = null;
    }

    public AdminCodec (CodecRegistry codecRegistry) {
        _codecRegistry = codecRegistry;
    }

    @Override
    public Admin decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String tag = bsonReader.readString("tag");
        bsonReader.readEndDocument();
        return new Admin(tag);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Admin admin, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("tag", admin.getTag());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Admin> getEncoderClass() {
        return Admin.class;
    }
}
