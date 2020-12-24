package Domain.SwimmingData;

import javafx.util.Pair;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.List;

public abstract class Draw {

    /**
     * The function draw a circle
     * @param frame
     * @param skeletonPoint
     * @param radius
     */
    public void drawCircle(Mat frame, SkeletonPoint skeletonPoint, int radius) {
        Point point = new Point(skeletonPoint.getX(),skeletonPoint.getY());
        Scalar color = new Scalar(0,255,0);
        int thickness = 3;
        Imgproc.circle(frame, point, radius, color, thickness);
    }

    /**
     * The function draw linew
     * @param frame
     * @param a
     * @param b
     */
    public void drawLine(Mat frame, SkeletonPoint a, SkeletonPoint b) {
        Point pointA = new Point(a.getX(),a.getY());
        Point pointB = new Point(b.getX(),b.getY());
        Scalar color = new Scalar(255,0,0);
        int thickness = 2;
        Imgproc.line(frame, pointA, pointB, color, thickness);
    }

    /**
     * The function draw a line
     * @param frame
     * @param s1
     * @param s2
     * @param r
     * @param g
     * @param b
     * @param a
     * @param thickness
     */
    public void drawLine(Mat frame, SkeletonPoint s1, SkeletonPoint s2, double r, double g, double b,
                         double a, int thickness) {
        Point pointA = new Point(s1.getX(),s1.getY());
        Point pointB = new Point(s2.getX(),s2.getY());
        Scalar color = new Scalar(b, g, r, a);
        Imgproc.line(frame, pointA, pointB, color, thickness);
    }

    /**
     * The function draw swimming tag into the frame
     * @param frame the current frame
     * @param skeleton the image tag to print
     * @return the new frame
     */
    public void drawSwimmer(Mat frame, SwimmingSkeleton skeleton) {
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
}
