package Domain.Streaming;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

public class VideoHandler {

    VideoCapture capture;
    VideoWriter writer;

    /**
     * The function save a video to the given path. The path includes his name
     * @param video - the data
     * @param path - the path
     * @return if saved true
     */
    public boolean saveVideo(byte[] video, String path) {
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
    public boolean deleteVideo(String path) {
        //TODO
        return false;
    }

    /**
     * The function read a video from a given path
     * @param path - the path of the video
     * @return the data of the video
     */
    public byte[] readVideo(String path) {
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
                return null;
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
    private List<Mat> getFrames(byte[] video) {
        List<Mat> output = new LinkedList<>();
        String videoPath = "videoTmp";
        if(saveVideo(video, videoPath)) {
            this.capture = new VideoCapture(videoPath);
            if(this.capture.isOpened()) {
                Mat frame = new Mat();
                while (this.capture.read(frame)) {
                    output.add(frame);
                    frame = new Mat();
                }
            }
            deleteVideo(videoPath);
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
        int fourcc = VideoWriter.fourcc('x', '2','6','4');
        Size size = new Size(frames.get(0).width(), frames.get(0).height());
        this.writer = new VideoWriter(path, fourcc, 30, size,true);
        if(this.writer.isOpened()) {
            for (Mat frame: frames) {
                this.writer.write(frame);
            }
            return true;
        }
        return false;
    }

    /**
     * The function generate a feedback video
     * @param video - the video data
     * @param dots - the tags of the swimmer
     * @param errors - the list of errors
     * @param visualComments - the list of visual comments
     * @return new byte video
     * @precondition all lists must be the save of the same video frames
     */
    public byte[] generatedFeedBack(byte[] video, List<SwimmingTag> dots, List<SwimmingError> errors,
                                    List<Object> visualComments) {
        byte[] output = null;
        //TODO optimize this to an iterative function rather then looping functions
        List<Mat> frames = getFrames(video);
        frames = drawSwimmer(frames, dots);
        frames = drawErrors(frames, errors);
        frames = drawVisualComment(frames, visualComments);
        String path = "videoTmp"; //TODO check if saved as mp4
        if(!frames.isEmpty() && saveVideo(path, frames)) {
            output = readVideo(path);
            if(output!=null) {
                deleteVideo(path);
            }
        }
        if(this.capture!=null) {
            this.capture.release();
        }
        if(this.writer!=null) {
            this.writer.release();
        }
        return output;
    }
}
