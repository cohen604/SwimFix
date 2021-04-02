package Storage.SwimmingErrors;

import Domain.Drawing.Draw;
import Domain.Errors.LeftPalmCrossHeadError;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class LeftPalmCrossHeadErrorCodec implements Codec<LeftPalmCrossHeadError> {

    @Override
    public LeftPalmCrossHeadError decode(BsonReader bsonReader, DecoderContext decoderContext) {
        //bsonReader.readStartDocument();
        //String tag = bsonReader.readString("tag");
        boolean inside = bsonReader.readBoolean("side");
        //bsonReader.readEndDocument();
        return new LeftPalmCrossHeadError(new Draw(), inside);
    }

    @Override
    public void encode(BsonWriter bsonWriter, LeftPalmCrossHeadError leftPalmCrossHeadError, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        // must write tag first
        bsonWriter.writeString("tag", leftPalmCrossHeadError.getTag());
        bsonWriter.writeBoolean("side", leftPalmCrossHeadError.getInside());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<LeftPalmCrossHeadError> getEncoderClass() {
        return LeftPalmCrossHeadError.class;
    }
}