package Storage.Feedbacks.Codecs.SwimmingErrors;

import Domain.Errors.*;
import Domain.Errors.Interfaces.SwimmingError;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class SwimmingErrorCodec implements Codec<SwimmingError> {

    private final CodecRegistry _codecRegistry;

    public SwimmingErrorCodec(CodecRegistry codecRegistry) {
        _codecRegistry = codecRegistry;
    }

    @Override
    public SwimmingError decode(BsonReader bsonReader, DecoderContext decoderContext) {
        SwimmingError swimmingError = null;
        bsonReader.readStartDocument();
        String tag = bsonReader.readString("tag");
        if(tag.equals("Right Elbow Error")) {
            Codec<RightElbowError> codec = _codecRegistry.get(RightElbowError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        else if(tag.equals("Left Elbow Error")) {
            Codec<LeftElbowError> codec = _codecRegistry.get(LeftElbowError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        else if(tag.equals("Right Forearm Error")) {
            Codec<RightForearmError> codec = _codecRegistry.get(RightForearmError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        else if(tag.equals("Left Forearm Error")) {
            Codec<LeftForearmError> codec = _codecRegistry.get(LeftForearmError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        else if(tag.equals("Right Palm Error")) {
            Codec<RightPalmCrossHeadError> codec = _codecRegistry.get(RightPalmCrossHeadError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        else if(tag.equals("Left Palm Error")) {
            Codec<LeftPalmCrossHeadError> codec = _codecRegistry.get(LeftPalmCrossHeadError.class);
            swimmingError = decoderContext.decodeWithChildContext(codec, bsonReader);
        }
        bsonReader.readEndDocument();
        return swimmingError;
    }

    @Override
    public void encode(BsonWriter bsonWriter, SwimmingError swimmingError, EncoderContext encoderContext) {
        if(swimmingError.getTag().equals("Right Elbow Error")) {
            Codec<RightElbowError> dateCodec = _codecRegistry.get(RightElbowError.class);
            encoderContext.encodeWithChildContext(dateCodec, bsonWriter, (RightElbowError)swimmingError);
        }
        else if(swimmingError.getTag().equals("Left Elbow Error")) {
            Codec<LeftElbowError> dateCodec = _codecRegistry.get(LeftElbowError.class);
            encoderContext.encodeWithChildContext(dateCodec, bsonWriter, (LeftElbowError)swimmingError);
        }
        else if(swimmingError.getTag().equals("Right Forearm Error")) {
            Codec<RightForearmError> dateCodec = _codecRegistry.get(RightForearmError.class);
            encoderContext.encodeWithChildContext(dateCodec, bsonWriter, (RightForearmError)swimmingError);
        }
        else if(swimmingError.getTag().equals("Left Forearm Error")) {
            Codec<LeftForearmError> dateCodec = _codecRegistry.get(LeftForearmError.class);
            encoderContext.encodeWithChildContext(dateCodec, bsonWriter, (LeftForearmError)swimmingError);
        }
        else if(swimmingError.getTag().equals("Right Palm Error")) {
            Codec<RightPalmCrossHeadError> dateCodec = _codecRegistry.get(RightPalmCrossHeadError.class);
            encoderContext.encodeWithChildContext(dateCodec, bsonWriter, (RightPalmCrossHeadError)swimmingError);
        }
        else if(swimmingError.getTag().equals("Left Palm Error")) {
            Codec<LeftPalmCrossHeadError> dateCodec = _codecRegistry.get(LeftPalmCrossHeadError.class);
            encoderContext.encodeWithChildContext(dateCodec, bsonWriter, (LeftPalmCrossHeadError)swimmingError);
        }
    }

    @Override
    public Class<SwimmingError> getEncoderClass() {
        return SwimmingError.class;
    }
}
