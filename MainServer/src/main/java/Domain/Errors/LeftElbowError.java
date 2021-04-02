package Domain.Errors;

import Domain.Drawing.IDraw;
import Domain.Errors.Interfaces.ElbowError;
import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Points.IPointUtils;
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
    public void drawBefore(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint shoulder = skeleton.getLeftShoulder();
        IPoint elbow = skeleton.getLeftElbow();

        IPoint v = IPointUtils.getVec(shoulder, elbow);
        IPoint vmin = IPointUtils.pivotVector(v, -this.minAngle);
        IPoint vmax = IPointUtils.pivotVector(v, this.maxAngle);

        vmin = IPointUtils.mulByScalar(vmin, 2);
        vmax = IPointUtils.mulByScalar(vmax, 2);

        vmin = IPointUtils.addByScalars(elbow, vmin.getX(), vmin.getY());
        vmax = IPointUtils.addByScalars(elbow, -vmax.getX(), -vmax.getY());

        drawLine(frame, elbow, vmin, 10, 10, 10, 1, 1);
        drawLine(frame, elbow, vmax, 10, 10, 10, 1, 1);
    }

    @Override
    public void drawAfter(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint wrist = skeleton.getLeftWrist();

        double additionX = inside ? 20 : -35;
        IPoint endArrow = IPointUtils.addByScalars(wrist, additionX, 0);
        drawArrow(frame, wrist, endArrow);
    }
}
