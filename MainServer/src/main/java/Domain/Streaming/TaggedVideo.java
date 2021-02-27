package Domain.Streaming;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.SwimmingSkeletonGraph.SwimmingSkeleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class TaggedVideo {

    private List<ISwimmingSkeleton> tags;

    public TaggedVideo() {
        this.tags = new LinkedList<>();
    }

    public TaggedVideo(List<ISwimmingSkeleton> tags) {
        this.tags = tags;
    }

    /**
     * Create a Tagged video from a json
     * @param json - the json of the tagged video
     * @precondition the json is a valid json object
     */
    // TODO - need o remove and fix the tests about it
    public TaggedVideo(String json) {
        this.tags = new LinkedList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<LinkedList<LinkedList<Double>>>(){}.getType();
        List<List<Double>> list = gson.fromJson(json, listType);
        for (List<Double> frame: list) {
            //TODO remove NEW!!!
            ISwimmingSkeleton skeleton = new SwimmingSkeleton(frame);
            tags.add(skeleton);
        }
    }

    // TODO - add tests
    public boolean addTag(ISwimmingSkeleton tag) {
        return this.tags.add(tag);
    }

    public List<ISwimmingSkeleton> getTags() {
        return this.tags;
    }

    public void setTags(List<ISwimmingSkeleton> tags) {
        this.tags = tags;
    }
}
