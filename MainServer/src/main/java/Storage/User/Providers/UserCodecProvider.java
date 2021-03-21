package Storage.User.Providers;

import Domain.UserData.Admin;
import Storage.User.AdminCodec;
import Storage.User.UserCodec;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class UserCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> aClass, CodecRegistry codecRegistry) {
        if (aClass == Admin.class) {
            return (Codec<T>) new UserCodec(codecRegistry);
        }
        return null;
    }

}
