package Storage.Admin.Codecs;

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
        String email = bsonReader.readString("_id");
        boolean researcherAuthorized = bsonReader.readBoolean("researcher_authorized");
        boolean adminAuthorized = bsonReader.readBoolean("admin_authorized");
        bsonReader.readEndDocument();
        return new Admin(email, researcherAuthorized, adminAuthorized);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Admin admin, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", admin.getEmail());
        bsonWriter.writeBoolean("researcher_authorized", admin.isResearcherAuthorized());
        bsonWriter.writeBoolean("admin_authorized", admin.isAdminAuthorized());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Admin> getEncoderClass() {
        return Admin.class;
    }
}
