package Domain.Errors;

import Domain.Errors.Interfaces.ElbowError;
import Domain.SwimmingSkeletonsData.*;
import Domain.Drawing.IDraw;
import Domain.Points.IPoint;
import Domain.Points.IPointUtils;
import org.opencv.core.Mat;

public class RightElbowError extends ElbowError {

    public RightElbowError(IDraw drawer,
                           double maxAngle,
                           double minAngle,
                           double angle,
                           boolean inside) {
        super(drawer, maxAngle, minAngle, angle, inside, "Right Elbow Error");
    }

    @Override
    public void drawBefore(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint shoulder = skeleton.getRightShoulder();
        IPoint elbow = skeleton.getRightElbow();

        IPoint v = IPointUtils.getVec(shoulder, elbow);
        IPoint vmin = IPointUtils.pivotVector(v, this.minAngle);
        IPoint vmax = IPointUtils.pivotVector(v, -this.maxAngle);

        vmin = IPointUtils.mulByScalar(vmin, 2);
        vmax = IPointUtils.mulByScalar(vmax, 2);

        vmin = IPointUtils.addByScalars(elbow, vmin.getX(), vmin.getY());
        vmax = IPointUtils.addByScalars(elbow, -vmax.getX(), -vmax.getY());

        drawLine(frame, elbow, vmin, 10, 255, 10, 1, 2);
        drawLine(frame, elbow, vmax, 10, 255, 10, 1, 2);
    }

    @Override
    public void drawAfter(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint shoulder = skeleton.getRightShoulder();
        IPoint elbow = skeleton.getRightElbow();
        IPoint wrist = skeleton.getRightWrist();

        drawLine(frame, shoulder, elbow, 255, 10, 10, 1, 2);
        drawLine(frame, elbow, wrist, 255, 10, 10, 1, 2);

        // recommendation
        drawVerticalArrow(frame, elbow, wrist, inside, 10, 217, 27);
    }
}
