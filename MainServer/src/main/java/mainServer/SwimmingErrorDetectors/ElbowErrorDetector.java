package mainServer.SwimmingErrorDetectors;
import Domain.SwimmingData.*;

import java.util.LinkedList;
import java.util.List;

import static Domain.SwimmingData.IPointUtils.calcDistance;
import static Domain.SwimmingData.IPointUtils.calcSlope;

public class ElbowErrorDetector implements SwimmingErrorDetector {


    private double minAngle; //the minimum angle between wrist to elbow to shoulder
    private double maxAngle; //the maximum angle between wrist to elbow to shoulder
    private IFactoryElbowError iFactoryElbowError;

    public ElbowErrorDetector( IFactoryElbowError iFactoryElbowError, double minAngle, double maxAngle) {
        this.iFactoryElbowError = iFactoryElbowError;
        this.minAngle = minAngle;
        this.maxAngle = maxAngle;
    }

    @Override
    public List<SwimmingError> detect(SwimmingSkeleton skeleton) {
        List<SwimmingError> output = new LinkedList<>();
        addRightError(output, skeleton);
        addLeftError(output, skeleton);
        return output;
    }

    @Override
    public String getTag() {
        return "Elbow";
    }

    /**
     * The functoion calc the angle between the shoulder, elbow, wrist
     * Computes angle using law of cosines
     * @param shoulder
     * @param elbow
     * @param wrist
     * @return
     */
    private double calcElbowAngle(IPoint shoulder,IPoint elbow,IPoint wrist) {
        double a = calcDistance(shoulder, elbow);
        double b = calcDistance(elbow, wrist);
        double c = calcDistance(shoulder, wrist);
        double top = Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2);
        double bottom = 2 * a * b;
        double radAngle = Math.acos(top/bottom);
        return Math.toDegrees(radAngle);
    }

    /**
     * The function calc the expected x from y with the linear equation from shoulder to elbow
     * @param shoulder - skeleton point
     * @param elbow - skeleton point
     * @param yNew - the y value
     * @return the expected x value
     */
    private double calcExpectedX(IPoint shoulder, IPoint elbow, double yNew) {
        double slope = calcSlope(elbow,shoulder);
        return (yNew - shoulder.getY()) / slope + shoulder.getX();
    }

    /**
     * The function return a if an angle is valid angle
     * @param angle
     * @return
     */
    private boolean isValidAngle(double angle) {
       return angle >= minAngle && angle <= maxAngle;
    }

    private boolean containesRightSide(SwimmingSkeleton swimmingSkeleton) {
        return swimmingSkeleton.containsRightShoulder()
                && swimmingSkeleton.containsRightElbow()
                && swimmingSkeleton.containsRightWrist();
    }

    private boolean containesLeftSide(SwimmingSkeleton swimmingSkeleton) {
        return swimmingSkeleton.containsLeftShoulder()
                && swimmingSkeleton.containsLeftElbow()
                    && swimmingSkeleton.containsLeftWrist();
    }

    /**
     * The function detect right side
     * @param skeleton - the skelaton
     * @return a list of
     */
    private void addRightError(List<SwimmingError> errors, SwimmingSkeleton skeleton) {
        if(containesRightSide(skeleton)) {
            IPoint shoulder = skeleton.getRightShoulder();
            IPoint elbow = skeleton.getRightElbow();
            IPoint wrist = skeleton.getRightWrist();
            double angle = calcElbowAngle(shoulder, elbow, wrist);
            double expectedX = calcExpectedX(shoulder,elbow,wrist.getY());
            if(expectedX < wrist.getX()) {
                angle = 360 - angle;
            }
            if(!isValidAngle(angle)) {
                errors.add(iFactoryElbowError.createRight(angle));
            }
        }
    }

    private void addLeftError(List<SwimmingError> errors, SwimmingSkeleton skeleton) {
        if(containesLeftSide(skeleton)) {
            IPoint shoulder = skeleton.getLeftShoulder();
            IPoint elbow = skeleton.getLeftElbow();
            IPoint wrist = skeleton.getLeftWrist();
            double angle = calcElbowAngle(shoulder, elbow, wrist);
            double expectedX = calcExpectedX(shoulder,elbow,wrist.getY());
            if(expectedX > wrist.getX()) {
                angle = 360 - angle;
            }
            if(!isValidAngle(angle)) {
                errors.add(iFactoryElbowError.createLeft(angle)) ;
            }
        }
    }
}
