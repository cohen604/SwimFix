package Storage.User.Providers;

import Domain.UserData.Coach;
import Domain.UserData.Swimmer;
import Storage.User.CoachCodec;
import Storage.User.SwimmerCodec;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class CoachCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if (aClass == Coach.class) {
            return (Codec<T>) new CoachCodec(codecRegistry);
        }
        return null;
    }

}
