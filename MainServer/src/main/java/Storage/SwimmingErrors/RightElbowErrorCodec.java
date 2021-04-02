package Storage.SwimmingErrors;

import Domain.Drawing.Draw;
import Domain.Errors.RightElbowError;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class RightElbowErrorCodec implements Codec<RightElbowError> {


    @Override
    public RightElbowError decode(BsonReader bsonReader, DecoderContext decoderContext) {
        //bsonReader.readStartDocument();
        //String tag = bsonReader.readString("tag");
        double angle = bsonReader.readDouble("angle");
        double minAngle = bsonReader.readDouble("minAngle");
        double maxAngle = bsonReader.readDouble("maxAngle");
        boolean inside = bsonReader.readBoolean("side");
        //bsonReader.readEndDocument();
        return new RightElbowError(new Draw(), maxAngle, minAngle, angle, inside);
    }

    @Override
    public void encode(BsonWriter bsonWriter, RightElbowError rightElbowError, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("tag", rightElbowError.getTag());
        bsonWriter.writeDouble("angle", rightElbowError.getAngle());
        bsonWriter.writeDouble("minAngle", rightElbowError.getMinAngle());
        bsonWriter.writeDouble("maxAngle", rightElbowError.getMaxAngle());
        bsonWriter.writeBoolean("side", rightElbowError.getIndise());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<RightElbowError> getEncoderClass() {
        return RightElbowError.class;
    }
}