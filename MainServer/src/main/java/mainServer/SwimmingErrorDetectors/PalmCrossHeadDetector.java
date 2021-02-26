package mainServer.SwimmingErrorDetectors;

import Domain.SwimmingData.*;
import Domain.SwimmingData.Points.IPoint;

import java.util.LinkedList;
import java.util.List;

import static Domain.SwimmingData.Points.IPointUtils.*;

public class PalmCrossHeadDetector implements SwimmingErrorDetector {

    private IFactoryPalmCrossHeadError iFactoryPalmCrossHeadError;

    public PalmCrossHeadDetector(IFactoryPalmCrossHeadError iFactoryPalmCrossHeadError) {
        this.iFactoryPalmCrossHeadError = iFactoryPalmCrossHeadError;
    }

    @Override
    public List<SwimmingError> detect(ISwimmingSkeleton skeleton) {
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
    private double calcMiddlePalmX(IPoint elbow, IPoint wrist) {
        double l1;  // the length between elbow and wrist
        double l2;  // the length between wrist and the end of the palm
        double ratio = 1.35;    // the ratio between l1 and l2
        l1 = calcDistance(elbow, wrist);
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
    private void detectRightPalmCross(ISwimmingSkeleton skeleton, List<SwimmingError> errors) {
        if (containsRightSide(skeleton)) {
            IPoint neck = skeleton.getHead();
            IPoint elbow = skeleton.getRightElbow();
            IPoint wrist = skeleton.getRightWrist();
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
    private void detectLeftPalmCross(ISwimmingSkeleton skeleton, List<SwimmingError> errors) {
        if (containsLeftSide(skeleton)) {
            IPoint neck = skeleton.getHead();
            IPoint elbow = skeleton.getLeftElbow();
            IPoint wrist = skeleton.getLeftWrist();
            double middlePalmX = calcMiddlePalmX(elbow,wrist);
            if (middlePalmX > neck.getX()) {
                System.out.println("left palm cross the head");
                errors.add(iFactoryPalmCrossHeadError.createLeft()) ;
            }
        }
    }

    private boolean containsRightSide(ISwimmingSkeleton swimmingSkeleton) {
        return swimmingSkeleton.containsHead()
                && swimmingSkeleton.containsRightElbow()
                && swimmingSkeleton.containsRightWrist();
    }

    private boolean containsLeftSide(ISwimmingSkeleton swimmingSkeleton) {
        return swimmingSkeleton.containsHead()
                && swimmingSkeleton.containsLeftElbow()
                && swimmingSkeleton.containsLeftWrist();
    }

    @Override
    public String getTag() {
        return "Palm";
    }

}
