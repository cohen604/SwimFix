package ExernalSystems;

import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import Domain.SwimmingData.SwimmingSkeleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class MLConnectionHandlerProxy implements MLConnectionHandler{
    private MLConnectionHandler mlConnectionHandler = null;

    public MLConnectionHandlerProxy() {
        mlConnectionHandler = new MLConnectionHandlerReal("84.109.116.61", "5050");
    }

    //TODO delete this
    private List<SwimmingSkeleton> buildSkeleton(String json) {
        List<SwimmingSkeleton> output = new LinkedList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<LinkedList<LinkedList<Double>>>(){}.getType();
        List<List<Double>> list = gson.fromJson(json, listType);
        for (List<Double> frame: list) {
            SwimmingSkeleton skeleton = new SwimmingSkeleton(frame);
            output.add(skeleton);
        }
        return output;
    }

    @Override
    public TaggedVideo getSkeletons(Video video) {
        if (mlConnectionHandler != null) {
            return mlConnectionHandler.getSkeletons(video);
            /*
            TODO for testing when eyal not avilable sample2
            try {
                String path = "C:\\Users\\avrah\\Desktop\\sample2_skeletons.txt";
                File file = new File(path);
                List<String> lines = readAllLines(file.toPath());
                String json = lines.stream().reduce("",(acc, cur)->acc+cur);
                List<SwimmingSkeleton> skeletons = buildSkeleton(json);
                return new TaggedVideo(skeletons);
            } catch (Exception e ) {
                e.printStackTrace();
                return null;
            }*/

        }
        return null;
    }
}
