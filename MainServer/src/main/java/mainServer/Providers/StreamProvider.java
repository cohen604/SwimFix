package mainServer.Providers;

import DTO.FeedbackVideoDTO;
import java.io.File;
import java.nio.file.Files;


public class StreamProvider implements IStreamProvider {

    @Override
    public FeedbackVideoDTO streamFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            try {
                byte[] data = Files.readAllBytes(file.toPath());
                return new FeedbackVideoDTO(file.getPath(), data);
            } catch (Exception e) {
                //TODO return here error
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }



}
