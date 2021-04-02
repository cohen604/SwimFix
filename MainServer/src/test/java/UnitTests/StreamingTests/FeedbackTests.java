package UnitTests.StreamingTests;

import DTO.FeedbackVideoStreamer;
import Domain.PeriodTimeData.SwimmingPeriodTime;
import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import Domain.Errors.Interfaces.SwimmingError;
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

    private void setUpFeedback() {
        String path = VIDEO_FOLDER + "/sample2.mp4";
        String type = ".mp4";
        Video video = new Video(path, type);
        //TODO
        TaggedVideo taggedVideo = null;
        try {
            String taggedFile = VIDEO_FOLDER + "/sample2_skeletons.txt";
            File file = new File(taggedFile);
            List<String> lines = readAllLines(file.toPath());
            String json = lines.stream().reduce("",(acc, cur)->acc+cur);
            taggedVideo = new TaggedVideo(json);
        } catch (Exception e ) {
            e.printStackTrace();
            fail();
        }
        Map<Integer,List<SwimmingError>> errorsMap = new HashMap<>();
        SwimmingPeriodTime swimmingPeriodTime = new SwimmingPeriodTime(new LinkedList<>(), new LinkedList<>());
        this.feedbackVideo = new FeedbackVideo(video, taggedVideo, errorsMap, path, swimmingPeriodTime);
    }

    private void setUFeedbackVideoNotExits() {
        String path = null;
        String type = null;
        Video video = new Video(path, type);
        //TODO
        TaggedVideo taggedVideo = null;
        Map<Integer,List<SwimmingError>> errorsMap = new HashMap<>();
        SwimmingPeriodTime swimmingPeriodTime = new SwimmingPeriodTime(new LinkedList<>(), new LinkedList<>());
        this.feedbackVideo = new FeedbackVideo(video,taggedVideo, errorsMap, path, swimmingPeriodTime);
    }

    private void setUpGeneratedFeedback() {
        setUpFeedback();
    }

    public void testGeneratedFeedbackStreamer() {
        setUpFeedback();
        List<String> detectors = new LinkedList<>();
        detectors.add("detector0");
        FeedbackVideoStreamer streamer = this.feedbackVideo.generateFeedbackStreamer(detectors);
        assertNotNull(streamer);
        assertEquals(this.feedbackVideo.getPath(), streamer.getPath());
        assertEquals(detectors, streamer.getDetectors());
    }

    public void testGeneratedFeedbackStreamerVideoNotExists() {
        setUFeedbackVideoNotExits();
        List<String> detectors = new LinkedList<>();
        detectors.add("detector0");
        FeedbackVideoStreamer streamer = this.feedbackVideo.generateFeedbackStreamer(detectors);
        assertNull(streamer);
    }

    public void testGeneratedFeedbackStreamerTwice() {
        setUpGeneratedFeedback();
        List<String> detectors = new LinkedList<>();
        detectors.add("detector0");
        FeedbackVideoStreamer streamer = this.feedbackVideo.generateFeedbackStreamer(detectors);
        assertNotNull(streamer);
        assertEquals(this.feedbackVideo.getPath(), streamer.getPath());
        assertEquals(detectors, streamer.getDetectors());
    }

    public void testGeneratedFeedbackStreamerVideoUpdated() {
        setUpGeneratedFeedback();
        this.feedbackVideo.updateVideo();
        List<String> detectors = new LinkedList<>();
        detectors.add("detector0");
        FeedbackVideoStreamer streamer = this.feedbackVideo.generateFeedbackStreamer(detectors);
        assertNotNull(streamer);
        assertEquals(this.feedbackVideo.getPath(), streamer.getPath());
        assertEquals(detectors, streamer.getDetectors());
        assertFalse(this.feedbackVideo.isFeedbackUpdated());
    }

    public void testGeneratedFeedbackStreamerDetectorsNull() {
        setUpFeedback();
        FeedbackVideoStreamer streamer = this.feedbackVideo.generateFeedbackStreamer(null);
        assertNull(streamer);
    }

}
