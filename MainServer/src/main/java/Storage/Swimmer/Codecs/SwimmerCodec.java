package Storage.Swimmer.Codecs;

import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.IFeedbackVideo;
import Domain.UserData.Invitation;
import Domain.UserData.Swimmer;
import Domain.UserData.SwimmerInvitation;
import Storage.Feedbacks.FeedbacksDao;
import Storage.Feedbacks.IFeedbackDao;
import Storage.Invitation.InvitationDao;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SwimmerCodec implements Codec<Swimmer> {

    private IFeedbackDao _feedbackDao;
    private InvitationDao _invitationDao;

    public SwimmerCodec() {
        _feedbackDao = new FeedbacksDao();
        _invitationDao = new InvitationDao();
    }

    @Override
    public Swimmer decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String email = bsonReader.readString("_id");
        List<IFeedbackVideo> feedbacks = decodeFeedbacks(bsonReader, decoderContext);
        ConcurrentHashMap<String, SwimmerInvitation> swimmerInvitations = decodePendingInvitations(bsonReader, decoderContext);
        ConcurrentHashMap<String, Invitation> invitations = decodeInvitationHistory(bsonReader, decoderContext);
        String teamId = null;
        if(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            teamId = bsonReader.readString("team_id");
        }
        bsonReader.readEndDocument();
        return new Swimmer(email, feedbacks, teamId, swimmerInvitations, invitations);
    }

    private List<IFeedbackVideo> decodeFeedbacks(BsonReader bsonReader, DecoderContext decoderContext) {
        List<IFeedbackVideo> feedbacks = new LinkedList<>();
        bsonReader.readName("feedbacks");
        bsonReader.readStartArray();
        while(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String id = bsonReader.readString();
            feedbacks.add(_feedbackDao.find(id));
        }
        bsonReader.readEndArray();
        return feedbacks;
    }

    private ConcurrentHashMap<String, SwimmerInvitation> decodePendingInvitations(BsonReader bsonReader, DecoderContext decoderContext) {
        ConcurrentHashMap<String, SwimmerInvitation> swimmerInvitations = new ConcurrentHashMap<>();
        bsonReader.readName("pending_invitations");
        bsonReader.readStartArray();
        while(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String invitationId = bsonReader.readString();
            Invitation invitation = _invitationDao.find(invitationId);
            swimmerInvitations.put(invitationId, new SwimmerInvitation(invitation));
        }
        bsonReader.readEndArray();
        return swimmerInvitations;
    }

    private ConcurrentHashMap<String, Invitation> decodeInvitationHistory(BsonReader bsonReader, DecoderContext decoderContext) {
        ConcurrentHashMap<String, Invitation> invitations = new ConcurrentHashMap<>();
        bsonReader.readName("invitations_history");
        bsonReader.readStartArray();
        while(bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            String invitationId = bsonReader.readString();
            Invitation invitation = _invitationDao.find(invitationId);
            invitations.put(invitationId, invitation);
        }
        bsonReader.readEndArray();
        return invitations;
    }

    @Override
    public void encode(BsonWriter bsonWriter, Swimmer swimmer, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", swimmer.getEmail());
        bsonWriter.writeStartArray("feedbacks");
        for (IFeedbackVideo feedbackVideo: swimmer.getFeedbacks()) {
            bsonWriter.writeString(feedbackVideo.getPath());
            _feedbackDao.tryInsertThenUpdate((FeedbackVideo) feedbackVideo);
        }
        bsonWriter.writeEndArray();

        bsonWriter.writeStartArray("pending_invitations");
        for(SwimmerInvitation swimmerInvitation: swimmer.getPendingInvitations().values()) {
            bsonWriter.writeString(swimmerInvitation.getId());
            _invitationDao.tryInsertThenUpdate(swimmerInvitation);
        }
        bsonWriter.writeEndArray();

        bsonWriter.writeStartArray("invitations_history");
        for(Invitation invitation: swimmer.getInvitationHistory().values()) {
            bsonWriter.writeString(invitation.getId());
            _invitationDao.tryInsertThenUpdate(invitation);
        }
        bsonWriter.writeEndArray();

        if(swimmer.getTeamId() != null) {
            bsonWriter.writeString("team_id", swimmer.getTeamId());
        }

        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Swimmer> getEncoderClass() {
        return Swimmer.class;
    }
}
