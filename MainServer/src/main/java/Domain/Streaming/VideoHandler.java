package Domain.Streaming;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

//TODO this class need be a synchronize methods ?
public class VideoHandler {

    private String path;
    private String desPath;
    private String desType;
    /**
     * constractor
     * @param type - the type of the video we working with, need to be in the format ".type"
     */
    public VideoHandler(String type) {
        //TODO generate here a uniqe string path that recognize the user so we can load later
        this.path = "clientVideos/videoTmp"+type;
        this.desType = ".mp4";
        this.desPath = "clientVideos/feedbackVideoTmp"+this.desType;
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        nu.pattern.OpenCV.loadShared();
        nu.pattern.OpenCV.loadLocally(); // Use in case loadShared() doesn't work
    }

    /**
     * The function save a byte list to the given path. The path includes his name
     * @param video - the data
     * @param path - the path
     * @return if saved true
     * @precondition there is no file in the given path - if there is it will override it
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
     * @postcondition the file is deleted
     */
    public synchronized boolean deleteVideo(String path) {
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
     * @precondition there is no video path saved as "./videoTmp" in the current folder,
     *               video frames size larger then (150 * 50)
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
            //TODO need to talk about this, maybe we need the original frames
            //deleteVideo(this.path);
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

    /**
     * The function draw swimming tag into the frame
     * @param frame the current frame
     * @param swimmingTag the image tag to print
     * @return the new frame
     */
    private Mat drawSwimmer(Mat frame, SwimmingTag swimmingTag) {
        //TODO
        //Imgproc.rectangle(frame,obj.getLeftBottom(),obj.getRightTop(),new Scalar(255,0,0),1);
        return frame;
    }

    /**
     * The function draw error on the frame
     * @param frame the current frame
     * @param error the error to draw
     * @return the new frame
     */
    private Mat drawErrors(Mat frame, SwimmingError error) {
        //TODO
        return frame;
    }

    /**
     * The function draw a visual comment on the frame
     * @param frame the current frame
     * @param visualComment the visual comment
     * @return the new frame
     */
    private Mat drawVisualComment(Mat frame, Object visualComment) {
        //TODO
        return frame;
    }

    /**
     * The function draw the logo on the frame
     * @param frame the current frame
     * @return the new frame with the logo
     */
    private Mat drawLogo(Mat frame) {
        String logo = "SwimFix";
        double x = frame.width() - 130;
        double y = 30;
        org.opencv.core.Point point = new org.opencv.core.Point(x, y);
        int scale = 1;
        Scalar color = new Scalar(0,0,0);
        int thickens = 2;
        Imgproc.putText(frame, logo, point, Core.FONT_HERSHEY_SIMPLEX, scale, color ,thickens);
        return frame;
    }

    /**
     * The function save a frame list to a given path
     * @param path - path and the name of the file
     * @param frames - the list of frames
     * @return the file saved
     * @postcondition videoWriter is working
     */
    public File saveVideo(String path, List<Mat> frames) {
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
     * @param dots - the tags of the swimmer
     * @param errors - the list of errors
     * @param visualComments - the list of visual comments
     * @return new byte video
     * @precondition all lists must be the save of the same video frames
     */
    private List<Mat> generatedFeedbackVideo(List<Mat> frames, List<SwimmingTag> dots, List<SwimmingError> errors,
                                             List<Object> visualComments) {
        for(int i=0; i<frames.size(); i++) {
            Mat frame = frames.get(i);
            if(dots!=null && !dots.isEmpty()) {
                SwimmingTag swimmingTag = dots.get(i);
                frame = drawSwimmer(frame, swimmingTag);
            }
            if(errors!=null && !errors.isEmpty()) {
                SwimmingError swimmingError = errors.get(i);
                frame = drawErrors(frame, swimmingError);
            }
            if(visualComments!= null && ! visualComments.isEmpty()) {
                //TODO
                Object visualComment = visualComments.get(i);
                frame = drawVisualComment(frame, visualComment);
            }
            drawLogo(frame);
            frames.set(i, frame);
        }
        return frames;
    }

    /**
     * The function get the feedback video file
     * @param frames - the video data
     * @param dots - the tags of the swimmer
     * @param errors - the list of errors
     * @param visualComments - the list of visual comments
     * @return the feedback file
     * @precondition all lists must be the save of the same video frames
     * @postcondition save the newest feedback generated in the des path
     */
    public File getFeedBackVideoFile(List<Mat> frames, List<SwimmingTag> dots, List<SwimmingError> errors,
                                     List<Object> visualComments) {
        frames = generatedFeedbackVideo(frames, dots, errors, visualComments);
        return saveVideo(this.desPath, frames);
    }

    /***
     * Getters
     */
    public String getPath() {
        return this.path;
    }

    public String getDesPath() {
        return this.desPath;
    }

    public String getDesType() {
        return this.desType;
    }
}
