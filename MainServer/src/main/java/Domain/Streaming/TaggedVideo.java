package Domain.Streaming;

import java.util.List;

public class TaggedVideo {

    private List<SwimmingSkeleton> tags;

    public TaggedVideo(List<SwimmingSkeleton> tags) {
        this.tags = tags;
    }

    public List<SwimmingSkeleton> getTags() {
        return this.tags;
    }
}
