package Storage.User.Providers;

import Domain.UserData.Swimmer;

import Storage.User.SwimmerCodec;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class SwimmerCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if (aClass == Swimmer.class) {
            return (Codec<T>) new SwimmerCodec(codecRegistry);
        }
        return null;
    }

}
