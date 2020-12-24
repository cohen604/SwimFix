package Domain.SwimmingData.Errors;

import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class LeftPalmCrossHeadError extends PalmCrossHeadError {

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        SkeletonPoint elbow = skeleton.getPoint(KeyPoint.L_ELBOW);
        SkeletonPoint wrist = skeleton.getPoint(KeyPoint.L_WRIST);
        drawPalmCrossHead(frame, elbow, wrist);
    }
}
