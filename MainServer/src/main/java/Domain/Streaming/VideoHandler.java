package Domain.Streaming;
import javafx.util.Pair;
import org.opencv.core.*;
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

    /**
     * constractor
     */
    public VideoHandler() {
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
    public List<Mat> getFrames(byte[] video, String desPath) {
        if(video == null || video.length == 0 || desPath == null || desPath.isEmpty()) {
            return null;
        }
        if(saveFramesBytes(video, desPath)) {
            return getFrames(desPath);
        }
        return null;
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
     * The function draw a circle
     * @param frame
     * @param skeletonPoint
     * @param radius
     */
    private void drawCircle(Mat frame, SkeletonPoint skeletonPoint, int radius) {
        Point point = new Point(skeletonPoint.getX(),skeletonPoint.getY());
        Scalar color = new Scalar(0,255,0);
        int thickness = 3;
        Imgproc.circle(frame, point, radius, color, thickness);
    }

    private void drawLine(Mat frame, SkeletonPoint a, SkeletonPoint b) {
        Point pointA = new Point(a.getX(),a.getY());
        Point pointB = new Point(b.getX(),b.getY());
        Scalar color = new Scalar(255,0,0);
        int thickness = 2;
        Imgproc.line(frame, pointA, pointB, color, thickness);
    }

    /**
     * The function draw swimming tag into the frame
     * @param frame the current frame
     * @param skeleton the image tag to print
     * @return the new frame
     */
    private void drawSwimmer(Mat frame, SwimmingSkeleton skeleton) {
        // points
        int radius = 2;
        List<SkeletonPoint> points = skeleton.getPoints();
        for (SkeletonPoint point: points) {
            drawCircle(frame, point, radius);
        }
        List<Pair<SkeletonPoint , SkeletonPoint>> lines = skeleton.getLines();
        for(Pair<SkeletonPoint , SkeletonPoint> line: lines) {
            drawLine(frame, line.getKey(), line.getValue());

        }
    }

    /**
     * The function draw error on the frame
     * @param frame the current frame
     * @param error the error to draw
     * @return the new frame
     */
    private void drawErrors(Mat frame, SwimmingError error) {
        //TODO
    }

    /**
     * The function draw a visual comment on the frame
     * @param frame the current frame
     * @param visualComment the visual comment
     * @return the new frame
     */
    private void drawVisualComment(Mat frame, Object visualComment) {
        //TODO
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
     * @param dots - the tags of the swimmer
     * @param errors - the list of errors
     * @param visualComments - the list of visual comments
     * @return new byte video
     * @precondition all lists must be the insert of the same video frames
     */
    private List<Mat> generatedFeedbackVideo(List<Mat> frames, List<SwimmingSkeleton> dots, List<SwimmingError> errors,
                                             List<Object> visualComments) {
        for(int i=0; i<frames.size(); i++) {
            Mat frame = frames.get(i);
            if(dots!=null && !dots.isEmpty()) {
                SwimmingSkeleton swimmingSkeleton = dots.get(i);
                drawSwimmer(frame, swimmingSkeleton);
            }
            if(errors!=null && !errors.isEmpty()) {
                SwimmingError swimmingError = errors.get(i);
                drawErrors(frame, swimmingError);
            }
            if(visualComments!= null && ! visualComments.isEmpty()) {
                //TODO
                Object visualComment = visualComments.get(i);
                drawVisualComment(frame, visualComment);
            }
            //TODO check if we print the logo in the end
            drawLogo(frame);
            frames.set(i, frame);
        }
        return frames;
    }

    /**
     * The function get the feedback video file
     * @param desPath - the destination path to insert the feedback file into
     * @param frames - the video data
     * @param dots - the tags of the swimmer
     * @param errors - the list of errors
     * @param visualComments - the list of visual comments
     * @return the feedback file
     * @precondition all lists must be the insert of the same video frames
     * @postcondition insert the newest feedback generated in the des path
     */
    public File getFeedBackVideoFile(String desPath, List<Mat> frames, List<SwimmingSkeleton> dots, List<SwimmingError> errors,
                                     List<Object> visualComments) {
        if(frames == null) {
            return null;
        }
        frames = generatedFeedbackVideo(frames, dots, errors, visualComments);
        return saveFrames(desPath, frames);
    }

}
