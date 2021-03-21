package Storage.User;

import Domain.UserData.Researcher;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

public class ResearcherCodec implements Codec<Researcher> {

    private final CodecRegistry _codecRegistry;

    public ResearcherCodec() {
        _codecRegistry = null;
    }

    public ResearcherCodec(CodecRegistry codecRegistry) {
        _codecRegistry = codecRegistry;
    }

    @Override
    public Researcher decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String tag = bsonReader.readString("tag");
        bsonReader.readEndDocument();
        return new Researcher(tag);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Researcher researcher, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("tag", researcher.getTag());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Researcher> getEncoderClass() {
        return Researcher.class;
    }
}
