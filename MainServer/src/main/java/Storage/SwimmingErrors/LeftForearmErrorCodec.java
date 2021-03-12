package Storage.SwimmingErrors;

import Domain.SwimmingData.Drawing.Draw;
import Domain.SwimmingData.Errors.LeftElbowError;
import Domain.SwimmingData.Errors.LeftForearmError;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class LeftForearmErrorCodec implements Codec<LeftForearmError> {


    @Override
    public LeftForearmError decode(BsonReader bsonReader, DecoderContext decoderContext) {
        //bsonReader.readStartDocument();
        //String tag = bsonReader.readString("tag");
        double angle = bsonReader.readDouble("angle");
        boolean inside = bsonReader.readBoolean("side");
        //bsonReader.readEndDocument();
        return new LeftForearmError(new Draw(), angle, inside);
    }

    @Override
    public void encode(BsonWriter bsonWriter, LeftForearmError leftForearmError, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("tag", leftForearmError.getTag());
        bsonWriter.writeDouble("angle", leftForearmError.getAngle());
        bsonWriter.writeBoolean("side", leftForearmError.getInside());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<LeftForearmError> getEncoderClass() {
        return LeftForearmError.class;
    }
}
