package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPointUtils;
import org.opencv.core.Mat;

public class LeftElbowError extends ElbowError {

    public LeftElbowError(IDraw drawer,
                          double maxAnlge,
                          double minAngle,
                          double angle,
                          boolean inside) {
        super(drawer, maxAnlge, minAngle, angle, inside, "Left Elbow Error");
    }

    @Override
    public void draw(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint shoulder = skeleton.getLeftShoulder();
        IPoint elbow = skeleton.getLeftElbow();
        IPoint wrist = skeleton.getLeftWrist();

        IPoint v = IPointUtils.getVec(shoulder, elbow);
        IPoint vmin = IPointUtils.pivotVector(v, -this.minAngle);
        IPoint vmax = IPointUtils.pivotVector(v, this.maxAngle);

        vmin = IPointUtils.mulByScalar(vmin, 2);
        vmax = IPointUtils.mulByScalar(vmax, 2);

        vmin = IPointUtils.addByScalars(elbow, vmin.getX(), vmin.getY());
        vmax = IPointUtils.addByScalars(elbow, -vmax.getX(), -vmax.getY());

        drawLine(frame, elbow, vmin, 10, 10, 10, 1, 1);
        drawLine(frame, elbow, vmax, 10, 10, 10, 1, 1);

        double additionX = inside ? 20 : -35;
        IPoint endArrow = IPointUtils.addByScalars(wrist, additionX, 0);
        drawArrow(frame, wrist, endArrow);

    }
}
