package Domain.SwimmingData;

import javafx.util.Pair;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.List;

public class Draw implements IDraw {

    public void drawCircle(Mat frame, IPoint skeletonPoint, int radius) {
        Point point = new Point(skeletonPoint.getX(),skeletonPoint.getY());
        Scalar color = new Scalar(0,255,0);
        int thickness = 3;
        Imgproc.circle(frame, point, radius, color, thickness);
    }

    public void drawElipce(Mat frame, double radius, double angle, double startAngle,
                           double endAngle, IPoint center, double r, double g,
                           double b, double a) {
        Point centerPoint = new Point(center.getX(), center.getY());
        Size size = new Size(radius, radius);
        Scalar scalar = new Scalar(b, g, r, a);
        int thickness = -1;
        Imgproc.ellipse(frame, centerPoint, size, angle, startAngle, endAngle,
                scalar, thickness);
    }

    public void drawLine(Mat frame, IPoint a, IPoint b) {
        Point pointA = new Point(a.getX(),a.getY());
        Point pointB = new Point(b.getX(),b.getY());
        Scalar color = new Scalar(255,0,0);
        int thickness = 2;
        Imgproc.line(frame, pointA, pointB, color, thickness);
    }


    public void drawLine(Mat frame, IPoint s1, IPoint s2, double r, double g, double b,
                         double a, int thickness) {
        Point pointA = new Point(s1.getX(),s1.getY());
        Point pointB = new Point(s2.getX(),s2.getY());
        Scalar color = new Scalar(b, g, r, a);
        Imgproc.line(frame, pointA, pointB, color, thickness);
    }

    public void drawSwimmer(Mat frame, ISwimmingSkeleton skeleton) {
        // points
        int radius = 2;
        List<IPoint> points = skeleton.getPoints();
        for (IPoint point: points) {
            drawCircle(frame, point, radius);
        }
        List<Pair<IPoint , IPoint>> lines = skeleton.getLines();
        for(Pair<IPoint , IPoint> line: lines) {
            drawLine(frame, line.getKey(), line.getValue());
        }
    }

}
