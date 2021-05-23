package Storage.Invitation.Codecs;

import Domain.UserData.Invitation;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class InvitationCodec implements Codec<Invitation> {
    @Override
    public Invitation decode(BsonReader bsonReader, DecoderContext decoderContext) {
        return null;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Invitation invitation, EncoderContext encoderContext) {

    }

    @Override
    public Class<Invitation> getEncoderClass() {
        return Invitation.class;
    }
}
