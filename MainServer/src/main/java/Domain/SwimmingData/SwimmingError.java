package Domain.SwimmingData;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import org.opencv.core.Mat;

public abstract class SwimmingError implements IDraw {

    private IDraw drawer;

    public SwimmingError(IDraw drawer) {
        this.drawer = drawer;
    }

    public void drawCircle(Mat frame, IPoint skeletonPoint, int radius) {
        drawer.drawCircle(frame,skeletonPoint,radius);
    }

    public void drawElipce(Mat frame, double radius, double angle, double startAngle,
                           double endAngle, IPoint center, double r, double g,
                           double b, double a) {
        drawer.drawElipce(frame,radius,angle,startAngle,endAngle
                            ,center,r,g,b,a);
    }

    public void drawLine(Mat frame, IPoint a, IPoint b) {
        drawer.drawLine(frame,a,b);
    }


    public void drawLine(Mat frame, IPoint s1, IPoint s2, double r, double g, double b,
                         double a, int thickness) {
        drawer.drawLine(frame, s1, s2, r, g, b, a, thickness);
    }

    public void drawSwimmer(Mat frame, ISwimmingSkeleton skeleton) {
        drawer.drawSwimmer(frame, skeleton);
    }

    public Mat drawLogo(Mat frame) {
        return drawer.drawLogo(frame);
    }

    public Mat drawMessage(Mat frame, String message, double x, double y, int thickness) {
        return drawer.drawMessage(frame, message, x, y, thickness);
    }

    public Mat drawArrow(Mat frame, IPoint a, IPoint b) {
        return drawer.drawArrow(frame, a, b);
    }

    public abstract void draw(Mat frame, ISwimmingSkeleton skeleton);
}
