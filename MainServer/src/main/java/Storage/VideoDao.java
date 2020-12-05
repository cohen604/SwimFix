package Storage;

import Domain.Streaming.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoDao implements Dao<Video>{

    @Autowired
    private VideoRepo videoRepo;

    @Override
    public boolean insert(Video value) {
        try {
            System.out.println("try to save video in db");
            this.videoRepo.save(value);
            System.out.println("saved video in db");
            return true;
        } catch (Exception e ){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
