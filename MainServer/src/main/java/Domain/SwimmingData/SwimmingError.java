package Domain.SwimmingData;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import org.opencv.core.Mat;

public abstract class SwimmingError implements IDraw {

    private IDraw drawer;
    private String tag;

    public SwimmingError(IDraw drawer, String tag) {
        this.drawer = drawer;
        this.tag = tag;
    }

    @Override
    public Mat drawCircle(Mat frame, IPoint skeletonPoint, int radius) {
        return drawer.drawCircle(frame,skeletonPoint,radius);
    }

    @Override
    public Mat drawCircle(Mat frame,
                          IPoint skeletonPoint,
                          int radius,
                          int thickness,
                          double red, double green, double blue) {
        return drawer.drawCircle(frame, skeletonPoint, radius, thickness, red, green, blue);
    }

    @Override
    public Mat drawElipce(Mat frame, double radius, double angle, double startAngle,
                           double endAngle, IPoint center, double r, double g,
                           double b, double a) {
        return drawer.drawElipce(frame,radius,angle,startAngle,endAngle
                            ,center,r,g,b,a);
    }

    @Override
    public Mat drawLine(Mat frame, IPoint a, IPoint b) {
        return drawer.drawLine(frame,a,b);
    }

    @Override
    public Mat drawLine(Mat frame, IPoint s1, IPoint s2, double r, double g, double b,
                         double a, int thickness) {
        return drawer.drawLine(frame, s1, s2, r, g, b, a, thickness);
    }

    @Override
    public Mat drawSwimmer(Mat frame, ISwimmingSkeleton skeleton) {
        return drawer.drawSwimmer(frame, skeleton);
    }

    @Override
    public Mat drawLogo(Mat frame) {
        return drawer.drawLogo(frame);
    }

    @Override
    public Mat drawMessage(Mat frame, String message, double x, double y, int thickness) {
        return drawer.drawMessage(frame, message, x, y, thickness);
    }

    @Override
    public Mat drawArrow(Mat frame, IPoint a, IPoint b) {
        return drawer.drawArrow(frame, a, b);
    }

    @Override
    public Mat drawVerticalArrow(Mat frame, IPoint pointA, IPoint pointB, boolean verticalSide) {
        return drawer.drawVerticalArrow(frame, pointA, pointB, verticalSide);
    }

    public abstract void draw(Mat frame, ISwimmingSkeleton skeleton);

    public String getTag() {
        return this.tag;
    }
}
