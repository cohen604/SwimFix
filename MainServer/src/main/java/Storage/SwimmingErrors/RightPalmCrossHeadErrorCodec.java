package Storage.SwimmingErrors;

import Domain.Drawing.Draw;
import Domain.Errors.RightPalmCrossHeadError;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class RightPalmCrossHeadErrorCodec implements Codec<RightPalmCrossHeadError> {


    @Override
    public RightPalmCrossHeadError decode(BsonReader bsonReader, DecoderContext decoderContext) {
        //bsonReader.readStartDocument();
        //String tag = bsonReader.readString("tag");
        boolean inside = bsonReader.readBoolean("side");
        //bsonReader.readEndDocument();
        return new RightPalmCrossHeadError(new Draw(), inside);
    }

    @Override
    public void encode(BsonWriter bsonWriter, RightPalmCrossHeadError rightPalmCrossHeadError,
                       EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        // must write tag first
        bsonWriter.writeString("tag", rightPalmCrossHeadError.getTag());
        bsonWriter.writeBoolean("side", rightPalmCrossHeadError.getInside());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<RightPalmCrossHeadError> getEncoderClass() {
        return RightPalmCrossHeadError.class;
    }
}