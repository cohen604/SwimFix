package Domain.Streaming;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.SwimmingSkeletonsData.SwimmingSkeletonGraph.SwimmingSkeleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

//TODO make for this class interface
public class TaggedVideo {

    private List<ISwimmingSkeleton> _tags;
    private String _mlSkeletonsPath;
    private String _skeletonsPath;

    public TaggedVideo(String mlSkeletonsPath, String skeletonsPath) {
        _tags = new LinkedList<>();
        _mlSkeletonsPath = mlSkeletonsPath;
        _skeletonsPath = skeletonsPath;
    }

    public TaggedVideo(List<ISwimmingSkeleton> tags, String mlSkeletonsPath, String skeletonsPath) {
        _tags = tags;
        _mlSkeletonsPath = mlSkeletonsPath;
        _skeletonsPath = skeletonsPath;
    }

    /**
     * Create a Tagged video from a json
     * @param json - the json of the tagged video
     * @precondition the json is a valid json object
     */
    // TODO - need o remove and fix the tests about it
    public TaggedVideo(String json) {
        _tags = new LinkedList<>();
        Gson gson = new Gson();
        Type listType = new TypeToken<LinkedList<LinkedList<Double>>>(){}.getType();
        List<List<Double>> list = gson.fromJson(json, listType);
        for (List<Double> frame: list) {
            //TODO remove NEW!!!
            ISwimmingSkeleton skeleton = new SwimmingSkeleton(frame);
            _tags.add(skeleton);
        }
    }

    // TODO - add tests
    public boolean addTag(ISwimmingSkeleton tag) {
        return _tags.add(tag);
    }


    public void setTags(List<ISwimmingSkeleton> tags) {
        _tags = tags;
    }


    public List<ISwimmingSkeleton> getTags() {
        return _tags;
    }

    public String getMlSkeletonsPath() {
        return _mlSkeletonsPath;
    }

    public String getskeletonsPath() {
        return _skeletonsPath;
    }
}
