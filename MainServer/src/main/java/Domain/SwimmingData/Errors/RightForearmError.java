package Domain.SwimmingData.Errors;

import Domain.SwimmingData.Drawing.IDraw;
import Domain.SwimmingData.Points.IPoint;
import Domain.SwimmingData.ISwimmingSkeleton;
import Domain.SwimmingData.Points.IPointUtils;
import org.opencv.core.Mat;

public class RightForearmError extends ForearmError{

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
        drawLine(frame, elbow, wrist, 255, 0, 0, 1, 1);
        // recommendation
        if(wrist.getX() > elbow.getX()) {
            drawVerticalArrow(frame, elbow, wrist, true);
        }
        else {
            drawVerticalArrow(frame, elbow, wrist, false);
        }
    }
}
