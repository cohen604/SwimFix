package UnitTests.StreamingTests;

import DTO.ConvertedVideoDTO;
import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import Domain.SwimmingData.SwimmingError;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.readAllLines;

public class FeedbackTests  extends TestCase {

    final private String VIDEO_FOLDER = "./src/test/java/TestingVideos";
    private List<File> deleteList;
    private FeedbackVideo feedbackVideo;

    @Before
    public void setUp() {
        try {
            deleteList = new LinkedList<>();
        } catch (Exception e ){
            System.out.println(e.getMessage());
            fail();
        }
    }

    @After
    public void tearDown() {
        for(File file: deleteList) {
            file.delete();
        }
    }

    private void setUpVideo() {
        String path = VIDEO_FOLDER + "/sample2.mov";
        String type = ".mov";
        Video video = new Video(path, type);
        //TODO
        TaggedVideo taggedVideo = null;
        try {
            String taggedFile = VIDEO_FOLDER + "/sample2_skeletons.txt";
            File file = new File(path);
            List<String> lines = readAllLines(file.toPath());
            String json = lines.stream().reduce("",(acc, cur)->acc+cur);
            taggedVideo = new TaggedVideo(json);
        } catch (Exception e ) {
            e.printStackTrace();
            fail();
        }
        Map<Integer,List<SwimmingError>> errorsMap = new HashMap<>();
        this.feedbackVideo = new FeedbackVideo(video,taggedVideo, errorsMap);
    }

    private void setUpVideoNotExits() {
        String path = null;
        String type = null;
        Video video = new Video(path, type);
        //TODO
        TaggedVideo taggedVideo = null;
        Map<Integer,List<SwimmingError>> errorsMap = new HashMap<>();
        this.feedbackVideo = new FeedbackVideo(video,taggedVideo, errorsMap);
    }

    public void testGenerateFeedbackDTO() {
        //TODO
    }


}
