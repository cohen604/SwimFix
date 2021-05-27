package Storage.Invitation.Codecs;

import Domain.UserData.Invitation;
import Domain.UserData.SwimmerInvitation;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class SwimmerInvitationCodec implements Codec<SwimmerInvitation> {

    private InvitationCodec invitationCodec;

    public SwimmerInvitationCodec() {
        invitationCodec = new InvitationCodec();
    }

    @Override
    public SwimmerInvitation decode(BsonReader bsonReader, DecoderContext decoderContext) {
        Invitation invitation =invitationCodec.decode(bsonReader,decoderContext);
        return new SwimmerInvitation(invitation);
    }

    @Override
    public void encode(BsonWriter bsonWriter, SwimmerInvitation invitation, EncoderContext encoderContext) {
        invitationCodec.encode(bsonWriter, invitation, encoderContext);
    }

    @Override
    public Class<SwimmerInvitation> getEncoderClass() {
        return SwimmerInvitation.class;
    }
}
