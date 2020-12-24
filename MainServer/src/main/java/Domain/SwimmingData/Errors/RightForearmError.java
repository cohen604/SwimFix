package Domain.SwimmingData.Errors;

import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class RightForearmError extends ForearmError{

    public RightForearmError(double angle) {
        super(angle);
    }

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        SkeletonPoint elbow = skeleton.getPoint(KeyPoint.R_ELBOW);
        SkeletonPoint wrist = skeleton.getPoint(KeyPoint.R_WRIST);
        int thickness = 2;
        double r = 240, g = 88, b = 248, a = 255.0;
        drawLine(frame, elbow, wrist, r, g, b, a, thickness);
    }
}
