package Storage;

import org.springframework.beans.factory.annotation.Autowired;

public class Daos {

    private static Daos storageRepos;
    private VideoDao videoDao;

    private Daos() {
        this.videoDao = new VideoDao();
    }

    public static Daos getInstance() {
        if(storageRepos == null) {
            storageRepos = new Daos();
        }
        return storageRepos;
    }

    public VideoDao getVideoDao() {
        return this.videoDao;
    }
}
