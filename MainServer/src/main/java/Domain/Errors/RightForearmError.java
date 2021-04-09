package Domain.Errors;

import Domain.Drawing.IDraw;
import Domain.Errors.Interfaces.ForearmError;
import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Points.IPointUtils;
import org.opencv.core.Mat;

public class RightForearmError extends ForearmError {

    public RightForearmError(IDraw drawer,
                             double angle,
                             double maxAngle,
                             double minAngle,
                             boolean inside) {
        super(drawer, angle, maxAngle, minAngle, inside, "Right Forearm Error");
    }

    @Override
    public void drawBefore(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint elbow = skeleton.getRightElbow();
        IPoint wrist = skeleton.getRightWrist();

        double distance = IPointUtils.calcDistance(elbow, wrist);
        IPoint elbowTag = IPointUtils.addByScalars(elbow, 0, distance * 2);
        IPoint v = IPointUtils.getVec(elbow, elbowTag);
        IPoint thetaMax = IPointUtils.pivotVector(v, this.maxAngle);
        IPoint thetaMin = IPointUtils.pivotVector(v, this.minAngle);

        thetaMax = IPointUtils.addByScalars(thetaMax, elbow.getX(), elbow.getY());
        thetaMin = IPointUtils.addByScalars(thetaMin, elbow.getX(), elbow.getY());

        drawLine(frame, elbow, thetaMax, 0, 255, 0, 1, 2);
        drawLine(frame, elbow, thetaMin, 0, 255, 0, 1, 2);

    }

    @Override
    public void drawAfter(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint elbow = skeleton.getRightElbow();
        IPoint wrist = skeleton.getRightWrist();
        drawLine(frame, elbow, wrist, 255, 0, 0, 1, 2);
        // recommendation
        if(wrist.getX() > elbow.getX()) {
            drawVerticalArrow(frame, elbow, wrist, true, 10, 217, 27);
        }
        else {
            drawVerticalArrow(frame, elbow, wrist, false, 10, 217, 27);
        }
    }
}
