package Storage;

import Domain.Streaming.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface VideoRepo extends MongoRepository<Video, String> {

    @Query("{'id': ?0}")
    Optional<Video> findbyID(String id);

}
