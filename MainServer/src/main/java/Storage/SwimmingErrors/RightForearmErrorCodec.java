package Storage.SwimmingErrors;

import Domain.Drawing.Draw;
import Domain.Errors.RightForearmError;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class RightForearmErrorCodec implements Codec<RightForearmError> {


    @Override
    public RightForearmError decode(BsonReader bsonReader, DecoderContext decoderContext) {
        //bsonReader.readStartDocument();
        //String tag = bsonReader.readString("tag");
        double angle = bsonReader.readDouble("angle");
        double angleMax = bsonReader.readDouble("angleMax");
        double angleMin = bsonReader.readDouble("angleMin");
        boolean inside = bsonReader.readBoolean("side");
        //bsonReader.readEndDocument();
        return new RightForearmError(new Draw(), angle, angleMax, angleMin, inside);
    }

    @Override
    public void encode(BsonWriter bsonWriter, RightForearmError rightForearmError, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        // must write tag first
        bsonWriter.writeString("tag", rightForearmError.getTag());
        bsonWriter.writeDouble("angle", rightForearmError.getAngle());
        bsonWriter.writeDouble("angleMax", rightForearmError.getMaxAngle());
        bsonWriter.writeDouble("angleMin", rightForearmError.getMinAngle());
        bsonWriter.writeBoolean("side", rightForearmError.getInside());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<RightForearmError> getEncoderClass() {
        return RightForearmError.class;
    }
}