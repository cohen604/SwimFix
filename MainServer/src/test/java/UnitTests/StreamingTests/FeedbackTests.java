package UnitTests.StreamingTests;

import DTO.ConvertedVideoDTO;
import DTO.FeedbackVideoDTO;
import DTO.FeedbackVideoStreamer;
import Domain.Streaming.FeedbackVideo;
import Domain.Streaming.TaggedVideo;
import Domain.Streaming.Video;
import Domain.Streaming.VideoHandler;
import Domain.SwimmingData.Draw;
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

    private void setUpFeedback() {
        String path = VIDEO_FOLDER + "/sample2.mp4";
        String type = ".mp4";
        Video video = new Video(new VideoHandler(new Draw()), path, type);
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
        this.feedbackVideo = new FeedbackVideo(video,taggedVideo, errorsMap);
    }

    private void setUFeedbackVideoNotExits() {
        String path = null;
        String type = null;
        Video video = new Video(new VideoHandler(new Draw()), path, type);
        //TODO
        TaggedVideo taggedVideo = null;
        Map<Integer,List<SwimmingError>> errorsMap = new HashMap<>();
        this.feedbackVideo = new FeedbackVideo(video,taggedVideo, errorsMap);
    }

    private void setUpGeneratedFeedback() {
        setUpFeedback();
        this.feedbackVideo.generateFeedbackDTO();
    }

    public void testGenerateFeedbackDTO() {
        setUpFeedback();
        FeedbackVideoDTO feedbackVideoDTO = this.feedbackVideo.generateFeedbackDTO();
        assertNotNull(feedbackVideoDTO);
        assertEquals(this.feedbackVideo.getPath(), feedbackVideoDTO.getPath());
    }

    public void testGenerateFeedbackDTOVideoNotExists() {
        setUFeedbackVideoNotExits();
        FeedbackVideoDTO feedbackVideoDTO = this.feedbackVideo.generateFeedbackDTO();
        assertNull(feedbackVideoDTO);
    }

    public void testGenerateFeedbackDTOTwice() {
        setUpGeneratedFeedback();
        FeedbackVideoDTO feedbackVideoDTO = this.feedbackVideo.generateFeedbackDTO();
        assertNotNull(feedbackVideoDTO);
        assertEquals(this.feedbackVideo.getPath(), feedbackVideoDTO.getPath());
    }

    public void testGenerateFeedbackDTOVideoUpdated() {
        setUpGeneratedFeedback();
        this.feedbackVideo.updateVideo();
        FeedbackVideoDTO feedbackVideoDTO = this.feedbackVideo.generateFeedbackDTO();
        assertNotNull(feedbackVideoDTO);
        assertEquals(this.feedbackVideo.getPath(), feedbackVideoDTO.getPath());
        assertFalse(this.feedbackVideo.isFeedbackUpdated());
    }

    public void testGeneratedFeedbackDTOVideoHandlerStub() {
        //TODO
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
