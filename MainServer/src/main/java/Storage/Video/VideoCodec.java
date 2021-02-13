package Storage.Video;

import Domain.Streaming.*;
import Domain.SwimmingData.Draw;
import Domain.SwimmingData.IDraw;
import mainServer.SwimmingErrorDetectors.FactoryDraw;
import mainServer.SwimmingErrorDetectors.IFactoryDraw;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

public class VideoCodec implements Codec<Video> {

    private IFactoryVideo iFactoryVideo;

    public VideoCodec(IFactoryVideo iFactoryVideo) {
        this.iFactoryVideo = iFactoryVideo;
    }

    @Override
    public Video decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        String path = bsonReader.readString("_id");
        String type = bsonReader.readString("type");
        bsonReader.readEndDocument();
        return new Video(new VideoHandler(new Draw()), path, type);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Video video, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("_id", video.getPath());
        bsonWriter.writeString("type", video.getVideoType());
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Video> getEncoderClass() {
        return Video.class;
    }
}
