package Storage.SwimmingErrors;

import Domain.SwimmingData.Drawing.Draw;
import Domain.SwimmingData.Errors.LeftElbowError;
import Domain.SwimmingData.Errors.RightElbowError;
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
        boolean inside = bsonReader.readBoolean("side");
        //bsonReader.readEndDocument();
        return new RightElbowError(new Draw(), angle, inside);
    }

    @Override
    public void encode(BsonWriter bsonWriter, RightElbowError rightElbowError, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("tag", rightElbowError.getTag());
        bsonWriter.writeDouble("angle", rightElbowError.getAngle());
        bsonWriter.writeBoolean("side", rightElbowError.getIndise());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<RightElbowError> getEncoderClass() {
        return RightElbowError.class;
    }
}