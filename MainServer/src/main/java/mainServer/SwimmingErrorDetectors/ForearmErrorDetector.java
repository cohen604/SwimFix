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

    /**
     * The function return the key point need for the right side
     * @return key points
     */
    private List<KeyPoint> getRightKeys() {
        List<KeyPoint> points = new LinkedList<>();
        points.add(KeyPoint.R_ELBOW);
        points.add(KeyPoint.R_WRIST);
        return points;
    }

    /**
     * The function return the key point need for the left side
     * @return key points
     */
    private List<KeyPoint> getLeftKeys() {
        List<KeyPoint> points = new LinkedList<>();
        points.add(KeyPoint.L_ELBOW);
        points.add(KeyPoint.L_WRIST);
        return points;
    }

    public void detectLeft(List<SwimmingError> errors, SwimmingSkeleton skeleton) {
        List<KeyPoint> leftKeys = getLeftKeys();
        if(skeleton.contatinsKeys(leftKeys)) {
            SkeletonPoint elbow = skeleton.getPoint(KeyPoint.L_ELBOW);
            SkeletonPoint wrist = skeleton.getPoint(KeyPoint.L_WRIST);
            double angle = calcAngle(elbow, wrist);
            if (angle < 0 && angle < minAngle) { // range is -10 degrees
                errors.add(new LeftForearmError(angle));
            } else if (angle > 0 && angle > maxAngle) { // range is 45 degrees
                errors.add(new LeftForearmError(angle));
            }
        }
    }

    public void detectRight(List<SwimmingError> errors, SwimmingSkeleton skeleton) {
        List<KeyPoint> rightKeys = getRightKeys();
        if(skeleton.contatinsKeys(rightKeys)) {
            // right forearm: delta_x will get positive value in 45 degrees case
            // delta_x will get negative value in 10 degrees case
            SkeletonPoint elbow = skeleton.getPoint(KeyPoint.R_ELBOW);
            SkeletonPoint wrist = skeleton.getPoint(KeyPoint.R_WRIST);
            double angle = calcAngle(elbow, wrist);
            if (angle < 0 && angle < -maxAngle) { // range is 45 degrees
                errors.add(new RightForearmError(angle));
            } else if (angle > 0 && angle > -minAngle) { // range is 10 degrees
                errors.add(new RightForearmError(angle));
            }
        }
    }
}
