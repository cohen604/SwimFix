package Domain.Errors;

import Domain.Drawing.IDraw;
import Domain.Errors.Interfaces.ForearmError;
import Domain.Points.IPoint;
import Domain.SwimmingSkeletonsData.ISwimmingSkeleton;
import Domain.Points.IPointUtils;
import org.opencv.core.Mat;

public class LeftForearmError extends ForearmError {

    public LeftForearmError(IDraw drawer,
                            double angle,
                            double maxAngle,
                            double minAngle,
                            boolean inside) {
        super(drawer, angle, maxAngle, minAngle, inside, "Left Forearm Error");
    }

    @Override
    public void drawBefore(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint elbow = skeleton.getLeftElbow();
        IPoint wrist = skeleton.getLeftWrist();

        double distance = IPointUtils.calcDistance(elbow, wrist);
        IPoint elbowTag = IPointUtils.addByScalars(elbow, 0, distance * 2);
        IPoint v = IPointUtils.getVec(elbow, elbowTag);
        IPoint thetaMax = IPointUtils.pivotVector(v, -this.maxAngle);
        IPoint thetaMin = IPointUtils.pivotVector(v, -this.minAngle);

        thetaMax = IPointUtils.addByScalars(thetaMax, elbow.getX(), elbow.getY());
        thetaMin = IPointUtils.addByScalars(thetaMin, elbow.getX(), elbow.getY());

        drawLine(frame, elbow, thetaMax, 0, 255, 0, 1, 2);
        drawLine(frame, elbow, thetaMin, 0, 255, 0, 1, 2);
    }

    @Override
    public void drawAfter(Mat frame, ISwimmingSkeleton skeleton) {
        IPoint elbow = skeleton.getLeftElbow();
        IPoint wrist = skeleton.getLeftWrist();
        drawLine(frame, elbow, wrist, 255, 0, 0, 1, 2);
        // recommendation
        if(wrist.getX() < elbow.getX()) {
            drawVerticalArrow(frame, elbow, wrist, false);
        }
        else {
            drawVerticalArrow(frame, elbow, wrist, true);
        }
    }
}
