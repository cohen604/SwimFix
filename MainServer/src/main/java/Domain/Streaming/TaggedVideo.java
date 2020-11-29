package Domain.Streaming;

import java.util.List;

public class TaggedVideo {

    private List<SwimmingTag> tags;

    public TaggedVideo(List<SwimmingTag> tags) {
        this.tags = tags;
    }

    public List<SwimmingTag> getTags() {
        return this.tags;
    }
}
