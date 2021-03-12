package Storage.SwimmingErrors;

import Domain.SwimmingData.Drawing.Draw;
import Domain.SwimmingData.Errors.LeftElbowError;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class LeftElbowErrorCodec implements Codec<LeftElbowError> {

    @Override
    public LeftElbowError decode(BsonReader bsonReader, DecoderContext decoderContext) {
        //bsonReader.readStartDocument();
        //String tag = bsonReader.readString("tag");
        double angle = bsonReader.readDouble("angle");
        boolean inside = bsonReader.readBoolean("side");
        //bsonReader.readEndDocument();
        return new LeftElbowError(new Draw(), angle, inside);
    }

    @Override
    public void encode(BsonWriter bsonWriter, LeftElbowError leftElbowError, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        // must right tag first
        bsonWriter.writeString("tag", leftElbowError.getTag());
        bsonWriter.writeDouble("angle", leftElbowError.getAngle());
        bsonWriter.writeBoolean("side", leftElbowError.getIndise());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<LeftElbowError> getEncoderClass() {
        return LeftElbowError.class;
    }
}
