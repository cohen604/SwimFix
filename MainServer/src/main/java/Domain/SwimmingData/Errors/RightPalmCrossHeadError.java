package Domain.SwimmingData.Errors;

import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class RightPalmCrossHeadError extends PalmCrossHeadError{

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        SkeletonPoint elbow = skeleton.getPoint(KeyPoint.R_ELBOW);
        SkeletonPoint wrist = skeleton.getPoint(KeyPoint.R_WRIST);
        drawPalmCrossHead(frame, elbow, wrist);
    }
}
