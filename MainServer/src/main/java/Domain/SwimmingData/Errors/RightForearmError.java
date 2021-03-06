package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPointUtils;
import org.opencv.core.Mat;

public class RightForearmError extends ForearmError{

    public RightForearmError(IDraw drawer, double angle, boolean inside) {
        super(drawer, angle, inside);
    }

    @Override
    public void draw(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint elbow = skeleton.getRightElbow();
        IPoint wrist = skeleton.getRightWrist();
        int thickness = 2;
        double r = 240, g = 88, b = 248, a = 255.0;
        drawLine(frame, elbow, wrist, r, g, b, a, thickness);

        // recommendation
        //double slope = 1 / IPointUtils.calcSlope(wrist, elbow);
        double additionX = inside ? -20 : 35;
        IPoint endArrow = IPointUtils.addByScalars(elbow, additionX, 0);
        drawArrow(frame, elbow, endArrow);
    }
}
