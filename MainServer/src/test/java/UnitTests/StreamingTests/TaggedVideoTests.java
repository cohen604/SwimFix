package UnitTests.StreamingTests;

import Domain.Streaming.TaggedVideo;
import junit.framework.TestCase;

import java.io.File;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class TaggedVideoTests extends TestCase {

    final private String VIDEO_FOLDER = "./src/test/java/TestingVideos";


    public void testCreatedTaggedVideoFromJson() {
        try {
            String taggedFile = VIDEO_FOLDER + "/sample2_skeletons.txt";
            File file = new File(taggedFile);
            List<String> lines = readAllLines(file.toPath());
            String json = lines.stream().reduce("",(acc, cur)->acc+cur);
            TaggedVideo taggedVideo = new TaggedVideo(json);
            assertNotNull(taggedVideo);
            assertEquals(83, taggedVideo.getTags().size());
        } catch (Exception e ) {
            e.printStackTrace();
            fail();
        }
    }
}
