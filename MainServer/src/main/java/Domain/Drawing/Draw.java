package Domain.Drawing;

import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Points.IPoint;
import Domain.Points.IPointUtils;
import javafx.util.Pair;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.List;

public class Draw implements IDraw {

    @Override
    public Mat drawCircle(Mat frame, IPoint skeletonPoint, int radius) {
//        Point point = new Point(skeletonPoint.getX(),skeletonPoint.getY());
//        Scalar color = new Scalar(0,255,0);
//        int thickness = 3;
//        Imgproc.circle(frame, point, radius, color, thickness);
//        return frame;
        return drawCircle(frame, skeletonPoint, radius, 3, 0, 255, 0);
    }

    @Override
    public Mat drawCircle(Mat frame,
                          IPoint skeletonPoint,
                          int radius,
                          int thickness,
                          double red, double green, double blue) {
        Point point = new Point(skeletonPoint.getX(),skeletonPoint.getY());
        Scalar color = new Scalar(blue, green, red);
        Imgproc.circle(frame, point, radius, color, thickness);
        return frame;
    }

    @Override
    public Mat drawElipce(Mat frame, double radius, double angle, double startAngle,
                           double endAngle, IPoint center, double r, double g,
                           double b, double a) {
        Point centerPoint = new Point(center.getX(), center.getY());
        Size size = new Size(radius, radius);
        Scalar scalar = new Scalar(b, g, r, a);
        int thickness = -1;
        Imgproc.ellipse(frame, centerPoint, size, angle, startAngle, endAngle,
                scalar, thickness);
        return frame;
    }

    @Override
    public Mat drawLine(Mat frame, IPoint a, IPoint b) {
        Point pointA = new Point(a.getX(),a.getY());
        Point pointB = new Point(b.getX(),b.getY());
        Scalar color = new Scalar(255,0,0);
        int thickness = 2;
        Imgproc.line(frame, pointA, pointB, color, thickness);
        return frame;
    }

    @Override
    public Mat drawLine(Mat frame, IPoint s1, IPoint s2, double r, double g, double b,
                         double a, int thickness) {
        Point pointA = new Point(s1.getX(),s1.getY());
        Point pointB = new Point(s2.getX(),s2.getY());
        Scalar color = new Scalar(b, g, r, a);
        Imgproc.line(frame, pointA, pointB, color, thickness);
        return frame;
    }

    @Override
    public Mat drawSwimmer(Mat frame, ISwimmingSkeleton skeleton) {
        // lines
        List<Pair<IPoint , IPoint>> lines = skeleton.getLines();
        for(Pair<IPoint , IPoint> line: lines) {
            drawLine(frame, line.getKey(), line.getValue(), 0, 0, 0, 1, 3);
            drawLine(frame, line.getKey(), line.getValue(), 255, 255, 255, 1, 1);
        }
        // points
        int radius = 2;
        List<IPoint> points = skeleton.getPoints();
        for (IPoint point: points) {
            drawCircle(frame, point, radius + 1, 2, 0, 0, 0);
            drawCircle(frame, point, radius, 2, 0, 0, 255);
        }
        return frame;
    }

    /**
     * The function drawBefore the logo on the frame
     * @param frame the current frame
     * @return the new frame with the logo
     */
    @Override
    public Mat drawLogo(Mat frame) {
        String logo = "Swim Analytics";
        double x = 10;
        double y = 30;
        int font = Core.FONT_HERSHEY_SIMPLEX;
        return drawMessage(frame, logo, x, y, 2, 0.7, font, 0, 0, 0);
    }

    /**
     * The function drawBefore the logo on the frame
     * @param frame the current frame
     * @return the new frame with the logo
     */
    @Override
    public Mat drawMessage(Mat frame, String message, double x, double y, int thickness) {
        int font = Core.FONT_HERSHEY_SIMPLEX;
        return drawMessage(frame, message, x, y, thickness, 1, font, 0, 0, 0);
    }

    //https://math.stackexchange.com/questions/1314006/drawing-an-arrow
    @Override
    public Mat drawArrow(Mat frame, IPoint a, IPoint b) {
        double red = 6;
        double green = 217;
        double blue = 27;
        double size = 8 / IPointUtils.calcDistance(a, b);
        //System.out.println("Arrow Size "+ size);
        if(size > 0.5) {
            size = 0.5;
        }
        double teta = 30 * Math.PI / 180;
        double x3 = b.getX() + size * ((a.getX() - b.getX()) * Math.cos(teta)
                + (a.getY() - b.getY()) * Math.sin(teta));
        double y3 = b.getY() + size * ((a.getY() - b.getY()) * Math.cos(teta)
                - (a.getX() - b.getX()) * Math.sin(teta));
        double x4 = b.getX() + size * ((a.getX() - b.getX()) * Math.cos(teta)
                - (a.getY() - b.getY()) * Math.sin(teta));
        double y4 = b.getY() + size * ((a.getY() - b.getY()) * Math.cos(teta)
                + (a.getX() - b.getX()) * Math.sin(teta));

        drawLine(frame, a, b, 10, 10, 10, 0, 3);
        drawLine(frame, b, x3, y3, 10, 10, 10 , 3);
        drawLine(frame, b, x4, y4, 10, 10, 10, 3);

        drawLine(frame, a, b, red, green, blue, 0, 1);
        drawLine(frame, b, x3, y3, red, green, blue, 1);
        drawLine(frame, b, x4, y4, red, green, blue, 1);
        return frame;
    }

    @Override
    public Mat drawVerticalArrow(Mat frame,
                                 IPoint pointA,
                                 IPoint pointB,
                                 boolean verticalSide) {
        IPoint vec = IPointUtils.getVec(pointA, pointB);
        double rotationAngle = -90;
        if(verticalSide) {
            rotationAngle = 90;
        }
        vec = IPointUtils.pivotVector(vec, rotationAngle);
        vec = IPointUtils.addByScalars(vec, pointB.getX(), pointB.getY());
        return drawArrow(frame, pointB, vec);
    }

    private void drawLine(Mat frame, IPoint a, double x, double y, double r, double g, double b) {
        drawLine(frame, a, x, y, r, g, b, 2);
    }

    private void drawLine(Mat frame, IPoint a, double x, double y, double r, double g, double b, int thickness) {
        Point pointA = new Point(a.getX(),a.getY());
        Point pointB = new Point(x, y);
        Scalar color = new Scalar(r,g,b);
        Imgproc.line(frame, pointA, pointB, color, thickness);
    }

    private Mat drawMessage(Mat frame, String message, double x, double y,
                            int thickness, double scale, int font,
                            double red, double green, double blue) {
        org.opencv.core.Point point = new org.opencv.core.Point(x, y);
        Scalar color = new Scalar(blue, red, green);
        Imgproc.putText(frame, message, point, font, scale, color , thickness);
        return frame;
    }

}
