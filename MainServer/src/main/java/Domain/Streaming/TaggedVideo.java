package Domain.Streaming;

import Domain.SwimmingData.SwimmingSkeleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class TaggedVideo {

    private List<SwimmingSkeleton> tags;

    public TaggedVideo(List<SwimmingSkeleton> tags) {
        this.tags = tags;
    }

    /**
     * Create a Tagged video from a json
     * @param json - the json of the tagged video
     * @precondition the json is a valid json object
     */
    public TaggedVideo(String json) {
        this.tags = new LinkedList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<LinkedList<LinkedList<Double>>>(){}.getType();
        List<List<Double>> list = gson.fromJson(json, listType);
        for (List<Double> frame: list) {
            SwimmingSkeleton skeleton = new SwimmingSkeleton(frame);
            tags.add(skeleton);
        }
    }

    public List<SwimmingSkeleton> getTags() {
        return this.tags;
    }
}
