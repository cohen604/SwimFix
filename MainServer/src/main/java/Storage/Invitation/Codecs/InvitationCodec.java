package Storage.Invitation.Codecs;

import Domain.UserData.Invitation;
import Domain.UserData.InvitationStatus;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class InvitationCodec implements Codec<Invitation> {

    @Override
    public Invitation decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String id = bsonReader.readString("_id");
        String teamId = bsonReader.readString("team_id");
        String swimmerId = bsonReader.readString("swimmer_id");
        long dateLong = bsonReader.readDateTime("creation_time");
        LocalDateTime localDateTime = Instant.ofEpochMilli(dateLong)
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
        String status = bsonReader.readString("status");
        InvitationStatus invitationStatus = InvitationStatus.valueOf(status);
        bsonReader.readEndDocument();
        return new Invitation(id, teamId, swimmerId, localDateTime, invitationStatus);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Invitation invitation, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", invitation.getId());
        bsonWriter.writeString("team_id", invitation.getTeamId());
        ZonedDateTime zonedDateTime = invitation.getCreationTime().atZone(ZoneId.of("UTC"));
        bsonWriter.writeDateTime("creation_time", zonedDateTime.toInstant().toEpochMilli());
        bsonWriter.writeString("status", invitation.getStatus().toString());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Invitation> getEncoderClass() {
        return Invitation.class;
    }

}
