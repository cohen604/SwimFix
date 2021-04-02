package Domain.Errors.Interfaces;

import Domain.Drawing.IDraw;
import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import org.opencv.core.Mat;

public abstract class SwimmingError{

    private IDraw drawer;
    private String tag;

    public SwimmingError(IDraw drawer, String tag) {
        this.drawer = drawer;
        this.tag = tag;
    }
    protected Mat drawLine(Mat frame, IPoint s1, IPoint s2, double r, double g, double b,
                         double a, int thickness) {
        return drawer.drawLine(frame, s1, s2, r, g, b, a, thickness);
    }
    protected Mat drawArrow(Mat frame, IPoint a, IPoint b) {
        return drawer.drawArrow(frame, a, b);
    }

    protected Mat drawVerticalArrow(Mat frame, IPoint pointA, IPoint pointB, boolean verticalSide) {
        return drawer.drawVerticalArrow(frame, pointA, pointB, verticalSide);
    }

    public abstract void drawBefore(Mat frame, ISwimmingSkeleton skeleton);

    public abstract void drawAfter(Mat frame, ISwimmingSkeleton skeleton);

    public String getTag() {
        return this.tag;
    }
}
