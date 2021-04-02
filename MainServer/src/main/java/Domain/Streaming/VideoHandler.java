package Domain.Streaming;
import Domain.Errors.Interfaces.SwimmingError;
import Domain.SwimmingSkeletonsData.*;
import Domain.Drawing.IDraw;
import org.opencv.core.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/***
 * Class Note: must create for each thread a new instance
 * otherwise synchronize will slow the system
 */
public class VideoHandler implements IVideoHandler {

    private IDraw drawer;

    /**
     * constructor
     */
    public VideoHandler(IDraw drawer) {
        this.drawer = drawer;
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        nu.pattern.OpenCV.loadShared();
        nu.pattern.OpenCV.loadLocally(); // Use in case loadShared() doesn't work
    }

    /**
     * The function insert a byte list to the given path. The path includes his name
     * @param video - the data
     * @param path - the path
     * @return if saved true
     * @precondition there is no file in the given path - if there is it will override it
     */
    public synchronized boolean saveFramesBytes(byte[] video, String path) {
        if(video == null || path == null || path.isEmpty() || video.length == 0) {
            return false;
        }
        try {
            FileOutputStream out = new FileOutputStream(path);
            out.write(video);
            out.close();
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * The function delete a video from a given path
     * @param path - the path for the video
     * @return if deleted true
     * @postcondition the file is deleted
     */
    public synchronized boolean deleteVideo(String path) {
        if(path==null || path.isEmpty()) {
            return false;
        }
        File file = new File(path);
        if(file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * The function read a video from a given path
     * @param path - the path of the video
     * @return the data of the video
     */
    public synchronized byte[] readVideo(String path) {
        if(path == null || path.isEmpty()) {
            return null;
        }
        File file =  new File(path);
        if(file.exists()) {
            try {
                byte[] output = new byte[(int)file.length()];
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(output);
                fileInputStream.close();
                return output;
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }


    /**
     * The function return a list for frames from a given path video
     * @param desPath - the video path
     * @return list of frames
     */
    public List<Mat> getFrames(String desPath) {
        if(desPath == null) {
            return null;
        }
        File file = new File(desPath);
        if(file.exists()) {
            List<Mat> output = new LinkedList<>();
            VideoCapture capture = new VideoCapture(file.getAbsolutePath());
            if(capture.isOpened()) {
                Mat frame = new Mat();
                while (capture.read(frame)) {
                    output.add(frame);
                    frame = new Mat();
                }
            }
            capture.release();
            return output;
        }
        return null;
    }

    /**
     * The function return a list of frames by a given video
     * @param video - the data
     * @param desPath - the destination path to insert the video frames
     * @return the list of frames
     */
    @Override
    public boolean createAndGetFrames(byte[] video, String desPath) {
        if(video == null || video.length == 0 || desPath == null || desPath.isEmpty()) {
            return false;
        }
        return saveFramesBytes(video, desPath);
    }

    /**
     * The function return the List of frames as List<byte>
     * @param frames - the original video
     * @return the frames as List<byte[]>
     */
    public List<byte[]> getFramesBytes(List<Mat> frames) {
        if(frames == null || frames.isEmpty()) {
            return null;
        }
        List<byte[]> output = new LinkedList<>();
        for (Mat mat: frames) {
            byte[] mat_bytes = new byte[(int) (mat.total() * mat.channels())];
            mat.get(0, 0, mat_bytes);
            output.add(mat_bytes);
        }
        return output;
    }


    /**
     * The function drawBefore a visual comment on the frame
     * @param frame the current frame
     * @param visualComment the visual comment
     * @return the new frame
     */
    private void drawVisualComment(Mat frame, Object visualComment) {
        //TODO
    }

    /**
     * The function insert a frame list to a given path
     * @param path - path and the name of the file
     * @param frames - the list of frames
     * @return the file saved
     */
    public File saveFrames(String path, List<Mat> frames) {
        if(path == null || path.isEmpty() || frames == null || frames.isEmpty()) {
            return  null;
        }
        File output = null;
        Size size = new Size(frames.get(0).width(), frames.get(0).height());
        File file = new File(path);
        VideoWriter writer = new VideoWriter(file.getAbsolutePath(), -1, 29, size,true);
        if(writer.isOpened()) {
            for (Mat frame: frames) {
                writer.write(frame);
            }
            output = file;
        }
        writer.release();
        return output;
    }

    /**
     * The function generate a feedback video frames
     * @param frames - the video data
     * @param skeletons - the tags of the swimmer
     * @param errors - the list of errors
     * @param visualComments - the list of visual comments
     * @return new byte video
     * @precondition all lists must be the insert of the same video frames
     */
    private List<Mat> generatedFeedbackVideo(List<Mat> frames, List<ISwimmingSkeleton> skeletons,
                                             Map<Integer, List<SwimmingError>> errors,
                                             List<Object> visualComments) {
        for(int i=0; i<frames.size(); i++) {
            Mat frame = frames.get(i);
            if (skeletons != null && !skeletons.isEmpty()) {
                ISwimmingSkeleton skeleton = skeletons.get(i);
                // draw before skeleton error
                if (errors != null && !errors.isEmpty() && errors.containsKey(i)) {
                    List<SwimmingError> frameErrors = errors.get(i);
                    for (SwimmingError error : frameErrors) {
                        error.drawBefore(frame, skeleton);
                    }
                }
                drawer.drawSwimmer(frame, skeleton);
                // draw after skeleton error
                if (errors != null && !errors.isEmpty() && errors.containsKey(i)) {
                    List<SwimmingError> frameErrors = errors.get(i);
                    for (SwimmingError error : frameErrors) {
                        error.drawAfter(frame, skeleton);
                    }
                }
            }
            if(visualComments!= null && ! visualComments.isEmpty()) {
                //TODO
                Object visualComment = visualComments.get(i);
                drawVisualComment(frame, visualComment);
            }
            drawer.drawLogo(frame);
            frames.set(i, frame);
        }
        return frames;
    }

    /**
     * The function get the feedback video file
     * @param feedbackPath - the feedback path to save the new frames
     * @param videoPath - the video path data
     * @param points - the tags of the swimmer
     * @param errors - the list of errors
     * @param visualComments - the list of visual comments
     * @return the feedback file
     * @precondition all lists must be the insert of the same video frames
     * @postcondition insert the newest feedback generated in the des path
     */
    public File getFeedBackVideoFile(String feedbackPath, String videoPath, List<ISwimmingSkeleton> points,
                                     Map<Integer, List<SwimmingError>> errors,
                                     List<Object> visualComments) {
        if(feedbackPath == null || videoPath == null || videoPath.isEmpty() || feedbackPath.isEmpty()) {
            return null;
        }
        List<Mat> frames = getFrames(videoPath);
        frames = generatedFeedbackVideo(frames, points, errors, visualComments);
        return saveFrames(feedbackPath, frames);
    }

}
