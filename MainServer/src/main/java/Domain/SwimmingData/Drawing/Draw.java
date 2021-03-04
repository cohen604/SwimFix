package Domain.SwimmingData.Drawing;

import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.Points.IPointUtils;
import javafx.util.Pair;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.List;

public class Draw implements IDraw {

    @Override
    public void drawCircle(Mat frame, IPoint skeletonPoint, int radius) {
        Point point = new Point(skeletonPoint.getX(),skeletonPoint.getY());
        Scalar color = new Scalar(0,255,0);
        int thickness = 3;
        Imgproc.circle(frame, point, radius, color, thickness);
    }

    @Override
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

    @Override
    public void drawLine(Mat frame, IPoint a, IPoint b) {
        Point pointA = new Point(a.getX(),a.getY());
        Point pointB = new Point(b.getX(),b.getY());
        Scalar color = new Scalar(255,0,0);
        int thickness = 2;
        Imgproc.line(frame, pointA, pointB, color, thickness);
    }

    @Override
    public void drawLine(Mat frame, IPoint s1, IPoint s2, double r, double g, double b,
                         double a, int thickness) {
        Point pointA = new Point(s1.getX(),s1.getY());
        Point pointB = new Point(s2.getX(),s2.getY());
        Scalar color = new Scalar(b, g, r, a);
        Imgproc.line(frame, pointA, pointB, color, thickness);
    }

    @Override
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

    /**
     * The function draw the logo on the frame
     * @param frame the current frame
     * @return the new frame with the logo
     */
    @Override
    public Mat drawLogo(Mat frame) {
        String logo = "SwimFix";
        double x = frame.width() - 130;
        double y = 30;
        return drawMessage(frame, logo, x, y, 2);
    }

    /**
     * The function draw the logo on the frame
     * @param frame the current frame
     * @return the new frame with the logo
     */
    @Override
    public Mat drawMessage(Mat frame, String message, double x, double y, int thickness) {
        org.opencv.core.Point point = new org.opencv.core.Point(x, y);
        int scale = 1;
        Scalar color = new Scalar(0,0,0);
        Imgproc.putText(frame, message, point, Core.FONT_HERSHEY_SIMPLEX, scale, color , thickness);
        return frame;
    }

    //https://math.stackexchange.com/questions/1314006/drawing-an-arrow
    public Mat drawArrow(Mat frame, IPoint a, IPoint b) {
        double red = 6;
        double green = 217;
        double blue = 27;
        drawLine(frame, a, b, red, green, blue, 0, 2);
        double size = 10 / IPointUtils.calcDistance(a, b);
        double teta = 30 * Math.PI / 180;
        double x3 = b.getX() + size * ((a.getX() - b.getX()) * Math.cos(teta)
                + (a.getY() - b.getY()) * Math.sin(teta));
        double y3 = b.getY() + size * ((a.getY() - b.getY()) * Math.cos(teta)
                - (a.getX() - b.getX()) * Math.sin(teta));
        double x4 = b.getX() + size * ((a.getX() - b.getX()) * Math.cos(teta)
                - (a.getY() - b.getY()) * Math.sin(teta));
        double y4 = b.getY() + size * ((a.getY() - b.getY()) * Math.cos(teta)
                + (a.getX() - b.getX()) * Math.sin(teta));
        drawLine(frame, b, x3, y3, red, green, blue);
        drawLine(frame, b, x4, y4, red, green, blue);
        return frame;
    }

    private void drawLine(Mat frame, IPoint a, double x, double y, double r, double g, double b) {
        Point pointA = new Point(a.getX(),a.getY());
        Point pointB = new Point(x, y);
        Scalar color = new Scalar(r,g,b);
        int thickness = 2;
        Imgproc.line(frame, pointA, pointB, color, thickness);
    }



}
