package ExernalSystems;

import Domain.Streaming.IVideo;
import Domain.Streaming.TaggedVideo;
import java.io.File;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class MLConnectionHandlerProxy implements MLConnectionHandler{
    private MLConnectionHandler mlConnectionHandler = null;

    public MLConnectionHandlerProxy() {
        mlConnectionHandler = new MLConnectionHandlerReal("84.109.116.61", "5050");
    }

    @Override
    public TaggedVideo getSkeletons(IVideo video) {
        if (mlConnectionHandler != null) {
//            return mlConnectionHandler.getSkeletons(video);
            //TODO for testing when eyal not avilable sample2
            try {
                String path = "./src/test/java/TestingVideos/sample2_skeletons.txt";
                File file = new File(path);
                List<String> lines = readAllLines(file.toPath());
                String json = lines.stream().reduce("",(acc, cur)->acc+cur);
                return new TaggedVideo(json);
            } catch (Exception e ) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
