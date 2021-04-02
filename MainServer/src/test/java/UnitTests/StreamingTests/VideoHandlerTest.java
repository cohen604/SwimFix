package UnitTests.StreamingTests;

import Domain.Drawing.Draw;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.Streaming.VideoHandler;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VideoHandlerTest extends TestCase {

    final private String VIDEO_FOLDER = "./src/test/java/TestingVideos";
    final private String WRONG_FOLDER = "./NO/SUCH/FOLDER";
    private VideoHandler videoHandler;
    private File testVideo;
    private List<File> deleteList;
    private String testVideoType;

    @Before
    public void setUp() {
        this.videoHandler = new VideoHandler(new Draw());
        this.testVideo = new File(VIDEO_FOLDER + "/sample.mov");
        this.testVideoType = ".mov";
        if(!testVideo.exists()) {
            fail();
        }
        this.deleteList = new LinkedList<>();

    }

    @After
    public void tearDown() {
        for (File file: deleteList) {
            file.delete();
        }
    }

    public void testSaveVideo() {
        try {
            byte[] bytes = Files.readAllBytes(this.testVideo.toPath());
            String path = VIDEO_FOLDER + "/testSaveVideo.mov";
            assertTrue(this.videoHandler.saveFramesBytes(bytes, path));
            File file = new File(path);
            this.deleteList.add(file);
            assertTrue(file.exists());

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveVideoEmptyBytes() {
        try {
            byte[] bytes = new byte[0];
            String path = VIDEO_FOLDER + "/test.mov";
            assertFalse(this.videoHandler.saveFramesBytes(bytes, path));
            File file = new File(path);
            assertFalse(file.exists());
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveVideoNullPath() {
        try {
            byte[] bytes = new byte[1];
            assertFalse(this.videoHandler.saveFramesBytes(bytes, null));
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveVideoEmptyPath() {
        try{
            byte[] bytes = new byte[1];
            String path = "";
            assertFalse(this.videoHandler.saveFramesBytes(bytes, path));
            File file = new File(path);
            assertFalse(file.exists());
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveVideoWrongPath() {
        try {
            byte[] bytes = new byte[1];
            String path = WRONG_FOLDER + "/test.mov";
            assertFalse(this.videoHandler.saveFramesBytes(bytes, path));
            File file = new File(path);
            assertFalse(file.exists());
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testDeleteVideo() {
        try {
            String path = VIDEO_FOLDER + "/testDeleteMove.mov";
            FileOutputStream out = new FileOutputStream(path);
            out.write(new byte[1]);
            out.close();
            assertTrue(this.videoHandler.deleteVideo(path));
            File file = new File(path);
            assertFalse(file.exists());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testDeleteVideoNullPath() {
        try {
            assertFalse(this.videoHandler.deleteVideo(null));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testDeleteVideoWrongPath() {
        try {
            String path = WRONG_FOLDER + "/test.mov";
            assertFalse(this.videoHandler.deleteVideo(path));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testDeleteVideoEmptyPath() {
        try {
            String path = "";
            assertFalse(this.videoHandler.deleteVideo(path));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testReadBytes() {
        try {
            byte[] bytes = new byte[3];
            bytes[0] = 98;
            bytes[1] = 99;
            String path = VIDEO_FOLDER + "/test.mov";
            FileOutputStream out = new FileOutputStream(path);
            out.write(bytes);
            out.close();
            this.deleteList.add(new File(path));
            // Test started
            byte[] result = this.videoHandler.readVideo(path);
            assertEquals(bytes.length, result.length);
            for(int i=0; i< bytes.length; i++) {
                assertEquals(bytes[i], result[i]);
            }
        } catch (Exception e ){
            e.printStackTrace();
            fail();
        }
    }

    public void testReadBytesNullPath() {
        try {
            byte[] result = this.videoHandler.readVideo(null);
            assertNull(result);
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testReadBytesWrongPath() {
        try {
            String path = WRONG_FOLDER + "/test.mov";
            byte[] result = this.videoHandler.readVideo(path);
            assertNull(result);
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testReadBytesEmptyPath() {
        try {
            byte[] result = this.videoHandler.readVideo("");
            assertNull(result);
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFrames() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            List<Mat> frames = this.videoHandler.getFrames(path);
            assertNotNull(frames);
            assertFalse(frames.isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFramesWrongPath() {
        try {
            String path = WRONG_FOLDER + "/sample.mov";
            List<Mat> frames = this.videoHandler.getFrames(path);
            assertNull(frames);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFramesEmptyPath() {
        try {
            List<Mat> frames = this.videoHandler.getFrames("");
            assertNull(frames);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFramesNullPath() {
        try {
            List<Mat> frames = this.videoHandler.getFrames(null);
            assertNull(frames);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFramesBytes() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            List<Mat> frames = this.videoHandler.getFrames(path);
            List<byte[]> bytes = this.videoHandler.getFramesBytes(frames);
            assertNotNull(bytes);
            assertFalse(bytes.isEmpty());
            assertEquals(frames.size(), bytes.size());
        } catch (Exception e ){
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFramesBytesEmptyList() {
        try {
            List<byte[]> bytes = this.videoHandler.getFramesBytes(new LinkedList<>());
            assertNull(bytes);
        } catch (Exception e ){
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFramesBytesNullList() {
        try {
            List<byte[]> bytes = this.videoHandler.getFramesBytes(null);
            assertNull(bytes);
        } catch (Exception e ){
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFrames() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            List<Mat> frames = this.videoHandler.getFrames(path);
            String des = VIDEO_FOLDER + "/testSaveFrames.mp4";
            File file = this.videoHandler.saveFrames(des, frames);
            assertNotNull(file);
            assertTrue(file.exists());
            this.deleteList.add(file);
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFramesWrongPath() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            List<Mat> frames = this.videoHandler.getFrames(path);
            String des = WRONG_FOLDER + "/testSaveFrames.mp4";
            File file = this.videoHandler.saveFrames(des, frames);
            assertNull(file);
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFramesNullPath() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            List<Mat> frames = this.videoHandler.getFrames(path);
            File file = this.videoHandler.saveFrames(null, frames);
            assertNull(file);
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFramesEmptyPath() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            List<Mat> frames = this.videoHandler.getFrames(path);
            File file = this.videoHandler.saveFrames("", frames);
            assertNull(file);
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFramesNullFrames() {
        try {
            String des = WRONG_FOLDER + "/testSaveFrames.mp4";
            File file = this.videoHandler.saveFrames(des, null);
            assertNull(file);
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFramesEmptyFrames() {
        try {
            String des = WRONG_FOLDER + "/testSaveFrames.mp4";
            File file = this.videoHandler.saveFrames(des, null);
            assertNull(file);
        }catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFramesBytes() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            byte[] bytes = this.videoHandler.readVideo(path);
            String des = VIDEO_FOLDER + "/testSaveFramesBytes.mp4";
            assertTrue(this.videoHandler.saveFramesBytes(bytes, des));
            this.deleteList.add(new File(des));
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFramesBytesWrongPath() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            byte[] bytes = this.videoHandler.readVideo(path);
            String des = WRONG_FOLDER + "/testSaveFramesBytes.mp4";
            assertFalse(this.videoHandler.saveFramesBytes(bytes, des));
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFramesBytesNullPath() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            byte[] bytes = this.videoHandler.readVideo(path);
            assertFalse(this.videoHandler.saveFramesBytes(bytes, null));
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFramesBytesEmptyPath() {
        try {
            String path = VIDEO_FOLDER + "./sample.mov";
            byte[] bytes = this.videoHandler.readVideo(path);
            assertFalse(this.videoHandler.saveFramesBytes(bytes, ""));
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFramesBytesEmptyBytes() {
        try {
            String des = VIDEO_FOLDER + "/testSaveFramesBytes.mp4";
            assertFalse(this.videoHandler.saveFramesBytes(new byte[0], des));
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    public void testSaveFramesBytesNullBytes() {
        try {
            String des = VIDEO_FOLDER + "/testSaveFramesBytes.mp4";
            assertFalse(this.videoHandler.saveFramesBytes(null, des));
        } catch (Exception e){
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFeedBackVideoFile() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            String des = VIDEO_FOLDER + "/testGetFeedbackVideo.mp4";
            List<ISwimmingSkeleton> dots = new LinkedList<>();
            Map<Integer, List<SwimmingError>> errors = new HashMap<>();
            List<Object> visuaComment = new LinkedList<>();
            File file = this.videoHandler.getFeedBackVideoFile(des, path, dots, errors, visuaComment);
            assertNotNull(file);
            assertTrue(file.exists());
            this.deleteList.add(file);
        } catch (Exception e ){
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFeedBackVideoFileWrongPath() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            String des = WRONG_FOLDER + "/testGetFeedbackVideo.mp4";
            List<ISwimmingSkeleton> dots = new LinkedList<>();
            Map<Integer, List<SwimmingError>> errors = new HashMap<>();
            List<Object> visuaComment = new LinkedList<>();
            File file = this.videoHandler.getFeedBackVideoFile(des, path, dots, errors, visuaComment);
            assertNull(file);
        } catch (Exception e ){
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFeedBackVideoFileEmptyPath() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            String des = "";
            List<ISwimmingSkeleton> dots = new LinkedList<>();
            Map<Integer, List<SwimmingError>> errors = new HashMap<>();
            List<Object> visuaComment = new LinkedList<>();
            File file = this.videoHandler.getFeedBackVideoFile(des, path, dots, errors, visuaComment);
            assertNull(file);
        } catch (Exception e ){
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFeedBackVideoFileNullPath() {
        try {
            String path = VIDEO_FOLDER + "/sample.mov";
            String des = null;
            List<ISwimmingSkeleton> dots = new LinkedList<>();
            Map<Integer, List<SwimmingError>> errors = new HashMap<>();
            List<Object> visuaComment = new LinkedList<>();
            File file = this.videoHandler.getFeedBackVideoFile(des, path, dots, errors, visuaComment);
            assertNull(file);
        } catch (Exception e ){
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFeedBackVideoFileEmptyFrames() {
        try {
            String des = VIDEO_FOLDER + "/testGetFeedbackVideo.mp4";
            List<ISwimmingSkeleton> dots = new LinkedList<>();
            Map<Integer, List<SwimmingError>> errors = new HashMap<>();
            List<Object> visuaComment = new LinkedList<>();
            File file = this.videoHandler.getFeedBackVideoFile(des, des, dots, errors, visuaComment);
            assertNull(file);
        } catch (Exception e ){
            e.printStackTrace();
            fail();
        }
    }

    public void testGetFeedBackVideoFileNullFrames() {
        try {
            String des = VIDEO_FOLDER + "/testGetFeedbackVideo.mp4";
            List<ISwimmingSkeleton> dots = new LinkedList<>();
            Map<Integer, List<SwimmingError>> errors = new HashMap<>();
            List<Object> visuaComment = new LinkedList<>();
            File file = this.videoHandler.getFeedBackVideoFile(des, null, dots, errors, visuaComment);
            assertNull(file);
        } catch (Exception e ){
            e.printStackTrace();
            fail();
        }
    }

}
