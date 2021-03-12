package DomainLogic.SwimmingErrorDetectors;

import Domain.SwimmingData.*;
import Domain.SwimmingData.Points.IPoint;

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
    public List<SwimmingError> detect(ISwimmingSkeleton skeleton) {
        List<SwimmingError> errors = new LinkedList<>();
        detectRight(errors, skeleton);
        detectLeft(errors, skeleton);
        return errors;
    }

    @Override
    public String getTag() {
        return "Forearm";
    }

    private double calcAngle(IPoint elbow, IPoint wrist) {
        double delta_x = wrist.getX() - elbow.getX();
        double delta_y = wrist.getY() - elbow.getY(); //will get positive value
        double angle = Math.toDegrees(Math.atan(delta_x / delta_y));
        return angle;
    }

    private boolean containsRightSide(ISwimmingSkeleton swimmingSkeleton) {
        return swimmingSkeleton.containsRightElbow()
                && swimmingSkeleton.containsRightWrist();
    }

    private boolean containsLeftSide(ISwimmingSkeleton swimmingSkeleton) {
        return swimmingSkeleton.containsLeftElbow()
                && swimmingSkeleton.containsLeftWrist();
    }

    public void detectLeft(List<SwimmingError> errors, ISwimmingSkeleton skeleton) {
        if(containsLeftSide(skeleton)) {
            IPoint elbow = skeleton.getLeftElbow();
            IPoint wrist = skeleton.getLeftWrist();
            double angle = calcAngle(elbow, wrist);
            if (angle < 0 && angle < minAngle) { // range is -10 degrees
                errors.add(iFactoryForearmError.createLeft(angle, false));
            } else if (angle > 0 && angle > maxAngle) { // range is 45 degrees
                errors.add(iFactoryForearmError.createLeft(angle, false));
            }
        }
    }

    public void detectRight(List<SwimmingError> errors, ISwimmingSkeleton skeleton) {
        if(containsRightSide(skeleton)) {
            // right forearm: delta_x will get positive value in 45 degrees case
            // delta_x will get negative value in 10 degrees case
            IPoint elbow = skeleton.getRightElbow();
            IPoint wrist = skeleton.getRightWrist();
            double angle = calcAngle(elbow, wrist);
            if (angle < 0 && angle < -maxAngle) { // range is 45 degrees
                errors.add(iFactoryForearmError.createRight(angle, false));
            } else if (angle > 0 && angle > -minAngle) { // range is 10 degrees
                errors.add(iFactoryForearmError.createRight(angle, false));
            }
        }
    }
}
