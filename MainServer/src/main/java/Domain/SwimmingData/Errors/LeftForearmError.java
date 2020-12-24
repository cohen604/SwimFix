package Domain.SwimmingData.Errors;

import Domain.SwimmingData.KeyPoint;
import Domain.SwimmingData.SkeletonPoint;
import Domain.SwimmingData.SwimmingSkeleton;
import org.opencv.core.Mat;

public class LeftForearmError extends ForearmError{

    public LeftForearmError(double angle) {
        super(angle);
    }

    @Override
    public void draw(Mat frame, SwimmingSkeleton skeleton) {
        SkeletonPoint elbow = skeleton.getPoint(KeyPoint.L_ELBOW);
        SkeletonPoint wrist = skeleton.getPoint(KeyPoint.L_WRIST);
        int thickness = 2;
        double r = 240, g = 88, b = 248, a = 255.0;
        drawLine(frame, elbow, wrist, r, g, b, a, thickness);
    }
}
