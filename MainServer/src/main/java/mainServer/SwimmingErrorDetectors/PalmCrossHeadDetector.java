package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.*;
import Domain.SwimmingData.Errors.LeftPalmCrossHeadError;
import Domain.SwimmingData.Errors.PalmCrossHeadError;
import Domain.SwimmingData.Errors.RightPalmCrossHeadError;

import java.util.LinkedList;
import java.util.List;

public class PalmCrossHeadDetector implements SwimmingErrorDetector {

    private IFactoryPalmCrossHeadError iFactoryPalmCrossHeadError;

    public PalmCrossHeadDetector(IFactoryPalmCrossHeadError iFactoryPalmCrossHeadError) {
        this.iFactoryPalmCrossHeadError = iFactoryPalmCrossHeadError;
    }

    @Override
    public List<SwimmingError> detect(SwimmingSkeleton skeleton) {
        List<SwimmingError> output = new LinkedList<>();
        detectRightPalmCross(skeleton, output);
        detectLeftPalmCross(skeleton, output);
        return output;
    }

    /**
     * The function calculate the middle palm X from the elbow and wrist
     * @param elbow - skeleton point
     * @param wrist - skeleton point
     * @return the middle palm X.
     */
    private double calcMiddlePalmX(SkeletonPoint elbow, SkeletonPoint wrist) {
        double l1;  // the length between elbow and wrist
        double l2;  // the length between wrist and the end of the palm
        double ratio = 1.35;    // the ratio between l1 and l2
        l1 = elbow.calcDistance(wrist);
        l2 = l1 / ratio;
        double x, y;
        x = wrist.getX() - elbow.getX();
        y = wrist.getY() - elbow.getY();
        double size = Math.sqrt(x * x + y * y);
        x = x / size;
        double middlePalmX = wrist.getX() + x * (l2 / 2);
        return middlePalmX;
    }

    /**
     * find and add an error of the right palm
     * @param skeleton - the skeleton of the swimmer
     * @param errors - the list of error the function update
     */
    private void detectRightPalmCross(SwimmingSkeleton skeleton, List<SwimmingError> errors) {
        List<KeyPoint> rightKeys = getRightKeys();
        if (skeleton.contatinsKeys(rightKeys)) {
            SkeletonPoint neck = skeleton.getPoint(KeyPoint.HEAD);
            SkeletonPoint elbow = skeleton.getPoint(KeyPoint.R_ELBOW);
            SkeletonPoint wrist = skeleton.getPoint(KeyPoint.R_WRIST);
            double middlePalmX = calcMiddlePalmX(elbow,wrist);
            if (middlePalmX < neck.getX()) {
                System.out.println("right palm cross the head");
                errors.add(iFactoryPalmCrossHeadError.createRight()) ;
            }
        }
    }

    /**
     * find and add an error of the right palm
     * @param skeleton - the skeleton of the swimmer
     * @param errors - the list of error the function update
     */
    private void detectLeftPalmCross(SwimmingSkeleton skeleton, List<SwimmingError> errors) {
        List<KeyPoint> leftKeys = getLeftKeys();
        if (skeleton.contatinsKeys(leftKeys)) {
            SkeletonPoint neck = skeleton.getPoint(KeyPoint.HEAD);
            SkeletonPoint elbow = skeleton.getPoint(KeyPoint.L_ELBOW);
            SkeletonPoint wrist = skeleton.getPoint(KeyPoint.L_WRIST);
            double middlePalmX = calcMiddlePalmX(elbow,wrist);
            if (middlePalmX > neck.getX()) {
                System.out.println("left palm cross the head");
                errors.add(iFactoryPalmCrossHeadError.createLeft()) ;
            }
        }
    }

    /**
     * The function return the key point need for the right side
     * @return key points
     */
    private List<KeyPoint> getRightKeys() {
        List<KeyPoint> points = new LinkedList<>();
        points.add(KeyPoint.HEAD);
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
        points.add(KeyPoint.HEAD);
        points.add(KeyPoint.L_ELBOW);
        points.add(KeyPoint.L_WRIST);
        return points;
    }

    @Override
    public String getTag() {
        return "Palm";
    }

}
