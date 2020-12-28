package Domain.SwimmingData.Errors;

import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class RightPalmCrossHeadError extends PalmCrossHeadError{

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        SkeletonPoint head = skeleton.getPoint(KeyPoint.HEAD);
        SkeletonPoint wrist = skeleton.getPoint(KeyPoint.R_WRIST);
        drawPalmCrossHead(frame, head, wrist);
    }
}
