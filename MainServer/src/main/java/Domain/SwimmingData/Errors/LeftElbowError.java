package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPointUtils;
import org.opencv.core.Mat;

public class LeftElbowError extends ElbowError {

    public LeftElbowError(IDraw drawer, double angle, boolean inside) {
        super(drawer, angle, inside);
    }

    @Override
    public void draw(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint shoulder = skeleton.getLeftShoulder();
        IPoint elbow = skeleton.getLeftElbow();
        IPoint wrist = skeleton.getLeftWrist();
        drawShoulderElbowWrist(frame, shoulder, elbow, wrist, -50);

        IPoint middle = IPointUtils.getMiddlePoint(elbow, wrist);
        double slope = 1 / IPointUtils.calcSlope(wrist, elbow);
        double additionX = inside ? 20 : -35;
        IPoint endArrow = IPointUtils.addByScalars(middle, additionX, 0);
        drawArrow(frame, middle, endArrow);
    }
}
