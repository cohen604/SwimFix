package Storage.Feedbacks.Codecs;

import Domain.Streaming.TextualComment;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TextualCommentCodec implements Codec<TextualComment> {

    @Override
    public TextualComment decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        long dateLong = bsonReader.readDateTime("time");
        LocalDateTime localDateTime = Instant.ofEpochMilli(dateLong)
                .atZone(ZoneId.of("UTC"))
                .toLocalDateTime();
        String coachId = bsonReader.readString("coach_id");
        String text = bsonReader.readString("text");
        bsonReader.readEndDocument();
        return new TextualComment(
                localDateTime,
                coachId,
                text
        );
    }

    @Override
    public void encode(BsonWriter bsonWriter, TextualComment textualComment, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        ZonedDateTime zonedDateTime = textualComment.getDate().atZone(ZoneId.of("UTC"));
        bsonWriter.writeDateTime("time", zonedDateTime.toInstant().toEpochMilli());
        bsonWriter.writeString("coach_id", textualComment.getCoachId());
        bsonWriter.writeString("text", textualComment.getText());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<TextualComment> getEncoderClass() {
        return TextualComment.class;
    }
}
