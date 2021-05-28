package Storage.Team.Codecs;

import Domain.UserData.Interfaces.ISwimmer;
import Domain.UserData.Invitation;
import Domain.UserData.Team;
import Storage.Invitation.InvitationDao;
import Storage.Swimmer.SwimmerDao;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class TeamCodec implements Codec<Team> {

    private SwimmerDao swimmerDao;
    private InvitationDao invitationDao;

    public TeamCodec() {
        this.swimmerDao = new SwimmerDao();
        this.invitationDao = new InvitationDao();
    }

    @Override
    public Team decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String name = bsonReader.readString("_id");
        long dateLong = bsonReader.readDateTime("open_date");
        LocalDateTime localDateTime = Instant.ofEpochMilli(dateLong)
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
        String coachId = bsonReader.readString("coach_id");

        ConcurrentHashMap<String, ISwimmer> swimmerHashMap = readSwimmers(bsonReader, decoderContext);

        int sendInvitations = bsonReader.readInt32("send_invitations");

        List<Invitation> invitationList = readInvitations(bsonReader, decoderContext);
        bsonReader.readEndDocument();

        return new Team(name, localDateTime, coachId, swimmerHashMap, sendInvitations, invitationList);
    }

    private ConcurrentHashMap<String, ISwimmer> readSwimmers(BsonReader bsonReader, DecoderContext decoderContext) {
        ConcurrentHashMap<String, ISwimmer> swimmerHashMap = new ConcurrentHashMap<>();
        bsonReader.readName("swimmers");
        bsonReader.readStartArray();
        while(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String swimmerId = bsonReader.readString();
            ISwimmer swimmer = this.swimmerDao.find(swimmerId);
            swimmerHashMap.put(swimmerId, swimmer);
        }
        bsonReader.readEndArray();
        return  swimmerHashMap;
    }

    private List<Invitation> readInvitations(BsonReader bsonReader, DecoderContext decoderContext) {
        List<Invitation> invitationList = new LinkedList<>();
        bsonReader.readName("invitations");
        bsonReader.readStartArray();
        while(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String invitationId = bsonReader.readString();
            Invitation teamInvitation = this.invitationDao.find(invitationId);
            invitationList.add(teamInvitation);
        }
        bsonReader.readEndArray();
        return invitationList;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Team team, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", team.getName());
        ZonedDateTime zonedDateTime = team.getOpenDate().atZone(ZoneId.of("UTC"));
        bsonWriter.writeDateTime("open_date", zonedDateTime.toInstant().toEpochMilli());
        bsonWriter.writeString("coach_id", team.getCoachId());
        bsonWriter.writeStartArray("swimmers");
        for(String swimmerId: team.getSwimmers().keySet()) {
            bsonWriter.writeString(swimmerId);
        }
        bsonWriter.writeEndArray();
        bsonWriter.writeInt32("send_invitations", team.getSendInvitations());
        bsonWriter.writeStartArray("invitations");
        for(Invitation invitation: team.getInvitations()) {
            bsonWriter.writeString(invitation.getId());
            // when team can delete invitation make this and make sure the swimmer and team invitation always the same
            //this.invitationDao.tryInsertThenUpdate(invitation);
        }
        bsonWriter.writeEndArray();

        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Team> getEncoderClass() {
        return Team.class;
    }
}
