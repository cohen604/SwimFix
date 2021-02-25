package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingError;
import Domain.SwimmingData.SwimmingSkeleton;
import java.util.LinkedList;
import java.util.List;

public class ForearmErrorDetector implements SwimmingErrorDetector{
    private double minAngle;
    private double maxAngle;
    private IFactoryForearmError iFactoryForearmError;

    public ForearmErrorDetector(IFactoryForearmError iFactoryForearmError, double minAngle, double maxAngle) {
        this.iFactoryForearmError = iFactoryForearmError;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    @Override
    public List<SwimmingError> detect(SwimmingSkeleton skeleton) {
        List<SwimmingError> errors = new LinkedList<>();
        detectRight(errors, skeleton);
        detectLeft(errors, skeleton);
        return errors;
    }

    @Override
    public String getTag() {
        return "Forearm";
    }

    private double calcAngle(SkeletonPoint elbow, SkeletonPoint wrist) {
        double delta_x = wrist.getX() - elbow.getX();
        double delta_y = wrist.getY() - elbow.getY(); //will get positive value
        double angle = Math.toDegrees(Math.atan(delta_x / delta_y));
        return angle;
    }

    private boolean containsRightSide(SwimmingSkeleton swimmingSkeleton) {
        return swimmingSkeleton.containsRightElbow()
                && swimmingSkeleton.containsRightWrist();
    }

    private boolean containsLeftSide(SwimmingSkeleton swimmingSkeleton) {
        return swimmingSkeleton.containsLeftElbow()
                && swimmingSkeleton.containsLeftWrist();
    }
    
    public void detectLeft(List<SwimmingError> errors, SwimmingSkeleton skeleton) {
        if(containsLeftSide(skeleton)) {
            SkeletonPoint elbow = skeleton.getLeftElbow();
            SkeletonPoint wrist = skeleton.getLeftWrist();
            double angle = calcAngle(elbow, wrist);
            if (angle < 0 && angle < minAngle) { // range is -10 degrees
                errors.add(iFactoryForearmError.createLeft(angle));
            } else if (angle > 0 && angle > maxAngle) { // range is 45 degrees
                errors.add(iFactoryForearmError.createLeft(angle));
            }
        }
    }

    public void detectRight(List<SwimmingError> errors, SwimmingSkeleton skeleton) {
        if(containsRightSide(skeleton)) {
            // right forearm: delta_x will get positive value in 45 degrees case
            // delta_x will get negative value in 10 degrees case
            SkeletonPoint elbow = skeleton.getRightElbow();
            SkeletonPoint wrist = skeleton.getRightWrist();
            double angle = calcAngle(elbow, wrist);
            if (angle < 0 && angle < -maxAngle) { // range is 45 degrees
                errors.add(iFactoryForearmError.createRight(angle));
            } else if (angle > 0 && angle > -minAngle) { // range is 10 degrees
                errors.add(iFactoryForearmError.createRight(angle));
            }
        }
    }
}
