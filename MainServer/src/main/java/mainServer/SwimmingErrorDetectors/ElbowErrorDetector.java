package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.Errors.LeftElbowError;
import Domain.SwimmingData.Errors.RightElbowError;
import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingError;
import Domain.SwimmingData.SwimmingSkeleton;

import java.util.LinkedList;
import java.util.List;

public class ElbowErrorDetector implements SwimmingErrorDetector {


    private double minAngle; //the minimum angle between wrist to elbow to shoulder
    private double maxAngle; //the maximum angle between wrist to elbow to shoulder

    public ElbowErrorDetector(double minAngle, double maxAngle) {
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
    private double calcElbowAngle(SkeletonPoint shoulder,SkeletonPoint elbow,SkeletonPoint wrist) {
        double a = shoulder.calcDistance(elbow);
        double b = elbow.calcDistance(wrist);
        double c = shoulder.calcDistance(wrist);
        double top = Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2);
        double bottom = 2 * a * b;
        double radAngle = Math.acos(top/bottom);
        return Math.toDegrees(radAngle);
    }

    /**
     * The function return a if an angle is valid angle
     * @param angle
     * @return
     */
    private boolean isValidAngle(double angle) {
       return angle >= minAngle && angle <= maxAngle;
    }

    /**
     * The function return the key point need for the right side
     * @return key points
     */
    private List<KeyPoint> getRightKeys() {
        List<KeyPoint> points = new LinkedList<>();
        points.add(KeyPoint.R_SHOULDER);
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
        points.add(KeyPoint.L_SHOULDER);
        points.add(KeyPoint.L_ELBOW);
        points.add(KeyPoint.L_WRIST);
        return points;
    }

    /**
     * The function detect right side
     * @param skeleton - the skelaton
     * @return a list of
     */
    private void addRightError(List<SwimmingError> errors, SwimmingSkeleton skeleton) {
        List<KeyPoint> rightKeys = getRightKeys();
        if(skeleton.contatinsKeys(rightKeys)) {
            SkeletonPoint shoulder = skeleton.getPoint(KeyPoint.R_SHOULDER);
            SkeletonPoint elbow = skeleton.getPoint(KeyPoint.R_ELBOW);
            SkeletonPoint wrist = skeleton.getPoint(KeyPoint.R_WRIST);
            double angle = calcElbowAngle(shoulder, elbow, wrist);
            if(!isValidAngle(angle)) {
                errors.add(new RightElbowError(angle));
            }
        }
    }

    private void addLeftError(List<SwimmingError> errors, SwimmingSkeleton skeleton) {
        List<KeyPoint> leftKeys = getLeftKeys();
        if(skeleton.contatinsKeys(leftKeys)) {
            SkeletonPoint shoulder = skeleton.getPoint(KeyPoint.L_SHOULDER);
            SkeletonPoint elbow = skeleton.getPoint(KeyPoint.L_ELBOW);
            SkeletonPoint wrist = skeleton.getPoint(KeyPoint.L_WRIST);
            double angle = calcElbowAngle(shoulder, elbow, wrist);
            if(!isValidAngle(angle)) {
                errors.add(new LeftElbowError(angle)) ;
            }
        }
    }
}
