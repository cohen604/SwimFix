package Domain.Streaming;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import static org.opencv.highgui.HighGui.destroyAllWindows;
import static org.opencv.videoio.Videoio.CAP_PROP_POS_MSEC;

//TODO optimize this class design
//TODO this class need be a synchronize methods ?
//TODO if needed to be static Class ?
public class VideoHandler {

    String path = "videoTmp.mov";
    String desPath = "videoTmp.avi";

    public VideoHandler() {
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        nu.pattern.OpenCV.loadShared();
        nu.pattern.OpenCV.loadLocally(); // Use in case loadShared() doesn't work
    }

    /**
     * The function save a video to the given path. The path includes his name
     * @param video - the data
     * @param path - the path
     * @return if saved true
     */
    public synchronized boolean saveVideo(byte[] video, String path) {
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
     */
    public synchronized boolean deleteVideo(String path) {
        //TODO
        return false;
    }

    /**
     * The function read a video from a given path
     * @param path - the path of the video
     * @return the data of the video
     */
    public synchronized byte[] readVideo(String path) {
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
     * The function return a list of frames by a given video
     * @param video - the data
     * @return the list of frames
     * @precondition there is no video path saved as "./videoTmp" in the current folder
     * @postcondition videoCapture is working
     */
    public List<Mat> getFrames(byte[] video) {
        List<Mat> output = new LinkedList<>();
        if(saveVideo(video, this.path)) {
            // this.capture = new VideoCapture(0); capture the camera
            File file = new File(this.path);
            VideoCapture capture = new VideoCapture(file.getAbsolutePath());
            if(capture.isOpened()) {
                Mat frame = new Mat();
                while (capture.read(frame)) {
                    output.add(frame);
                    frame = new Mat();
                }
            }
            capture.release();
            deleteVideo(this.path);
        }
        return output;
    }

    /**
     * The function return the List of frames as List<byte>
     * @param frames - the original video
     * @return the frames as List<byte[]>
     */
    public List<byte[]> getFramesBytes(List<Mat> frames) {
        List<byte[]> output = new LinkedList<>();
        for (Mat mat: frames) {
            byte[] mat_bytes = new byte[(int) (mat.total() * mat.channels())];
            mat.get(0, 0, mat_bytes);
            output.add(mat_bytes);
        }
        return output;
    }

    private List<Mat> drawSwimmer(List<Mat> frames, List<SwimmingTag> dots) {
        //TODO
        //Imgproc.rectangle(frame,obj.getLeftBottom(),obj.getRightTop(),new Scalar(255,0,0),1);
        return frames;
    }

    private List<Mat> drawErrors(List<Mat> frames, List<SwimmingError> errors) {
        //TODO
        return frames;
    }

    private List<Mat> drawVisualComment(List<Mat> frames, List<Object> visualComments) {
        //TODO
        return frames;
    }

    /**
     * The function save a frame list to a given path
     * @param path - path and the name of the file
     * @param frames - the list of frames
     * @return if saved true
     * @postcondition videoWriter is working
     */
    private boolean saveVideo(String path, List<Mat> frames) {
        boolean output = false;
        Size size = new Size(frames.get(0).width(), frames.get(0).height());
        File file = new File("feedback_"+this.path);
        VideoWriter writer = new VideoWriter(file.getAbsolutePath(), -1, 29, size,true);
        if(writer.isOpened()) {
            for (Mat frame: frames) {
                writer.write(frame);
            }

            output = true;
        }
        writer.release();
        return output;
    }

    /**
     * The function generate a feedback video
     * @param frames - the video data
     * @param dots - the tags of the swimmer
     * @param errors - the list of errors
     * @param visualComments - the list of visual comments
     * @return new byte video
     * @precondition all lists must be the save of the same video frames
     */
    public byte[] generatedFeedBack(List<Mat> frames, List<SwimmingTag> dots, List<SwimmingError> errors,
                                    List<Object> visualComments) {
        byte[] output = null;
        //TODO optimize this to an iterative function rather then looping functions
        frames = drawSwimmer(frames, dots);
        frames = drawErrors(frames, errors);
        frames = drawVisualComment(frames, visualComments);
        if(!frames.isEmpty() && saveVideo(this.path, frames)) {
            output = readVideo(this.path);
            if(output!=null) {
                deleteVideo(this.path);
            }
        }
        return output;
    }
}
