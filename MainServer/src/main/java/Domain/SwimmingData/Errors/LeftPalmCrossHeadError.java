package Domain.SwimmingData.Errors;

import Domain.SwimmingData.IDraw;
import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class LeftPalmCrossHeadError extends PalmCrossHeadError {


    public LeftPalmCrossHeadError(IDraw drawer) {
        super(drawer);
    }

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        SkeletonPoint head = skeleton.getPoint(KeyPoint.HEAD);
        SkeletonPoint wrist = skeleton.getPoint(KeyPoint.L_WRIST);
        drawPalmCrossHead(frame, head, wrist);
    }
}
