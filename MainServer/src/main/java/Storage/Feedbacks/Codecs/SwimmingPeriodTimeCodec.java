package Storage.Feedbacks.Codecs;

import Domain.PeriodTimeData.IPeriodTime;
import Domain.PeriodTimeData.PeriodTime;
import Domain.PeriodTimeData.SwimmingPeriodTime;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.util.LinkedList;
import java.util.List;

public class SwimmingPeriodTimeCodec implements Codec<SwimmingPeriodTime> {

    @Override
    public SwimmingPeriodTime decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String name = bsonReader.readName();
        List<IPeriodTime> rights = decodeTimes(bsonReader, decoderContext);
        name = bsonReader.readName();
        List<IPeriodTime> lefts = decodeTimes(bsonReader, decoderContext);
        bsonReader.readEndDocument();
        return new SwimmingPeriodTime(rights, lefts);
    }

    private List<IPeriodTime> decodeTimes(BsonReader bsonReader, DecoderContext decoderContext) {
        List<IPeriodTime> output = new LinkedList<>();
        bsonReader.readStartArray();
        while (bsonReader.readBsonType() != BsonType.END_OF_DOCUMENT) {
            bsonReader.readStartDocument();
            int start = bsonReader.readInt32("start");
            int end = bsonReader.readInt32("end");
            output.add(new PeriodTime(start, end));
            bsonReader.readEndDocument();
        }
        bsonReader.readEndArray();
        return output;
    }

    @Override
    public void encode(BsonWriter bsonWriter, SwimmingPeriodTime periodTime, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeName("right");
        encodeTimes(bsonWriter, periodTime.getRightTimes(), encoderContext);
        bsonWriter.writeName("left");
        encodeTimes(bsonWriter, periodTime.getLeftTimes(), encoderContext);
        bsonWriter.writeEndDocument();
    }

    private void encodeTimes(BsonWriter bsonWriter, List<IPeriodTime> times, EncoderContext context) {
        bsonWriter.writeStartArray();
        for(IPeriodTime p: times) {
            bsonWriter.writeStartDocument();
            bsonWriter.writeInt32("start", p.getStart());
            bsonWriter.writeInt32("end", p.getEnd());
            bsonWriter.writeEndDocument();
        }
        bsonWriter.writeEndArray();
    }

    @Override
    public Class<SwimmingPeriodTime> getEncoderClass() {
        return SwimmingPeriodTime.class;
    }
}
