package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.*;

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
        if (containsRightSide(skeleton)) {
            SkeletonPoint neck = skeleton.getHead();
            SkeletonPoint elbow = skeleton.getRightElbow();
            SkeletonPoint wrist = skeleton.getRightWrist();
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
        if (containsLeftSide(skeleton)) {
            SkeletonPoint neck = skeleton.getHead();
            SkeletonPoint elbow = skeleton.getLeftElbow();
            SkeletonPoint wrist = skeleton.getLeftWrist();
            double middlePalmX = calcMiddlePalmX(elbow,wrist);
            if (middlePalmX > neck.getX()) {
                System.out.println("left palm cross the head");
                errors.add(iFactoryPalmCrossHeadError.createLeft()) ;
            }
        }
    }

    private boolean containsRightSide(SwimmingSkeleton swimmingSkeleton) {
        return swimmingSkeleton.containsHead()
                && swimmingSkeleton.containsRightElbow()
                && swimmingSkeleton.containsRightWrist();
    }

    private boolean containsLeftSide(SwimmingSkeleton swimmingSkeleton) {
        return swimmingSkeleton.containsHead()
                && swimmingSkeleton.containsLeftElbow()
                && swimmingSkeleton.containsLeftWrist();
    }

    @Override
    public String getTag() {
        return "Palm";
    }

}
