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

        drawLine(frame, elbow, vmin, 10, 255, 10, 1, 2);
        drawLine(frame, elbow, vmax, 10, 255, 10, 1, 2);
    }

    @Override
    public void drawAfter(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint shoulder = skeleton.getLeftShoulder();
        IPoint elbow = skeleton.getLeftElbow();
        IPoint wrist = skeleton.getLeftWrist();

        drawLine(frame, shoulder, elbow, 255, 10, 10, 1, 2);
        drawLine(frame, elbow, wrist, 255, 10, 10, 1, 2);

        // recommendation
        drawVerticalArrow(frame, elbow, wrist, !inside, 10, 217, 27);
    }
}
