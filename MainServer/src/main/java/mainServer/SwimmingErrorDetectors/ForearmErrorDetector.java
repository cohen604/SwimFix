package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.Errors.LeftForearmError;
import Domain.SwimmingData.Errors.RightForearmError;
import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingError;
import Domain.SwimmingData.SwimmingSkeleton;
import java.util.LinkedList;
import java.util.List;

public class ForearmErrorDetector implements SwimmingErrorDetector{
    //TODO move to constructor
    double minAngle = -10;
    double maxAngle = 45;

    @Override
    public List<SwimmingError> detect(SwimmingSkeleton skeleton) {
        List<SwimmingError> errors = new LinkedList<>();
        detectLeft(errors, skeleton);
        detectRight(errors, skeleton);
        return errors;
    }

    private double calcAngle(SkeletonPoint elbow, SkeletonPoint wrist) {
        double delta_x = wrist.getX() - elbow.getX();
        double delta_y = wrist.getY() - elbow.getY(); //will get positive value
        double angle = Math.toDegrees(Math.atan(delta_x / delta_y));
        return angle;
    }

    public void detectLeft(List<SwimmingError> errors, SwimmingSkeleton skeleton) {
        SkeletonPoint elbow = skeleton.getPoint(KeyPoint.L_ELBOW);
        SkeletonPoint wrist = skeleton.getPoint(KeyPoint.L_WRIST);
        double angle = calcAngle(elbow, wrist);
        if (angle < 0 && angle > minAngle) { // range is -10 degrees
            errors.add(new LeftForearmError(angle));
        }
        else if (angle > 0 && angle < maxAngle) { // range is 45 degrees
            errors.add(new LeftForearmError(angle));
        }
    }

    public void detectRight(List<SwimmingError> errors, SwimmingSkeleton skeleton) {
        // right forearm: delta_x will get positive value in 45 degrees case
        // delta_x will get negative value in 10 degrees case
        SkeletonPoint elbow = skeleton.getPoint(KeyPoint.R_ELBOW);
        SkeletonPoint wrist = skeleton.getPoint(KeyPoint.R_WRIST);
        double angle = calcAngle(elbow, wrist);
        if (angle < 0 && angle > -maxAngle) { // range is 45 degrees
            errors.add(new RightForearmError(angle));
        }
        else if (angle > 0 && angle < -minAngle) { // range is 10 degrees
            errors.add(new RightForearmError(angle));
        }
    }
}
